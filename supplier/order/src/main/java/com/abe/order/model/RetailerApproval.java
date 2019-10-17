package com.abe.order.model;

import io.swagger.annotations.ApiModelProperty;

public class RetailerApproval {
	@ApiModelProperty
	private boolean approved;
	@ApiModelProperty
	private Long budgetId;
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public Long getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}
}
