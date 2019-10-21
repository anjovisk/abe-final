package com.abe.supplier.gateway.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@EnableAutoConfiguration
public class ApiDocumentationController implements SwaggerResourcesProvider {

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		resources.add(toResource("catalog", "/catalog/v2/api-docs", "1.0"));
		resources.add(toResource("order", "/order/v2/api-docs", "1.0"));
		resources.add(toResource("notification", "/notification/v2/api-docs", "1.0"));

		return resources;
	}

	private SwaggerResource toResource(String name, String location, String version) {
		SwaggerResource resource = new SwaggerResource();
		resource.setName(name);
		resource.setLocation(location);
		resource.setSwaggerVersion(version);

		return resource;
	}
}
