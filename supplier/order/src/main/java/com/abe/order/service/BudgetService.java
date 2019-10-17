package com.abe.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abe.order.exception.BudgetNotFoundException;
import com.abe.order.exception.BudgetRequestException;
import com.abe.order.exception.SupplierAnswerException;
import com.abe.order.model.Budget;
import com.abe.order.model.Budget.BudgetStatus;
import com.abe.order.model.BudgetRequest;
import com.abe.order.model.Product;
import com.abe.order.model.SupplierAnswer;

@Service
public class BudgetService {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	
	private static Map<Long, List<BudgetRequest>> budgetRequests = new HashMap<>();
	private static Map<Long, List<Budget>> budgets = new HashMap<>();
	
	public void requestBudget(Long supplier, BudgetRequest newBudgetRequest) {
		Optional<BudgetRequest> pendingBudgetRequest = getBudgetRequest(newBudgetRequest.getClient(), supplier);
		if (pendingBudgetRequest.isPresent()) {
			throw new BudgetRequestException("Já existe uma solicitação de orçament em andamento.");
		}
		budgetRequests.get(supplier).add(newBudgetRequest);
		//TODO - Notify supplier
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
		System.out.println(String.format("budget id: %s", budget.getId()));
		//TODO - Notify Retailer
	}
	
	private Optional<BudgetRequest> getBudgetRequest(Long client, Long supplier) {
		if (!budgetRequests.containsKey(supplier)) {
			budgetRequests.put(supplier, new ArrayList<BudgetRequest>());
		}
		Optional<BudgetRequest> result = budgetRequests.get(supplier).stream().filter(request ->
			request.getClient().equals(client)
		).findFirst();
		return result;
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
	
	public Optional<Budget> getBudget(Long supplier, Long id) {
		if (!budgets.containsKey(supplier)) {
			budgets.put(supplier, new ArrayList<Budget>());
		}
		Optional<Budget> result = budgets.get(supplier).stream().filter(budget ->
			budget.getId().equals(id)
			&& budget.getSupplier().equals(supplier)
		).findFirst();
		return result;
	}
	
	public void rejectBudget(Long supplier, Long id) {
		Optional<Budget> budget = getPendingBudget(supplier, id);
		if (!budget.isPresent()) {
			throw new BudgetNotFoundException("Orçamento pendente não encontrado.");
		}
		budget.get().setStatus(BudgetStatus.REJECTED);
		//TODO - Notify supplier
	}
	
	public void acceptBudget(Long supplier, Long id) {
		Optional<Budget> budget = getPendingBudget(supplier, id);
		if (!budget.isPresent()) {
			throw new BudgetNotFoundException("Orçamento pendente não encontrado.");
		}
		orderService.create(budget.get());
		budget.get().setStatus(BudgetStatus.ACCEPTED);
		//TODO - Notify supplier
	}
}
