package com.abe.order.controller.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abe.order.model.Order;
import com.abe.order.model.Order.OrderStatus;
import com.abe.order.model.resource.OrderResource;
import com.abe.order.model.resource.mapper.ResourceMapper;
import com.abe.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("OrderControllerV1")
@ExposesResourceFor(Order.class)
@RequestMapping("/v1/public/suppliers/{supplier}/orders")
@Api(tags = {"Orders"})
public class OrderController {
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private OrderService orderService;
	
	@ApiOperation(value = "Obtém um pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Pedido não encontrado.")
	})
	@RequestMapping(path = "{id}", method=RequestMethod.GET)
	public ResponseEntity<OrderResource> getOrder(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Código do pedido") @PathVariable("id") Long id) {
		Optional<Order> order = orderService.getOrder(supplier, id);
		OrderResource orderResource = null;
		if (order.isPresent()) {
			orderResource = resourceMapper.toResource(order.get(), OrderResource.class);
			orderResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(OrderController.class).getOrder(supplier, id)).withSelfRel());
		}
		return order.isPresent() 
				? ResponseEntity.accepted().body(orderResource)
						: ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Altera o status de um pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação processada com sucesso.")
	})
	@RequestMapping(path = "{id}/status", method=RequestMethod.POST)
	public ResponseEntity<?> updateStatus(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier,
			@ApiParam(required = true, value = "Código do pedido") @PathVariable("id") Long id,
			@ApiParam(required = true, value = "Status do pedido") @RequestBody(required = true) OrderStatus orderStatus) {
		orderService.updateStatus(supplier, id, orderStatus);
		return ResponseEntity.accepted().build();
	}
}
