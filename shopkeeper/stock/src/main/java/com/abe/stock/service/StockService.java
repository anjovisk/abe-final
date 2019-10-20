package com.abe.stock.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.abe.stock.model.Product;
import com.abe.stock.util.DataContainer;

@Service
public class StockService {
	private static List<Product> products;
	private static int productsCount = 100;
	
	@PostConstruct
	public void populateProducts() {
		Random random = new Random();
		products = new ArrayList<>();
		for (int i = 0; i < productsCount; i++) {
			Product product = new Product();
			product.setId((long)i);
			product.setDescription(String.format("Description %s", i));
			product.setName(String.format("Name %s", i));
			product.setPrice(BigDecimal.valueOf(i));
			product.setCount(random.nextInt(10));
			products.add(product);
		}
	}
	
	public Optional<Product> getAvailableProduct(Long id) {
		Optional<Product> result = products.stream().filter(product -> filterById(product, id)).findFirst();
		return result;
	}
	
	public DataContainer<Product> find(Product parameter, int limit, int offset) {
		List<Product> filteredList = products.stream().filter(product ->
			filterById(product, parameter.getId())
			&& filterByName(product, parameter.getName())
		).collect(Collectors.toList());
		DataContainer<Product> result = new DataContainer<Product>(limit, offset, filteredList.size(), filteredList.subList(offset, (offset+limit <= filteredList.size() ? offset+limit : filteredList.size())));
		return result;
	}
	
	public DataContainer<Product> find(List<Long> ids, int limit, int offset) {
		List<Product> filteredList = products.stream().filter(product ->
			ids.contains(product.getId())
		).collect(Collectors.toList());
		DataContainer<Product> result = new DataContainer<Product>(limit, offset, filteredList.size(), filteredList.subList(offset, (offset+limit <= filteredList.size() ? offset+limit : filteredList.size())));
		return result;
	}
	
	private boolean filterById(Product item, Long id) {
		return id == null || item.getId().equals(id);
	}
	
	private boolean filterByName(Product item, String name) {
		return name == null || item.getName().toUpperCase().contains(name.toUpperCase());
	}
}
