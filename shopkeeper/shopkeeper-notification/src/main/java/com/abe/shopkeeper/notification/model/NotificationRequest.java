package com.abe.shopkeeper.notification.model;

import com.abe.shopkeeper.notification.model.Notification.NotificationType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class NotificationRequest {
	public NotificationRequest() {
		
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
