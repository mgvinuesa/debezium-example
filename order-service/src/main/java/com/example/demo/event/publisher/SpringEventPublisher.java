package com.example.demo.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisher implements EventPublisher {

	private ApplicationEventPublisher publisher;
	
	public SpringEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	
	@Override
	public void fire(Object event) {
		publisher.publishEvent(event);
	}

}
