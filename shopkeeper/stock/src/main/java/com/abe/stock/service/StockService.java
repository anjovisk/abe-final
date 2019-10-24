package com.abe.stock.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abe.stock.model.Product;
import com.abe.stock.util.DataContainer;

@Service
public class StockService {
	private static Map<Long, List<Product>> stock = new HashMap<Long, List<Product>>();
	private static int productsCount = 100;
	
	private List<Product> getStock(Long shopkeeper) {
		if (!stock.containsKey(shopkeeper)) {
			Random random = new Random();
			List<Product> products = new ArrayList<>();
			for (int i = 0; i < productsCount; i++) {
				Product product = new Product();
				product.setId((long)i);
				product.setDescription(String.format("Description %s", i));
				product.setName(String.format("Name %s", i));
				product.setPrice(BigDecimal.valueOf(i));
				product.setCount(random.nextInt(10));
				products.add(product);
			}
			stock.put(shopkeeper, products);
		}
		return stock.get(shopkeeper);
	}
	
	public Optional<Product> getAvailableProduct(Long shopkeeper, Long id) {
		Optional<Product> result = getStock(shopkeeper).stream().filter(product -> filterById(product, id)).findFirst();
		return result;
	}
	
	public DataContainer<Product> find(Long shopkeeper, Product parameter, int limit, int offset) {
		List<Product> filteredList = getStock(shopkeeper).stream().filter(product ->
			filterById(product, parameter.getId())
			&& filterByName(product, parameter.getName())
		).collect(Collectors.toList());
		DataContainer<Product> result = new DataContainer<Product>(limit, offset, filteredList.size(), filteredList.subList(offset, (offset+limit <= filteredList.size() ? offset+limit : filteredList.size())));
		return result;
	}
	
	public DataContainer<Product> find(Long shopkeeper, List<Long> ids, int limit, int offset) {
		List<Product> filteredList = getStock(shopkeeper).stream().filter(product ->
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
