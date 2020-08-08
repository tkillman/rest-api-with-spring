package com.femis.index;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {

	@GetMapping("/api")
	public ResponseEntity index() {
		
		ControllerLinkBuilder selfLinkBuilder = linkTo(methodOn(IndexController.class).index()).slash("/events");
		URI createdUri = selfLinkBuilder.toUri();
		
		ResourceSupport resourceSupport = new ResourceSupport();
		
		resourceSupport.add(selfLinkBuilder.withRel("events"));
		return ResponseEntity.created(createdUri).body(resourceSupport);
	}
}
