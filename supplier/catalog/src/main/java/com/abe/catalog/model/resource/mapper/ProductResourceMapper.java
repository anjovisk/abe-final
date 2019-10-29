package com.abe.catalog.model.resource.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.abe.catalog.model.Product;
import com.abe.catalog.model.resource.ProductResource;

@Component
public class ProductResourceMapper {
	@Autowired
	private EntityLinks entityLinks;
	
	public ProductResource toResource(Product product) {
		ProductResource resource = new ProductResource(
				product.getId(), 
				product.getName(), 
				product.getDescription(), 
				product.getPrice(), 
				product.getAvailable());
		final Link selfLink = entityLinks.linkToSingleResource(product);
		resource.add(selfLink.withSelfRel());
		return resource;
	}
	
	public Collection<ProductResource> toResourceCollection(Collection<Product> domainObjects) {
		return domainObjects.stream()
				.map(t -> toResource(t))
				.collect(Collectors.toList());
	}
}
