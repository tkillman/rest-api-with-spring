package com.femis.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.femis.common.TestAnnotation;

import org.hamcrest.Matchers;


//@WebMvcTest

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	//@MockBean
	//EventRepository EventRepository;
	
	// api.json api.xml
	@Test
	@TestAnnotation("정상적으로 이벤트를 생성하는 테스트")
	public void createEvent() throws Exception{

		EventDto eventDto = EventDto.builder()
							.name("Spring")
							.description("REST API Development with Spring")
							.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
							.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
							.beginEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
							.endEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
							.basePrice(100)
							.maxPrice(100)
							.limitOfEnrollment(100)
							.location("강남역 D2 스타텁 팩토리")
							.build();
		//event.setId(10);
		
		//Mockito.when(EventRepository.save(event)).thenReturn(event);
		
		 //장소(location)가 있으면 오프라인여부(offLine) 은 false_
		mockMvc.perform(post("/api/events/")
							.contentType(MediaType.APPLICATION_JSON_UTF8)
							.accept(MediaTypes.HAL_JSON)
							.content(objectMapper.writeValueAsString(eventDto)))
							.andDo(print()).andExpect(status().isCreated())
							.andExpect(jsonPath("id").exists())
							.andExpect(header().exists(org.springframework.http.HttpHeaders.LOCATION))
							.andExpect(header().string("Content-Type",MediaTypes.HAL_JSON_UTF8_VALUE))
							.andExpect(jsonPath("id").value(Matchers.not(100)))
							.andExpect(jsonPath("free").value(false))
							.andExpect(jsonPath("offLine").value(false))
							.andExpect(jsonPath("eventsStatus").value(EventsStatus.DRAFT.name()))
							.andExpect(jsonPath("_links.self").exists())
							.andExpect(jsonPath("_links.query-events").exists())
							.andExpect(jsonPath("_links.update-event").exists());
	}
	
	@Test
	public void createEvent_Bad_Reqeust() throws Exception{

		Event event = Event.builder()
							.id(100)
							.name("Spring")
							.description("REST API Development with Spring")
							.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
							.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
							.beginEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
							.endEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
							.basePrice(100)
							.maxPrice(100)
							.limitOfEnrollment(100)
							.location("강남역 D2 스타텁 팩토리")
							.free(true)
							.offLine(false)
							.eventsStatus(EventsStatus.PUBLISHED)
							.build();
		//event.setId(10);
		
		//Mockito.when(EventRepository.save(event)).thenReturn(event);
		
		mockMvc.perform(post("/api/events/")
							.contentType(MediaType.APPLICATION_JSON_UTF8)
							.accept(MediaTypes.HAL_JSON)
							.content(objectMapper.writeValueAsString(event)))
							.andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	public void createEvent_Bad_Request_Empty_Input() throws JsonProcessingException, Exception {
		EventDto eventDto = EventDto.builder().build();
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(eventDto)))
		.andExpect(status().isBadRequest());
		
	}

	@Test
	@TestAnnotation("입력값이 잘못된 경우 에러가 발생하는 테스트")
	public void createEvent_Bad_Request_Wrong_Input() throws JsonProcessingException, Exception {
		System.out.println("createEvent_Bad_Request_Wrong_Input()");
		//이상한 값, 끝나는 날짜가 시작 날짜보다 더 빠르고, 기본금액이 최대 금액보다 크다.
		EventDto eventDto = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
				.basePrice(10000)
				.maxPrice(100)
				.limitOfEnrollment(100)
				.location("강남역 D2 스타텁 팩토리")
				.build();

		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(eventDto)))
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$[0].objectName").exists())
		//.andExpect(jsonPath("$[0].field").exists())
		.andExpect(jsonPath("$[0].defaultMessage").exists())
		.andExpect(jsonPath("$[0].code").exists())
		//.andExpect(jsonPath("$[0].rejectedValue").exists())
		;
		
	}

}
