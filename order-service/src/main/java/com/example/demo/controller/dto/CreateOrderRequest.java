package com.example.demo.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.OrderLine;
import com.example.demo.model.PurchaseOrder;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateOrderRequest {

	private long customerId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orderDate;
	private List<OrderLineDto> lineItems;

	public CreateOrderRequest() {
		// TODO Auto-generated constructor stub
	}

	public CreateOrderRequest(long customerId, LocalDateTime orderDate, List<OrderLineDto> lineItems) {
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.lineItems = lineItems;
	}

	public PurchaseOrder toOrder() {
		List<OrderLine> lines = null;
		if (lineItems != null) {
			lines = lineItems.stream().map(l -> new OrderLine(l.getItem(), l.getQuantity(), l.getTotalPrice()))
					.collect(Collectors.toList());
		}
		return new PurchaseOrder(customerId, orderDate, lines);
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderLineDto> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<OrderLineDto> lineItems) {
		this.lineItems = lineItems;
	}

}
