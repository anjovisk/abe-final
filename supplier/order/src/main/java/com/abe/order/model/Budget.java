package com.abe.order.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class Budget {
	private static Long ghostId = 0l;
	
	@ApiModel
	public enum BudgetStatus {
		@ApiModelProperty(notes = "Orçamento pendente de aprovação")
		PENDING, 
		@ApiModelProperty(notes = "Orçamento aprovado")
		ACCEPTED,
		@ApiModelProperty(notes = "Orçamento reprovado")
		REJECTED
	}
	
	public Budget(BudgetRequest budgetRequest, Long supplier, String budgetNumber, BigDecimal total, LocalDate estimatedDeliveryDate) {
		this.id = ++ghostId;
		this.status = BudgetStatus.PENDING;
		this.items = budgetRequest.getItems();
		this.client = budgetRequest.getClient();
		this.webhookUrl = budgetRequest.getWebhookUrl();
		this.supplier = supplier;
		this.total = total;
		this.estimatedDeliveryDate = estimatedDeliveryDate;
		this.budgetNumber = budgetNumber;
	}
	
	@ApiModelProperty
	private Long id;
	@ApiModelProperty
	private List<BudgetItem> items;
	@ApiModelProperty
	private Long supplier;
	@ApiModelProperty
	private Long client;
	@ApiModelProperty
	private BigDecimal total;
	@ApiModelProperty
	private BudgetStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(notes = "Data estimada da entrega yyyy-MM-dd", example = "2019-09-21")
	private LocalDate estimatedDeliveryDate;
	@ApiModelProperty
	private String budgetNumber;
	@ApiModelProperty
	private String webhookUrl;
	
	public List<BudgetItem> getItems() {
		return items;
	}
	public void setItems(List<BudgetItem> items) {
		this.items = items;
	}
	public Long getSupplier() {
		return supplier;
	}
	public void setSupplier(Long supplier) {
		this.supplier = supplier;
	}
	public Long getClient() {
		return client;
	}
	public void setClient(Long client) {
		this.client = client;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public BudgetStatus getStatus() {
		return status;
	}
	public void setStatus(BudgetStatus status) {
		this.status = status;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getWebhookUrl() {
		return webhookUrl;
	}
	public void setWebhookUrl(String webhookUrl) {
		this.webhookUrl = webhookUrl;
	}
}
