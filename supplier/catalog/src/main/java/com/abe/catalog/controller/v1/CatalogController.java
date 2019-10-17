package com.abe.catalog.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abe.catalog.model.Product;
import com.abe.catalog.service.CatalogService;
import com.abe.catalog.util.DataContainer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("CatalogControllerV1")
@RequestMapping("/v1/public/products")
@Api(tags = {"Catalog"})
public class CatalogController {
	@Autowired
	private CatalogService catalogService;
	
	@ApiOperation(value = "Lista os produtos disponíveis no catálogo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<DataContainer<Product>>  getProducts(
			@ApiParam(required = false, value = "Código dos produtos") @RequestParam(name="ids", required=false) List<Long> ids,
			@ApiParam(required = false, value = "Código do produto") @RequestParam(name="id", required=false) Long id, 
			@ApiParam(required = false, value = "Nome do produto") @RequestParam(name="name", required=false) String name,
			@ApiParam(required = false, value = "Quantidade máxima de produtos retornados na requisição", defaultValue = "10") @RequestParam(name="limit", required=false, defaultValue = "10") int limit,
			@ApiParam(required = false, value = "Quantidade de produtos ignorados na pesquisa", defaultValue = "0") @RequestParam(name="offset", required=false, defaultValue = "0") int offset) {
		if ((ids != null) && (!ids.isEmpty())) {
			return ResponseEntity.accepted().body(catalogService.find(ids, limit, offset));
		} else {
			Product parameters = new Product();
			parameters.setId(id);
			parameters.setAvailable(true);
			parameters.setName(name);
			return ResponseEntity.accepted().body(catalogService.find(parameters, limit, offset));
		}
	}
	
	@ApiOperation(value = "Obtém um dos produtos disponíveis no catálogo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Produto não encontrada.")
	})
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Product> getProduct(
			@ApiParam(required = false, value = "Código do produto") @RequestParam(name="id", required=false) Long id) {
		Optional<Product> product = catalogService.getAvailableProduct(id);
		return product.isPresent()
				? ResponseEntity.accepted().body(product.get())
						: ResponseEntity.notFound().build();
	}
}
