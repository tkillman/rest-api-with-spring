package com.femis.events;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
	
	public void validate(EventDto eventDto, Errors errors) {
		if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
			errors.rejectValue("basePrice", "wrong value","BasePrice is wrong.");
			errors.rejectValue("maxPrice", "wrong value","maxPrice is wrong."); //field error
			errors.reject("wrong value","Prices are wrong"); //global error
		}
		
		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		
		if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime())) {
			errors.rejectValue("endEventDateTime", "wrong value","endEventDateTime is wrong");
		}
		
	}
}
