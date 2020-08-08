package com.femis.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EventDto {
	
	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;
	private LocalDateTime closeEnrollmentDateTime;
	private LocalDateTime beginEventDateTime;
	private LocalDateTime endEventDateTime;
	private String location; // (optional) 이게 없으면 온라인 모임 private int basePrice; // (optional) private int
								// maxPrice; // (optional) private int limitOfEnrollment;
	
	private boolean offLine;
	private boolean free;
	private int basePrice;
	private int maxPrice;
	private int limitOfEnrollment;
}
