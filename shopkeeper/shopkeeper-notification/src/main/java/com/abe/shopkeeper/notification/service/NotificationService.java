package com.abe.shopkeeper.notification.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.abe.shopkeeper.notification.model.Notification;
import com.abe.shopkeeper.notification.model.NotificationRequest;
import com.abe.shopkeeper.notification.util.DataContainer;

@Service
public class NotificationService {
	private static Map<Long, List<Notification>> notifications = new HashMap<>();
	
	public Notification create(Long supplier, NotificationRequest notificationRequest) {
		Notification notification = new Notification(notificationRequest);
		if (!notifications.containsKey(supplier)) {
			notifications.put(supplier, new ArrayList<Notification>());
		}
		notifications.get(supplier).add(notification);
		System.out.println(String.format("Notification id: %s", notification.getId()));
		return notification;
	}
	
	public DataContainer<Notification> find(Long supplier, int limit, int offset) {
		if (!notifications.containsKey(supplier)) {
			notifications.put(supplier, new ArrayList<Notification>());
		}
		List<Notification> filteredList = notifications.get(supplier);
		DataContainer<Notification> result = new DataContainer<Notification>(limit, offset, filteredList.size(), filteredList.subList(offset, (offset+limit <= filteredList.size() ? offset+limit : filteredList.size())));
		return result;
	}
	
	public Optional<Notification> getNotification(Long supplier, Long notificationId) {
		if (!notifications.containsKey(supplier)) {
			notifications.put(supplier, new ArrayList<Notification>());
		}
		Optional<Notification> result = notifications.get(supplier).stream().filter(order ->
			order.getId().equals(notificationId)
		).findFirst();
		return result;
	}
}
