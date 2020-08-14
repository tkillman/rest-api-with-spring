package com.femis.config;

import java.util.Arrays;
import java.util.HashSet;

import org.hibernate.mapping.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.femis.account.Account;
import com.femis.account.AccountRole;
import com.femis.account.AccountService;

@Configuration
public class AppConfig {
	
	@Autowired
	AccountService accountService;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();	
	}
	
	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			
			@Override
			public void run(ApplicationArguments args) throws Exception {
				Account keesun = Account.builder()
						.email("keesun@email.com")
						.password("keesun")
						.roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
						.build();
				
				accountService.saveAccount(keesun);
			}
		};
	}
}
