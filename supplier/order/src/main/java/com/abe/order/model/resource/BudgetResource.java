package com.abe.order.model.resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.abe.order.model.BudgetItem;
import com.abe.order.model.Budget;
import com.abe.order.model.Budget.BudgetStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class BudgetResource extends  ResourceBase<Budget, BudgetResource> {
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

	@JsonProperty("id")
	public Long getBudgetId() {
		return id;
	}

	public List<BudgetItem> getItems() {
		return items;
	}

	public Long getSupplier() {
		return supplier;
	}

	public Long getClient() {
		return client;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public BudgetStatus getStatus() {
		return status;
	}

	public LocalDate getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public String getBudgetNumber() {
		return budgetNumber;
	}

	public String getWebhookUrl() {
		return webhookUrl;
	}

	@Override
	public BudgetResource prepare(Budget modelItem) {
		this.id = modelItem.getId();
		this.items = modelItem.getItems();
		this.supplier = modelItem.getSupplier();
		this.client = modelItem.getClient();
		this.total = modelItem.getTotal();
		this.status = modelItem.getStatus();
		this.estimatedDeliveryDate = modelItem.getEstimatedDeliveryDate();
		this.budgetNumber = modelItem.getBudgetNumber();
		this.webhookUrl = modelItem.getWebhookUrl();
		return this;
	}
}
