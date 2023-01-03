/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

import com.example.demo.model.PurchaseOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * A 'Customer' event that indicates an invoice has been created.
 */
public class InvoiceCreatedEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1088983210568714372L;

	private static ObjectMapper mapper = new ObjectMapper();

    private final long customerId;
    private final JsonNode order;

    private InvoiceCreatedEvent(Object source,long customerId, JsonNode order) {
    	super(source);
        this.customerId = customerId;
        this.order = order;
    }

    public static InvoiceCreatedEvent of(Object source, PurchaseOrder order) {
        ObjectNode asJson = mapper.createObjectNode()
                .put("orderId", order.getId())
                .put("invoiceDate", order.getOrderDate().toString())
                .put("invoiceValue", order.getTotalValue());

        return new InvoiceCreatedEvent(source, order.getCustomerId(), asJson);
    }

    public String getAggregateId() {
        return String.valueOf(customerId);
    }

    public String getAggregateType() {
        return "Customer";
    }

    public JsonNode getPayload() {
        return order;
    }

    public String getType() {
        return "InvoiceCreated";
    }

}
