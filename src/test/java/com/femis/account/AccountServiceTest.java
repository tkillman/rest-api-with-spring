package com.femis.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

	@Autowired
	AccountService accountService;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	public void findByUsername() {
		Set roleSet = new HashSet<>();
		roleSet.add(AccountRole.ADMIN);
		roleSet.add(AccountRole.USER);
		
		String password = "keesun";
		String email = "keesun@email.com";
		//Given
		Account account = Account.builder()
								.email(email)
								.password(password)
								.roles(roleSet)
								.build();
		
		//this.accountService.saveAccount(account);
		
		UserDetailsService userDetailsService = (UserDetailsService)accountService;
		UserDetails userdetails = userDetailsService.loadUserByUsername(email);
				
		//assertThat(userdetails.getPassword(), Matchers.equalTo(password));
		
		assertThat(this.passwordEncoder.matches(password, userdetails.getPassword())).isTrue();
	}

	@Test(expected = UsernameNotFoundException.class)
	public void findByUsernameFail() {
		String username = "random@email.com";
		accountService.loadUserByUsername(username);
	}
}
