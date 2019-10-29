package com.abe.stock.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abe.stock.model.Product;
import com.abe.stock.model.resourece.ProductResource;
import com.abe.stock.model.resourece.mapper.ResourceMapper;
import com.abe.stock.service.StockService;
import com.abe.stock.util.DataContainer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("StockControllerV1")
@RequestMapping("/v1/public/shopkeepers/{shopkeeper}/products")
@Api(tags = {"Stock"})
public class StockController {
	@Autowired
	private StockService stockService;
	@Autowired
	private ResourceMapper resourceMapper;
	
	@ApiOperation(value = "Lista os produtos do estoque")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<DataContainer<ProductResource>>  getProducts(
			@ApiParam(required = true, value = "Código do atacadista") @PathVariable("shopkeeper") Long shopkeeper,
			@ApiParam(required = false, value = "Código dos produtos") @RequestParam(name="ids", required=false) List<Long> ids,
			@ApiParam(required = false, value = "Nome do produto") @RequestParam(name="name", required=false) String name,
			@ApiParam(required = false, value = "Quantidade máxima de produtos retornados na requisição", defaultValue = "10") @RequestParam(name="limit", required=false, defaultValue = "10") int limit,
			@ApiParam(required = false, value = "Quantidade de produtos ignorados na pesquisa", defaultValue = "0") @RequestParam(name="offset", required=false, defaultValue = "0") int offset) {
		DataContainer<Product> products = null;
		if ((ids != null) && (!ids.isEmpty())) {
			products = stockService.find(shopkeeper, ids, limit, offset);
		} else {
			Product parameters = new Product();
			parameters.setName(name);
			products = stockService.find(shopkeeper, parameters, limit, offset);
		}
		DataContainer<ProductResource> result = new DataContainer<>(limit, offset, products.getTotal(), resourceMapper.toResourceCollection(products.getData(), ProductResource.class));
		result.getData().forEach(productResource -> {
			productResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StockController.class).getProduct(shopkeeper, productResource.getProductId())).withSelfRel());
		});
		return ResponseEntity.accepted().body(result);
	}
	
	@ApiOperation(value = "Obtém um dos produtos do estoque")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Produto não encontrada.")
	})
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ProductResource> getProduct(
			@ApiParam(required = true, value = "Código do atacadista") @PathVariable("shopkeeper") Long shopkeeper,
			@ApiParam(required = false, value = "Código do produto") @RequestParam(name="id", required=false) Long id) {
		Optional<Product> product = stockService.getAvailableProduct(shopkeeper, id);
		ProductResource productResource = null;
		if (product.isPresent()) {
			productResource = resourceMapper.toResource(product.get(), ProductResource.class);
			productResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(StockController.class).getProduct(shopkeeper, productResource.getProductId())).withSelfRel());
		}
		return product.isPresent()
				? ResponseEntity.accepted().body(productResource)
						: ResponseEntity.notFound().build();
	}
}
