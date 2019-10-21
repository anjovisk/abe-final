package com.abe.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class SupplierNotificationRequest {
	@ApiModel
	public enum NotificationType {
		@ApiModelProperty(notes = "Orçamento solicitado")
		BUDGET_REQUESTED,
		@ApiModelProperty(notes = "Orçamento aprovado")
		BUDGET_ACCEPTED,
		@ApiModelProperty(notes = "Orçamento rejeitado")
		BUDGET_REJECTED
	}
	
	@ApiModelProperty
	private NotificationType type;
	@ApiModelProperty
	private String key;
	
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
