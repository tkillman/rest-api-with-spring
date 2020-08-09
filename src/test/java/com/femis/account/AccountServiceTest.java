package com.femis.account;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
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
		
		this.accountRepository.save(account);
		
		UserDetailsService userDetailsService = (UserDetailsService)accountService;
		UserDetails userdetails = userDetailsService.loadUserByUsername(email);
				
		assertThat(userdetails.getPassword(), Matchers.equalTo(password));
	}

	@Test(expected = UsernameNotFoundException.class)
	public void findByUsernameFail() {
		String username = "random@email.com";
		accountService.loadUserByUsername(username);
	}
}
