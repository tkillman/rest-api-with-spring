package com.femis.index;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.femis.common.TestAnnotation;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IndexControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	@TestAnnotation("/api를 요청하면 응답을 해주는 테스트")
	public void test() throws Exception {
		
		mockMvc.perform(get("/api").content(""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("_links.events").exists());
	}
}
