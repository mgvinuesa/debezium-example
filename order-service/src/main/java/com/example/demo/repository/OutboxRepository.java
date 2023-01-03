package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Outbox;

public interface OutboxRepository extends CrudRepository<Outbox, String>{

}
