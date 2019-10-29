package com.abe.catalog.model.resource;

import java.math.BigDecimal;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ProductResource extends ResourceSupport {
	@ApiModelProperty
	private final Long id;
	@ApiModelProperty
	private final String name;
	@ApiModelProperty
	private final String description;
	@ApiModelProperty
	private final BigDecimal price;
	@ApiModelProperty
	private final Boolean available;
	
	public ProductResource(Long id, String name, String description, BigDecimal price, Boolean available) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = available;
	}

	@JsonProperty("id")
	public Long getProductId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Boolean getAvailable() {
		return available;
	}
}
