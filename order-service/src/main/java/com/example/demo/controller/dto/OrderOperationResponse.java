package com.example.demo.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.PurchaseOrder;

import lombok.Getter;

@Getter
public class OrderOperationResponse {

	private final long id;
	private final long customerId;
	private final LocalDateTime orderDate;
	private final List<OrderLineDto> lineItems;

	public OrderOperationResponse(long id, long customerId, LocalDateTime orderDate, List<OrderLineDto> lineItems) {
		this.id = id;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.lineItems = lineItems;
	}

	public static OrderOperationResponse from(PurchaseOrder order) {
		List<OrderLineDto> lines = order.getLineItems().stream()
				.map(l -> new OrderLineDto(l.getId(), l.getItem(), l.getQuantity(), l.getTotalPrice(), l.getStatus()))
				.collect(Collectors.toList());

		return new OrderOperationResponse(order.getId(), order.getCustomerId(), order.getOrderDate(), lines);
	}

}
