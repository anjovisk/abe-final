package com.abe.order.model.resource.mapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.hateoas.Identifiable;
import org.springframework.stereotype.Component;

import com.abe.order.model.resource.ResourceBase;

@Component
public class ResourceMapper {
	public <T extends Identifiable<Serializable>, Resource extends ResourceBase<T, Resource>> Resource toResource(T item, Class<Resource> clazz) {
		Resource resource;
		try {
			resource = clazz.newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
		resource = resource.prepare(item);
		return resource;
	}
	
	public <T extends Identifiable<Serializable>, Resource extends ResourceBase<T, Resource>> Collection<Resource> toResourceCollection(Collection<T> domainObjects, Class<Resource> clazz) {
		return domainObjects.stream()
				.map(t -> toResource(t, clazz))
				.collect(Collectors.toList());
	}
}