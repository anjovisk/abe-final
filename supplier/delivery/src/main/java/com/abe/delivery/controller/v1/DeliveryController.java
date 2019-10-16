package com.abe.delivery.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController("DeliveryControllerV1")
@RequestMapping("/v1/public/deliveries")
@Api(tags = {"Deliveries"})
public class DeliveryController {

}
