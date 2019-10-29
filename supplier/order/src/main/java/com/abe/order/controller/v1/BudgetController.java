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

import com.abe.order.exception.BudgetNotFoundException;
import com.abe.order.model.Budget;
import com.abe.order.model.BudgetRequest;
import com.abe.order.model.SupplierAnswer;
import com.abe.order.model.resource.BudgetResource;
import com.abe.order.model.resource.mapper.ResourceMapper;
import com.abe.order.service.BudgetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("BudgetControllerV1")
@ExposesResourceFor(Budget.class)
@RequestMapping("/v1/public/suppliers/{supplier}/budgets")
@Api(tags = {"Budgets"})
public class BudgetController {
	@Autowired
	private ResourceMapper resourceMapper;
	@Autowired
	private BudgetService budgetService;
	
	@ApiOperation(value = "Solicita um orçamento")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Solicitação de orçamento criada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> requestBudget(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Solicitação de orçamento") @RequestBody(required = true) BudgetRequest budgetRequest) {
		budgetService.requestBudget(supplier, budgetRequest);
		return ResponseEntity.accepted().build();
	}
	
	@ApiOperation(value = "Responde uma solicitação de orçamento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação de processada com sucesso.")
	})
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<?> answerBudget(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Resposta do fornecedor") @RequestBody(required = true) SupplierAnswer supplierAnswer) {
		budgetService.createBudget(supplier, supplierAnswer);
		return ResponseEntity.accepted().build();
	}
	
	@ApiOperation(value = "Obtém um orçamento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Orçamento não encontrado.")
	})
	@RequestMapping(path = "{id}", method=RequestMethod.GET)
	public ResponseEntity<BudgetResource> getBudget(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Código do orçamento") @PathVariable("id") Long id) {
		Optional<Budget> budget = budgetService.getBudget(supplier, id);
		BudgetResource budgetResource = null;
		if (budget.isPresent()) {
			budgetResource = resourceMapper.toResource(budget.get(), BudgetResource.class);
			budgetResource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(BudgetController.class).getBudget(supplier, id)).withSelfRel());
		}
		return budget.isPresent() 
				? ResponseEntity.accepted().body(budgetResource)
						: ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Reprova um orçamento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Orçamento não encontrado.")
	})
	@RequestMapping(path = "{id}/approval", method=RequestMethod.DELETE)
	public ResponseEntity<?> rejectBudget(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Código do orçamento") @PathVariable("id") Long id) {
		try {
			budgetService.rejectBudget(supplier, id);
		} catch (BudgetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.accepted().build();
	}
	
	@ApiOperation(value = "Aprova um orçamento")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Solicitação procecessada com sucesso."),
			@ApiResponse(code = 404, message = "Orçamento não encontrado.")
	})
	@RequestMapping(path = "{id}/approval", method=RequestMethod.POST)
	public ResponseEntity<?> acceptBudget(
			@ApiParam(required = true, value = "Código do fornecedor") @PathVariable("supplier") Long supplier, 
			@ApiParam(required = true, value = "Código do orçamento") @PathVariable("id") Long id) {
		try {
			budgetService.acceptBudget(supplier, id);
		} catch (BudgetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.accepted().build();
	}
}
