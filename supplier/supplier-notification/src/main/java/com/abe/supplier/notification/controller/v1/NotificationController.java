package com.abe.supplier.notification.controller.v1;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abe.supplier.notification.model.Notification;
import com.abe.supplier.notification.model.NotificationRequest;
import com.abe.supplier.notification.model.resource.NotificationResource;
import com.abe.supplier.notification.model.resource.mapper.ResourceMapper;
import com.abe.supplier.notification.service.NotificationService;
import com.abe.supplier.notification.util.DataContainer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("NotificationControllerV1")
@ExposesResourceFor(Notification.class)
@RequestMapping("/v1/public/suppliers/{supplier}/notifications")
@Api(tags = {"Notifications"})
public class NotificationController {
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private NotificationService notificationService;
	
	@ApiOperation(value = "Lista as notificações de um atacadista")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<DataContainer<NotificationResource>>  getNotifications(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier,
			@ApiParam(required = false, value = "Quantidade máxima de notificações retornadas na requisição", defaultValue = "10") @RequestParam(name="limit", required=false, defaultValue = "10") int limit,
			@ApiParam(required = false, value = "Quantidade de notificações ignoradas na pesquisa", defaultValue = "0") @RequestParam(name="offset", required=false, defaultValue = "0") int offset) {
		Collection<NotificationResource> notifications = resourceMapper.toResourceCollection(notificationService.find(supplier, limit, offset), NotificationResource.class);
		DataContainer<NotificationResource> result = new DataContainer<NotificationResource>(limit, offset, notifications.size(), notifications);
		result.getData().forEach(notificationResource ->{
			notificationResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(NotificationController.class).getNotification(supplier, notificationResource.getNotificationId())).withSelfRel());
		});
		return ResponseEntity.accepted().body(result);
	}
	
	@ApiOperation(value = "Obtém uma notificação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Notificação não encontrada.")
	})
	@RequestMapping(path = "{id}", method=RequestMethod.GET)
	public ResponseEntity<NotificationResource> getNotification(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Código da notificação") @PathVariable("id") Long id) {
		Optional<Notification> notification = notificationService.getNotification(supplier, id);
		NotificationResource notificationResource = null;
		if (notification.isPresent()) {
			notificationResource = resourceMapper.toResource(notification.get(), NotificationResource.class);
			notificationResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(NotificationController.class).getNotification(supplier, notificationResource.getNotificationId())).withSelfRel());
		}
		return notification.isPresent() 
				? ResponseEntity.accepted().body(notificationResource)
						: ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Cria uma notificação")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Notificação criada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Notification> create(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Notificação") @RequestBody(required = true) NotificationRequest notificationRequest) {
		Notification notification = notificationService.create(supplier, notificationRequest);
		return ResponseEntity.accepted().body(notification);
	}
}
