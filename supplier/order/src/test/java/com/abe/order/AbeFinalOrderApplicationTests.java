package com.abe.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.abe.order.model.Budget;
import com.abe.order.model.BudgetItem;
import com.abe.order.model.BudgetRequest;
import com.abe.order.model.Order;
import com.abe.order.model.Order.OrderStatus;
import com.abe.order.model.SupplierAnswer;
import com.abe.order.model.Budget.BudgetStatus;
import com.abe.order.service.BudgetService;
import com.abe.order.service.OrderService;

/**
 * @author imarques
 *
 *Para executar os testes, é necessário certificar
 *que os containers com os microsserviços estejam em execução.
 *Isso é necessário para possibilitar a comunicação
 *com os demais microsserviços.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AbeFinalOrderApplicationTests {
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private OrderService orderService;
	
	private final Long CLIENT_ID = 1L;
	private final Long SUPPLIER_ID = 11L;
	private final String WEBHOOK_URL = String.format("http://localhost:9090/notification/v1/public/shopkeepers/%s/notifications", CLIENT_ID);
	private final String BUDGET_NUMBER = "N111";
	private final BigDecimal DISCOUNT = BigDecimal.valueOf(10);
	
	@Test
	public void whenBudgetAccepted_thenOrderShouldBeFound() {
		requestBudget();
		
		Optional<BudgetRequest> budgetRequest = budgetService.getBudgetRequest(CLIENT_ID, SUPPLIER_ID);
		assertThat(budgetRequest).isPresent();
		
		createBudget();
		
		budgetRequest = budgetService.getBudgetRequest(CLIENT_ID, SUPPLIER_ID);
		assertThat(budgetRequest).isNotPresent();
		
		Optional<Budget> budget = budgetService.getBudget(SUPPLIER_ID, 1L);
		assertThat(budget).isPresent();
		
		assertEquals(budget.get().getStatus(), BudgetStatus.PENDING);
		
		budgetService.acceptBudget(SUPPLIER_ID, 1L);
		
		assertEquals(budget.get().getStatus(), BudgetStatus.ACCEPTED);
		
		Optional<Order> order = orderService.getOrder(SUPPLIER_ID, 1L);
		assertThat(order).isPresent();
		
		orderService.updateStatus(SUPPLIER_ID, order.get().getId(), OrderStatus.REQUESTED);
		assertEquals(order.get().getStatus(), OrderStatus.REQUESTED);
		
		orderService.updateStatus(SUPPLIER_ID, order.get().getId(), OrderStatus.IN_PROGRESS);
		assertEquals(order.get().getStatus(), OrderStatus.IN_PROGRESS);
		
		orderService.updateStatus(SUPPLIER_ID, order.get().getId(), OrderStatus.DONE);
		assertEquals(order.get().getStatus(), OrderStatus.DONE);
		
		orderService.updateStatus(SUPPLIER_ID, order.get().getId(), OrderStatus.SENT);
		assertEquals(order.get().getStatus(), OrderStatus.SENT);
	}
	
	@Test
	public void whenBudgetRejected_thenOrderShouldNotBeFound() {
		requestBudget();
		createBudget();
		
		budgetService.rejectBudget(SUPPLIER_ID, 2L);
		
		Optional<Budget> budget = budgetService.getBudget(SUPPLIER_ID, 2L);
		assertThat(budget).isPresent();
		
		assertEquals(budget.get().getStatus(), BudgetStatus.REJECTED);
		
		Optional<Order> order = orderService.getOrder(SUPPLIER_ID, 2L);
		assertThat(order).isNotPresent();
	}
	
	private void requestBudget() {
		BudgetRequest budgetRequest = new BudgetRequest();
		budgetRequest.setClient(CLIENT_ID);
		budgetRequest.setItems(new ArrayList<BudgetItem>());
		budgetRequest.setWebhookUrl(WEBHOOK_URL);
		budgetService.requestBudget(SUPPLIER_ID, budgetRequest);
	}
	
	private void createBudget() {
		SupplierAnswer supplierAnswer = new SupplierAnswer();
		supplierAnswer.setBudgetNumber(BUDGET_NUMBER);
		supplierAnswer.setClient(CLIENT_ID);
		supplierAnswer.setDiscount(DISCOUNT);
		supplierAnswer.setEstimatedDeliveryDate(LocalDate.now());
		budgetService.createBudget(SUPPLIER_ID, supplierAnswer);
	}
}
