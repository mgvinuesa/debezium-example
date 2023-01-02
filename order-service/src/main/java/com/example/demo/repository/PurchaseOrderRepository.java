package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.PurchaseOrder;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {

}
