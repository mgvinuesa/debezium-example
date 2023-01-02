package com.example.demo.controller.dto;

import com.example.demo.model.OrderLineStatus;

import lombok.Data;

@Data
public class UpdateOrderLineRequest {

	private OrderLineStatus newStatus;
}
