package com.femis.events;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id","name"})
@Entity
public class Event {
	@Id @GeneratedValue
	private int id;
	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;
	private LocalDateTime closeEnrollmentDateTime;
	private LocalDateTime beginEventDateTime;
	private LocalDateTime endEventDateTime;
	private String location; // (optional) 이게 없으면 온라인 모임 private int basePrice; // (optional) private int
								// maxPrice; // (optional) private int limitOfEnrollment;
	
	private boolean offline;
	private boolean free;
	private int basePrice;
	private int maxPrice;
	private int limitOfEnrollment;
	
	@Enumerated(EnumType.STRING)
	private EventsStatus evetnStatus;

}
