package com.femis.events;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {
	
	private final EventRepository eventRepository;
	
	private final ModelMapper modelMapper;
	
	private final EventValidator eventValidator;
	
	public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
		this.eventValidator = eventValidator;
	}

	@PostMapping
	public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
		//필수 유효성 검사
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
			//return badRequest(errors);
		}
		
		eventValidator.validate(eventDto, errors);
		//입력값 유효성 검사
		if (errors.hasErrors()) {
			//return ResponseEntity.badRequest().body(errors);
			return badRequest(errors);
		}
		
		Event event = modelMapper.map(eventDto, Event.class);
		event.update();
		Event newEvent = this.eventRepository.save(event);
		ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
		URI createdUri = selfLinkBuilder.toUri();		
		
		EventResource eventResource = new EventResource(event);
		eventResource.add(linkTo(EventController.class).withRel("query-events"));
		//eventResource.add(selfLinkBuilder.withSelfRel());
		eventResource.add(selfLinkBuilder.withRel("update-event"));
		
		return ResponseEntity.created(createdUri).body(eventResource);
	}
	
	private ResponseEntity badRequest(Errors errors) {
		System.out.println("ResponseEntity.badRequest().body(new ErrorsResource(errors))");
		System.out.println(ResponseEntity.badRequest().body(new ErrorsResource(errors)));
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}
}
