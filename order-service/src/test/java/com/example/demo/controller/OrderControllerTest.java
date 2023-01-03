package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.dto.CreateOrderRequest;
import com.example.demo.controller.dto.OrderLineDto;
import com.example.demo.event.listener.InvoiceCreatedEventListener;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@SpyBean
	private InvoiceCreatedEventListener invoiceCreatedEventListener;
	
	@BeforeEach
	void prepareMockMvc() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@Test
	void when_post_new_order_request_then_order_table_is_fullfilled() {
		List<OrderLineDto> orders = new ArrayList<>();
		orders.add(new OrderLineDto(1L, "Door", 1, BigDecimal.valueOf(5.6), null));
		RestAssuredMockMvc.given()
			.log().all()
			.body(new CreateOrderRequest(1,LocalDateTime.now(),orders ))
			.contentType(ContentType.JSON).when().post("/order")
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
		

		RestAssuredMockMvc.given()
			.log().all()
			.get("/order/1")
		.then()
			.statusCode(HttpStatus.OK.value())
			.log().all();
		
		}

	

}
