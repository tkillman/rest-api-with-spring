package com.femis.events;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends ResourceSupport{
	
	@JsonUnwrapped
	private Event event;
	
	public EventResource(Event event) {
		this.event = event;
		add(linkTo(EventController.class).slash(this.event.getId()).withSelfRel());
	}
	
	public Event getEvent() {
		return event;
	}
}
