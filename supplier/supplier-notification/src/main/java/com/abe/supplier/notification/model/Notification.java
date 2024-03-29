package com.abe.supplier.notification.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Notification  implements Identifiable<Serializable> {
	private static Long ghostId = 0l;
	
	@ApiModel
	public enum NotificationType {
		@ApiModelProperty(notes = "Orçamento solicitado")
		BUDGET_REQUESTED,
		@ApiModelProperty(notes = "Orçamento criado")
		BUDGET_CREATED,
		@ApiModelProperty(notes = "Orçamento aprovado")
		BUDGET_ACCEPTED,
		@ApiModelProperty(notes = "Orçamento rejeitado")
		BUDGET_REJECTED
	}
	
	public Notification() {
		
	}
	
	public Notification(NotificationRequest notificationRequest) {
		this.id = ++ghostId;
		this.type = notificationRequest.getType();
		this.key = notificationRequest.getKey();
		this.description = notificationRequest.getDescription();
		this.date = LocalDateTime.now();
	}
	
	private Long id;
	private NotificationType type;
	private String key;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
	@ApiModelProperty(notes = "Data em que a notificação foi gerada yyyy-MM-dd HH-mm-ss", example = "2019-10-10 10:10:10")
	private LocalDateTime date;
	@ApiModelProperty
	private String description;
	
	public Long getId() {
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
