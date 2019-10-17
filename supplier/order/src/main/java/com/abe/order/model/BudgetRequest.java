package com.abe.order.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BudgetRequest {
	@ApiModelProperty
	private List<BudgetItem> items;
	@ApiModelProperty
	private Long client;
	public List<BudgetItem> getItems() {
		return items;
	}
	public void setItems(List<BudgetItem> items) {
		this.items = items;
	}
	public Long getClient() {
		return client;
	}
	public void setClient(Long client) {
		this.client = client;
	}
}
