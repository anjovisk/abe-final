package com.abe.supplier.notification.model.resource;

import java.time.LocalDateTime;import com.abe.supplier.notification.model.Notification;
import com.abe.supplier.notification.model.Notification.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
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

	public Long getNotificationId() {
		return id;
	}

	public NotificationType getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}
	
}
