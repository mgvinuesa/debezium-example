package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.OrderLineStatus;
import com.example.demo.model.PurchaseOrder;
import com.example.demo.repository.PurchaseOrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	private PurchaseOrderRepository repository;

	public OrderService(PurchaseOrderRepository repository) {
		this.repository = repository;
	}

	public PurchaseOrder addOrder(PurchaseOrder order) {
		return repository.save(order);
	}

	public PurchaseOrder updateOrderLine(long orderId, long orderLineId, OrderLineStatus newStatus) {
		Optional<PurchaseOrder> purcharseOrder = repository.findById(orderId);
		if (purcharseOrder.isPresent()) {
			PurchaseOrder purchaseOrder = purcharseOrder.get();
			purchaseOrder.updateOrderLine(orderLineId, newStatus);
			repository.save(purchaseOrder);
			return purchaseOrder;
		} else {
			throw new EntityNotFoundException("Order with id " + orderId + " could not be found");
		}
	}

	public PurchaseOrder getOrderLine(long orderId) {
		return repository.findById(orderId).orElseThrow();
	}

}
