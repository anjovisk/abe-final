package com.abe.stock.model.resourece;

import org.springframework.hateoas.ResourceSupport;

public abstract class ResourceBase<ModelItem, Resource> extends ResourceSupport {
	public abstract Resource prepare(ModelItem modelItem);
}
