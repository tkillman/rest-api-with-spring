package com.femis.events;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.validation.Errors;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ErrorsResource extends ResourceSupport{
	
	@JsonUnwrapped
	private Errors errors;

	public ErrorsResource(Errors errors) {
		super();
		this.errors = errors;
		add(linkTo(EventController.class).withRel("index"));
	}
	
	public Errors getErrors() {
		return this.errors;
	}
}
