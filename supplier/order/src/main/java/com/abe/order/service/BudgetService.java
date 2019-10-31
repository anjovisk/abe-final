package com.abe.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abe.order.exception.BudgetNotFoundException;
import com.abe.order.exception.BudgetRequestException;
import com.abe.order.exception.SupplierAnswerException;
import com.abe.order.model.Budget;
import com.abe.order.model.Budget.BudgetStatus;
import com.abe.order.model.SupplierNotificationRequest.NotificationType;
import com.abe.order.model.BudgetRequest;
import com.abe.order.model.Product;
import com.abe.order.model.ShopkeeperNotificationRequest;
import com.abe.order.model.SupplierAnswer;
import com.abe.order.model.SupplierNotificationRequest;

@Service
public class BudgetService extends BudgetServiceBase {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private NotificationService notificationService;
	
	public void requestBudget(Long supplier, BudgetRequest newBudgetRequest) {
		Optional<BudgetRequest> pendingBudgetRequest = getBudgetRequest(newBudgetRequest.getClient(), supplier);
		if (pendingBudgetRequest.isPresent()) {
			throw new BudgetRequestException("Já existe uma solicitação de orçament em andamento.");
		}
		budgetRequests.get(supplier).add(newBudgetRequest);
		SupplierNotificationRequest notificationRequest = new SupplierNotificationRequest();
		notificationRequest.setKey(String.valueOf(newBudgetRequest.getClient()));
		notificationRequest.setType(NotificationType.BUDGET_REQUESTED);
		notificationService.notifySupplier(supplier, notificationRequest);
	}
	
	public void createBudget(Long supplier, SupplierAnswer supplierAnswer) {
		Optional<BudgetRequest> pendingBudgetRequest = getBudgetRequest(supplierAnswer.getClient(), supplier);
		if (!pendingBudgetRequest.isPresent()) {
			throw new SupplierAnswerException("Solicitação de orçamento não encontrada.");
		}
		List<Long> productIds = pendingBudgetRequest.get().getItems().stream().map(item -> item.getProductId()).collect(Collectors.toList());
		List<Product> products = productService.getProducts(productIds);
		BigDecimal total = BigDecimal.valueOf(products.stream().mapToDouble(product -> product.getPrice().doubleValue()).sum());
		if (supplierAnswer.getDiscount() != null) {
			total = total.subtract(total.multiply(supplierAnswer.getDiscount()).divide(BigDecimal.valueOf(100)));
		}
		Budget budget = new Budget(pendingBudgetRequest.get(), supplier, supplierAnswer.getBudgetNumber(), total, supplierAnswer.getEstimatedDeliveryDate());
		if (!budgets.containsKey(supplier)) {
			budgets.put(supplier, new ArrayList<Budget>());
		}
		budgets.get(supplier).add(budget);
		budgetRequests.get(supplier).removeIf(budgetRequest -> budgetRequest.getClient().equals(pendingBudgetRequest.get().getClient()));
		System.out.println(String.format("budget id: %s", budget.getId()));
		ShopkeeperNotificationRequest notificationRequest = new ShopkeeperNotificationRequest();
		notificationRequest.setKey(String.valueOf(budget.getId()));
		notificationRequest.setType(com.abe.order.model.ShopkeeperNotificationRequest.NotificationType.BUDGET_CREATED);
		notificationService.notifyShopkeeper(notificationRequest, budget.getWebhookUrl());
	}
	
	public Optional<Budget> getPendingBudget(Long supplier, Long id) {
		if (!budgets.containsKey(supplier)) {
			budgets.put(supplier, new ArrayList<Budget>());
		}
		Optional<Budget> result = budgets.get(supplier).stream().filter(budget ->
			budget.getId().equals(id)
			&& budget.getSupplier().equals(supplier)
			&& budget.getStatus().equals(BudgetStatus.PENDING)
		).findFirst();
		return result;
	}
	
	public void rejectBudget(Long supplier, Long id) {
		Optional<Budget> budget = getPendingBudget(supplier, id);
		if (!budget.isPresent()) {
			throw new BudgetNotFoundException("Orçamento pendente não encontrado.");
		}
		budget.get().setStatus(BudgetStatus.REJECTED);
		SupplierNotificationRequest notificationRequest = new SupplierNotificationRequest();
		notificationRequest.setKey(String.valueOf(budget.get().getId()));
		notificationRequest.setType(NotificationType.BUDGET_REJECTED);
		notificationService.notifySupplier(supplier, notificationRequest);
	}
	
	public void acceptBudget(Long supplier, Long id) {
		Optional<Budget> budget = getPendingBudget(supplier, id);
		if (!budget.isPresent()) {
			throw new BudgetNotFoundException("Orçamento pendente não encontrado.");
		}
		orderService.create(budget.get());
		budget.get().setStatus(BudgetStatus.ACCEPTED);
		SupplierNotificationRequest notificationRequest = new SupplierNotificationRequest();
		notificationRequest.setKey(String.valueOf(budget.get().getId()));
		notificationRequest.setType(NotificationType.BUDGET_ACCEPTED);
		notificationService.notifySupplier(supplier, notificationRequest);
	}
}
