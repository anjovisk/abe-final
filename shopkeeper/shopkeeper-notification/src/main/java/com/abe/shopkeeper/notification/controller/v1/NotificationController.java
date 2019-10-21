package com.abe.shopkeeper.notification.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abe.shopkeeper.notification.model.Notification;
import com.abe.shopkeeper.notification.model.NotificationRequest;
import com.abe.shopkeeper.notification.service.NotificationService;
import com.abe.shopkeeper.notification.util.DataContainer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("NotificationControllerV1")
@RequestMapping("/v1/public/shopkeepers/{shopkeeper}/notifications")
@Api(tags = {"Notifications"})
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@ApiOperation(value = "Lista as notificações de um atacadista")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<DataContainer<Notification>>  getNotifications(
			@ApiParam(required = true, value = "Código do atacadista") @PathVariable("shopkeeper") Long shopkeeper,
			@ApiParam(required = false, value = "Quantidade máxima de notificações retornadas na requisição", defaultValue = "10") @RequestParam(name="limit", required=false, defaultValue = "10") int limit,
			@ApiParam(required = false, value = "Quantidade de notificações ignoradas na pesquisa", defaultValue = "0") @RequestParam(name="offset", required=false, defaultValue = "0") int offset) {
		return ResponseEntity.accepted().body(notificationService.find(shopkeeper, limit, offset));
	}
	
	@ApiOperation(value = "Obtém uma notificação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Notificação não encontrada.")
	})
	@RequestMapping(path = "{id}", method=RequestMethod.GET)
	public ResponseEntity<Notification> getBudget(
			@ApiParam(required = true, value = "Código do atacadista") @PathVariable("shopkeeper") Long shopkeeper, 
			@ApiParam(required = true, value = "Código da notificação") @PathVariable("id") Long id) {
		Optional<Notification> notification = notificationService.getNotification(shopkeeper, id);
		return notification.isPresent() 
				? ResponseEntity.accepted().body(notification.get())
						: ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Cria uma notificação")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Notificação criada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Notification> requestBudget(
			@ApiParam(required = true, value = "Código do atacadista") @PathVariable("shopkeeper") Long shopkeeper, 
			@ApiParam(required = true, value = "Notificação") @RequestBody(required = true) NotificationRequest notificationRequest) {
		Notification notification = notificationService.create(shopkeeper, notificationRequest);
		return ResponseEntity.accepted().body(notification);
	}
}
