package com.abe.shopkeeper.notification.model.resource;

import org.springframework.hateoas.ResourceSupport;

public abstract class ResourceBase<ModelItem, Resource> extends ResourceSupport {
	public abstract Resource prepare(ModelItem modelItem);
}
