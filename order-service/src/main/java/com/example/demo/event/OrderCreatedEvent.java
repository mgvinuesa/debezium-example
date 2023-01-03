/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

import com.example.demo.model.OrderLine;
import com.example.demo.model.PurchaseOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * An 'Order' event that indicates an order has been created.
 */
public class OrderCreatedEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8723729060537858944L;

	private static ObjectMapper mapper = new ObjectMapper();

    private final long id;
    private final JsonNode order;

    private OrderCreatedEvent(Object source, long id, JsonNode order) {
    	super(source);
        this.id = id;
        this.order = order;
    }

    public static OrderCreatedEvent of(Object source, PurchaseOrder order) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("id", order.getId())
                .put("customerId", order.getCustomerId())
                .put("orderDate", order.getOrderDate().toString());

        ArrayNode items = asJson.putArray("lineItems");

        for (OrderLine orderLine : order.getLineItems()) {
            ObjectNode lineAsJon = mapper.createObjectNode()
                    .put("id", orderLine.getId())
                    .put("item", orderLine.getItem())
                    .put("quantity", orderLine.getQuantity())
                    .put("totalPrice", orderLine.getTotalPrice())
                    .put("status", orderLine.getStatus().name());

            items.add(lineAsJon);
        }

        return new OrderCreatedEvent(source, order.getId(), asJson);
    }

    public String getAggregateId() {
        return String.valueOf(id);
    }

    public String getAggregateType() {
        return "Order";
    }

    public JsonNode getPayload() {
        return order;
    }

    public String getType() {
        return "OrderCreated";
    }

}
