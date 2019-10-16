package com.abe.order.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController("OrderControllerV1")
@RequestMapping("/v1/public/orders")
@Api(tags = {"Orders"})
public class OrderController {

}
