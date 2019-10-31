package com.abe.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.abe.order.model.Budget;
import com.abe.order.model.BudgetRequest;

@Service
public class BudgetServiceBase {
	protected static Map<Long, List<BudgetRequest>> budgetRequests = new HashMap<>();
	protected static Map<Long, List<Budget>> budgets = new HashMap<>();
	
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
	
	public Optional<BudgetRequest> getBudgetRequest(Long client, Long supplier) {
		if (!budgetRequests.containsKey(supplier)) {
			budgetRequests.put(supplier, new ArrayList<BudgetRequest>());
		}
		Optional<BudgetRequest> result = budgetRequests.get(supplier).stream().filter(request ->
			request.getClient().equals(client)
		).findFirst();
		return result;
	}
}
