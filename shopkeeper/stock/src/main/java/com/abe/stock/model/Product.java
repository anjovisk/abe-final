package com.abe.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.hateoas.Identifiable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class Product implements Identifiable<Serializable> {
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
