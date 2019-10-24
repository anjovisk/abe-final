package com.abe.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abe.order.exception.OrderNotFoundException;
import com.abe.order.model.Budget;
import com.abe.order.model.Order;
import com.abe.order.model.ShopkeeperNotificationRequest;
import com.abe.order.model.Order.OrderStatus;

@Service
public class OrderService {
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private BudgetServiceBase budgetServiceBase;
	
	private static Map<Long, List<Order>> orders = new HashMap<>();
	
	public Order create(Budget budget) {
		Order order = new Order(budget.getId());
		if (!orders.containsKey(budget.getSupplier())) {
			orders.put(budget.getSupplier(), new ArrayList<Order>());
		}
		orders.get(budget.getSupplier()).add(order);
		System.out.println(String.format("Order id: %s", order.getId()));
		updateStatus(budget.getSupplier(), order.getId(), OrderStatus.REQUESTED);
		return order;
	}
	
	public Optional<Order> getOrder(Long supplier, Long orderId) {
		if (!orders.containsKey(supplier)) {
			orders.put(supplier, new ArrayList<Order>());
		}
		Optional<Order> result = orders.get(supplier).stream().filter(order ->
			order.getId().equals(orderId)
		).findFirst();
		return result;
	}
	
	public void updateStatus(Long supplier, Long orderId, OrderStatus orderStatus) {
		Optional<Order> order = getOrder(supplier, orderId);
		if (!order.isPresent()) {
			throw new OrderNotFoundException("Pedido não encontrado");
		}
		Optional<Budget> budget = budgetServiceBase.getBudget(supplier, order.get().getBudgetId());
		order.get().setStatus(orderStatus);
		ShopkeeperNotificationRequest notificationRequest = new ShopkeeperNotificationRequest();
		notificationRequest.setKey(String.valueOf(orderId));
		notificationRequest.setType(com.abe.order.model.ShopkeeperNotificationRequest.NotificationType.ORDER_STATUS_CHANGED);
		switch (order.get().getStatus()) {
			case REQUESTED:
				notificationRequest.setDescription("Pedido solicitado.");
				break;
			case DONE:
				notificationRequest.setDescription("Processo de fabricação finalizado.");
				break;
			case IN_PROGRESS:
				notificationRequest.setDescription("Pedido em processo de fabricação.");
				break;
			case SENT:
				notificationRequest.setDescription("Pedido despachado.");
				break;
		}
		notificationService.notifyShopkeeper(notificationRequest, budget.get().getWebhookUrl());
	}
}
