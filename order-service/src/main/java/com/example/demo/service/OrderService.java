package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.event.InvoiceCreatedEvent;
import com.example.demo.event.OrderCreatedEvent;
import com.example.demo.event.OrderLineUpdatedEvent;
import com.example.demo.event.publisher.EventPublisher;
import com.example.demo.model.OrderLineStatus;
import com.example.demo.model.PurchaseOrder;
import com.example.demo.repository.PurchaseOrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	private PurchaseOrderRepository repository;
	
	private EventPublisher publisher;
    
	public OrderService(PurchaseOrderRepository repository, EventPublisher publisher) {
		this.repository = repository;
		this.publisher = publisher;
	}

	public PurchaseOrder addOrder(PurchaseOrder order) {
		PurchaseOrder newOrder = repository.save(order);
		
		publisher.fire(OrderCreatedEvent.of(this,order));
		publisher.fire(InvoiceCreatedEvent.of(this,order));
		
		return newOrder;
	}

	public PurchaseOrder updateOrderLine(long orderId, long orderLineId, OrderLineStatus newStatus) {
		Optional<PurchaseOrder> purcharseOrder = repository.findById(orderId);
		if (purcharseOrder.isPresent()) {
			PurchaseOrder purchaseOrder = purcharseOrder.get();
			OrderLineStatus oldStatus = purchaseOrder.updateOrderLine(orderLineId, newStatus);
			repository.save(purchaseOrder);
			publisher.fire(OrderLineUpdatedEvent.of(this,orderId, orderLineId, newStatus, oldStatus));
			
			return purchaseOrder;
		} else {
			throw new EntityNotFoundException("Order with id " + orderId + " could not be found");
		}
	}

	public PurchaseOrder getOrderLine(long orderId) {
		return repository.findById(orderId).orElseThrow();
	}

}
