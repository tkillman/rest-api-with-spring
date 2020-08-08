package com.femis.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class EventTest {
	
	@Test
	public void builder() {
		Event event = Event.builder()
				.name("Inflearn Spring REST API")
				.description("REST API development with Spring")
				.build();
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

	@Test
	@Parameters(method = "paramsForTestFree")
	public void testFree(int basePrice, int maxPrice, boolean isFree) {
		// given
		Event event = Event.builder().basePrice(basePrice).maxPrice(maxPrice).build();
		// When
		event.update();

		// Then
		assertThat(event.isFree()).isEqualTo(isFree);
	}
	
	private Object[] paramsForTestFree() {
		return new Object[] {
			new Object[] {0, 0, true}
			, new Object[] {100, 0, false}
			, new Object[] {0, 100, false}
			, new Object[] {100, 100, false}
		};
	}
	
	@Test 
	@Parameters(method = "paramtersForTestOffLine")
	public void testOffLine(String location, boolean isOffLine) {
		// given
		Event event = Event.builder().location(location).build();
		// When
		event.update();

		// Then
		assertThat(event.isOffLine()).isEqualTo(isOffLine);
	}
	
	public Object[] paramtersForTestOffLine() {
		return new Object[] {
				new Object[] {"강남역", false}
				, new Object[] {null, true}
		};
	}
}
