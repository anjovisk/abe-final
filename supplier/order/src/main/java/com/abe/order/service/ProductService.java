package com.abe.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.abe.order.model.Product;
import com.abe.order.util.DataContainer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class ProductService {
	@Autowired
	private Environment env;
	
	public List<Product> getProducts(List<Long> productIds) {
		RestTemplate restTemplate = new RestTemplate();
		String productsUrl = env.getProperty("catalog.products.url");
		List<Product> result = new ArrayList<Product>();
		while (productIds.size() > 0) {
			int limit = 10;
			List<Long> sublist = productIds.subList(0, productIds.size() < limit ? productIds.size() : 10);
			StringBuilder ids = new StringBuilder();
			sublist.stream().forEach(item -> ids.append(String.format("ids=%s&", item.toString())));
			String url = String.format("%s?%s", productsUrl, ids.toString());
			ResponseEntity<DataContainer<Product>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<DataContainer<Product>>(){});
			DataContainer<Product> products = response.getBody();			
			productIds.removeAll(sublist);
			if ((products.getData() != null) & (products.getData().size() > 0)) {
				result.addAll(products.getData());
			}
		}
		return result;
	}
}
