package com.femis.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

class EventTest {

	@Test
	public void builder() {
		Event event = Event.builder()
							.name("Inflearn Spring REST API")
							.description("REST API development with Spring").build();
		assertThat(event).isNotNull();
	}

	@Test
	public void javaBean() {
		Event event = new Event();
		String name = "Event";
		event.setName(name);
		String description = "Spring";
		event.setDescription(description);
		
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
	}
	
}
