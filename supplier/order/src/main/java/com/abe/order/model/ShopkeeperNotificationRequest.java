package com.abe.order.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ShopkeeperNotificationRequest {
	@ApiModel
	public enum NotificationType {
		@ApiModelProperty(notes = "Orçamento criado")
		BUDGET_CREATED,
		@ApiModelProperty(notes = "Status da entrega alterado")
		ORDER_STATUS_CHANGED
	}
	
	@ApiModelProperty
	private NotificationType type;
	@ApiModelProperty
	private String key;
	@ApiModelProperty
	private String description;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
