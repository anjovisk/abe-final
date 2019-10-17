package com.abe.order.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class SupplierAnswer {
	@ApiModelProperty
	private Long client;
	@ApiModelProperty
	private String budgetNumber;
	@ApiModelProperty
	private BigDecimal discount;
	@ApiModelProperty
	private LocalDate estimatedDeliveryDate;
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public LocalDate getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}
	public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}
	public String getBudgetNumber() {
		return budgetNumber;
	}
	public void setBudgetNumber(String budgetNumber) {
		this.budgetNumber = budgetNumber;
	}
	public Long getClient() {
		return client;
	}
	public void setClient(Long client) {
		this.client = client;
	}
}
