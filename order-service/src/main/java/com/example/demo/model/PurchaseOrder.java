package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class PurchaseOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_ids")
	@SequenceGenerator(name = "purchase_order_ids", sequenceName = "seq_purchase_order", allocationSize = 50)
	private Long id;

	private long customerId;

	private LocalDateTime orderDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "purchaseOrder")
	private List<OrderLine> lineItems;

	PurchaseOrder() {
	}

	public PurchaseOrder(long customerId, LocalDateTime orderDate, List<OrderLine> lineItems) {
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.lineItems = new ArrayList<>(lineItems);
		lineItems.forEach(line -> line.setPurchaseOrder(this));
	}

	public OrderLineStatus updateOrderLine(long orderLineId, OrderLineStatus newStatus) {
		for (OrderLine orderLine : lineItems) {
			if (orderLine.getId() == orderLineId) {
				OrderLineStatus oldStatus = orderLine.getStatus();
				orderLine.setStatus(newStatus);
				return oldStatus;
			}
		}

		throw new EntityNotFoundException("Order does not contain line with id " + orderLineId);
	}
}
