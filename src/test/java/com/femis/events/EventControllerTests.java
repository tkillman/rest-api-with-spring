package com.femis.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.femis.common.BaseControllerTest;
import com.femis.common.TestAnnotation;


//@WebMvcTest

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EventControllerTests extends BaseControllerTest{
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
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
		this.mockMvc.perform(post("/api/events/")
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
							.andDo(print())
							.andExpect(status().isBadRequest())
							//.andExpect(jsonPath("_links.index").exists())
							;
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
		.andExpect(jsonPath("errors[0].objectName").exists())
		//.andExpect(jsonPath("$[0].field").exists())
		.andExpect(jsonPath("errors[0].defaultMessage").exists())
		.andExpect(jsonPath("errors[0].code").exists())
		//.andExpect(jsonPath("$[0].rejectedValue").exists())
		.andExpect(jsonPath("_links.index").exists())
		;
		
	}
	
	public Event generateEvent(int i) throws Exception{

		Event event = Event.builder()
							.name("event generate i " + i)
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
		return this.eventRepository.save(event);
	}

	@Test
	@TestAnnotation("목록 조회를 테스트한다.")
	public void selectEventsListTest() throws Exception {
		
		//준비
		IntStream.range(0, 30).forEach(i -> {
			try {
				this.generateEvent(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		//실행
		mockMvc.perform(get("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.param("page", "1")
				.param("size", "10")
				.param("sort", "name,DESC"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("page").exists())
				.andExpect(jsonPath("_embedded.eventResourceList[0]._links.self").exists())
				.andExpect(jsonPath("_links.self").exists())
				//.andExpect(jsonPath("_links.profile").exists())
				//.andDo(document("query-events"))
				;
	}
	
	@Test
	@TestAnnotation("단일 조회를 테스트한다. 없는 건을 조회한다면 실패")
	public void selectEventTest_Bad_Request() throws Exception {
		
		//실행
		mockMvc.perform(get("/api/events/2885844"))
				.andExpect(status().isBadRequest())
				;
		//단언
	}
	
	@Test
	@TestAnnotation("단일 조회를 테스트한다.성공")
	public void selectEventTest_Success() throws Exception {
		//준비
		Event event = this.generateEvent(100);
		
		//실행
		mockMvc.perform(get("/api/events/" + event.getId()))
				.andExpect(status().isOk())
				;
		//단언
	}
	
	@Test
	@TestAnnotation("이벤트 수정을 테스트한다. 필수값 누락체크")
	public void updateEventTest_Bad_Request() throws Exception {
		//준비
		Event event = this.generateEvent(1);
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		eventDto.setName(null);
		
		//실행
		mockMvc.perform(put("/api/events/" + event.getId())
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsBytes(eventDto)))
				.andExpect(status().isBadRequest())
				;
		//단언
	}
	
	@Test
	@TestAnnotation("이벤트 수정을 테스트한다. 비즈니스로직 체크")
	public void updateEventTest_Bad_Request2() throws Exception {
		//준비
		Event event = this.generateEvent(1);
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		eventDto.setBasePrice(1000);
		eventDto.setMaxPrice(100);
		
		//실행
		mockMvc.perform(put("/api/events/" + event.getId())
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsBytes(eventDto)))
				.andExpect(status().isBadRequest())
				;
		//단언
	}
	
	@Test
	@TestAnnotation("이벤트 수정을 테스트한다. 성공")
	public void updateEventTest() throws Exception {
		//준비
		Event event = this.generateEvent(1);
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		
		String updateName = "currentTime" + LocalDateTime.now();
		eventDto.setName(updateName);
		
		//실행
		mockMvc.perform(put("/api/events/" + event.getId())
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(objectMapper.writeValueAsBytes(eventDto)))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("name",Matchers.is(updateName)))
				;
		//단언
	}

}
