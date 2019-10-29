package com.abe.stock.model.resourece;

import java.math.BigDecimal;

import com.abe.stock.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ProductResource extends ResourceBase<Product, ProductResource> {
	@ApiModelProperty
	private Long id;
	@ApiModelProperty
	private String name;
	@ApiModelProperty
	private String description;
	@ApiModelProperty
	private BigDecimal price;
	@ApiModelProperty
	private int count;
	
	@Override
	public ProductResource prepare(Product modelItem) {
		this.id = modelItem.getId();
		this.name = modelItem.getName();
		this.description = modelItem.getDescription();
		this.price = modelItem.getPrice();
		this.count = modelItem.getCount();
		return this;
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

	public int getCount() {
		return count;
	}

}
