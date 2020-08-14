package com.femis.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.femis.account.Account;
import com.femis.account.AccountRole;
import com.femis.account.AccountService;
import com.femis.common.BaseControllerTest;
import com.femis.common.TestAnnotation;

public class AuthServerConfigTest extends BaseControllerTest{

	@Autowired
	AccountService accountService;
	
	@Test
	@TestAnnotation("인증 토큰을 발급받는 테스트")
	public void getAuthToken() throws Exception{
		//Given
		String username = "keesun@email.com";
		String password = "keesun";
		
		Account keesun = Account.builder()
				.email(username)
				.password(password)
				.roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
				.build();
		
				
		String clientId = "myApp";
		String clientSecret = "pass";

		this.mockMvc.perform(post("/oauth/token")
								.with(httpBasic(clientId, clientSecret))
								.param("username", username)
								.param("password", password)
								.param("grant_type", "password")
						)
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("access_token").exists());
	}

}
