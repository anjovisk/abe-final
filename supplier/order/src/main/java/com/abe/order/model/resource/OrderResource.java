package com.abe.order.model.resource;

import com.abe.order.model.Order;
import com.abe.order.model.Order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class OrderResource  extends ResourceBase<Order, OrderResource> {
	@ApiModelProperty
	private Long id;
	@ApiModelProperty
	private Long budgetId;
	@ApiModelProperty
	private OrderStatus status;
	
	public OrderResource() {
		
	}

	@Override
	public OrderResource prepare(Order modelItem) {
		this.id = modelItem.getId();
		this.budgetId = modelItem.getBudgetId();
		this.status = modelItem.getStatus();
		return this;
	}

	@JsonProperty("id")
	public Long getOrderId() {
		return id;
	}

	public Long getBudgetId() {
		return budgetId;
	}

	public OrderStatus getStatus() {
		return status;
	}
}
