package com.femis.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

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
@EqualsAndHashCode(of = "id")
@Entity
public class Event {
	
	@Id
    @GeneratedValue(generator = "Entity_Sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "Entity_Sequence", sequenceName = "Entity_Seq")
	private int id;
	
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
	
	@Enumerated(EnumType.STRING)
	private EventsStatus eventsStatus = EventsStatus.DRAFT;

	public void update() {
		// Update free
		if(this.basePrice == 0 && this.maxPrice == 0) {
			this.free = true;
		} else {
			this.free = false;
		}
		
		if (this.location != null && location != "") {
			this.offLine = false;
		} else {
			this.offLine = true;
		}
	}
}
