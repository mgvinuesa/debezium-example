/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.example.demo.event;

import java.time.Instant;

import org.springframework.context.ApplicationEvent;

import com.example.demo.model.OrderLineStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An 'Order' event that indicates an order line's status has changed.
 */
public class OrderLineUpdatedEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 829952777013416550L;

	private static ObjectMapper mapper = new ObjectMapper();

    private final long orderId;
    private final long orderLineId;
    private final OrderLineStatus newStatus;
    private final OrderLineStatus oldStatus;

    public OrderLineUpdatedEvent(Object source, long orderId, long orderLineId, OrderLineStatus newStatus, OrderLineStatus oldStatus) {
    	super(source);
        this.orderId = orderId;
        this.orderLineId = orderLineId;
        this.newStatus = newStatus;
        this.oldStatus = oldStatus;
    }

    public static OrderLineUpdatedEvent of(Object source, long orderId, long orderLineId, OrderLineStatus newStatus, OrderLineStatus oldStatus) {
        return new OrderLineUpdatedEvent(source, orderId, orderLineId, newStatus, oldStatus);
    }

    public String getAggregateId() {
        return String.valueOf(orderId);
    }

    public String getAggregateType() {
        return "Order";
    }

    public JsonNode getPayload() {
        return mapper.createObjectNode()
                .put("orderId", orderId)
                .put("orderLineId", orderLineId)
                .put("oldStatus", oldStatus.name())
                .put("newStatus", newStatus.name());
    }

    public String getType() {
        return "OrderLineUpdated";
    }

}
