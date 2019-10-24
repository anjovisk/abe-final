package com.abe.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.abe.order.model.ShopkeeperNotificationRequest;
import com.abe.order.model.SupplierNotificationRequest;

import org.springframework.core.env.Environment;

@Service
public class NotificationService {
	@Autowired
	private Environment env;
	
	public void notifySupplier(Long supplier, SupplierNotificationRequest notificationRequest) {
		RestTemplate restTemplate = new RestTemplate();
		String notificationUrl = String.format(env.getProperty("supplier.notification.url"), supplier);
		restTemplate.postForLocation(notificationUrl, notificationRequest);
	}
	
	public void notifyShopkeeper(ShopkeeperNotificationRequest notificationRequest, String webhookUrl) {
		RestTemplate restTemplate = new RestTemplate();
		String notificationUrl = String.format(webhookUrl);
		restTemplate.postForLocation(notificationUrl, notificationRequest);
	}
}
