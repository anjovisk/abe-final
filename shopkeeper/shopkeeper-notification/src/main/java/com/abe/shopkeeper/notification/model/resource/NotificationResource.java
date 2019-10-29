package com.abe.shopkeeper.notification.model.resource;

import java.time.LocalDateTime;

import com.abe.shopkeeper.notification.model.Notification;
import com.abe.shopkeeper.notification.model.Notification.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class NotificationResource extends ResourceBase<Notification, NotificationResource> {
	@ApiModelProperty
	private Long id;
	@ApiModelProperty
	private NotificationType type;
	@ApiModelProperty
	private String key;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
	@ApiModelProperty(notes = "Data em que a notificação foi gerada yyyy-MM-dd HH-mm-ss", example = "2019-10-10 10:10:10")
	private LocalDateTime date;
	@ApiModelProperty
	private String description;
	
	@Override
	public NotificationResource prepare(Notification modelItem) {
		this.id = modelItem.getId();
		this.type = modelItem.getType();
		this.key = modelItem.getKey();
		this.date = modelItem.getDate();
		this.description = modelItem.getDescription();
		return this;
	}

	@JsonProperty("id")
	public Long getNotificationId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
