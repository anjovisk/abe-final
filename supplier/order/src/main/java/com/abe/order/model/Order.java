package com.abe.order.model;

import java.io.Serializable;

import org.springframework.hateoas.Identifiable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Order implements Identifiable<Serializable> {
	private static Long ghostId = 0l;
	
	@ApiModel
	public enum OrderStatus {
		@ApiModelProperty(notes = "Solicitado")
		REQUESTED, 
		@ApiModelProperty(notes = "Em fabricação")
		IN_PROGRESS,
		@ApiModelProperty(notes = "Finalizado")
		DONE,
		@ApiModelProperty(notes = "Despachado")
		SENT
	}
	
	public Order() {
		
	}
	
	public Order(Long budgetId) {
		this.id = ++ghostId;
		this.budgetId = budgetId;
	}
	
	@ApiModelProperty
	private Long id;
	@ApiModelProperty
	private Long budgetId;
	@ApiModelProperty
	private OrderStatus status;
	
	public static Long getGhostId() {
		return ghostId;
	}
	public static void setGhostId(Long ghostId) {
		Order.ghostId = ghostId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
