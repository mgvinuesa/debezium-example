package com.example.demo.event.listener;

import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.example.demo.event.InvoiceCreatedEvent;
import com.example.demo.model.Outbox;
import com.example.demo.repository.OutboxRepository;

@Service
public class InvoiceCreatedEventListener implements ApplicationListener<InvoiceCreatedEvent> {

	private OutboxRepository repository;
	
	public InvoiceCreatedEventListener(OutboxRepository repository) {
		this.repository = repository;
	}

	@Override
	public void onApplicationEvent(InvoiceCreatedEvent event) {
		Outbox outbox = Outbox.builder().aggregateid(event.getAggregateId()).aggregatetype(event.getAggregateType())
				.payload(event.getPayload().toString()).type(event.getType()).id(UUID.randomUUID().toString())
				.timestamp(event.getTimestamp()).build();
		
		this.repository.save(outbox);

	}

}
