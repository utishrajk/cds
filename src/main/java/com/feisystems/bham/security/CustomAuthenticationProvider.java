package com.feisystems.bham.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import com.feisystems.bham.service.LoginAttemptService;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	public CustomAuthenticationProvider() {
		super.setPasswordEncoder(passwordEncoder());
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {
			Authentication auth = super.authenticate(authentication);

			if (auth.isAuthenticated()) {
				loginAttemptService.resetFailAttempts(authentication.getName());

			}

			return auth;

		} catch (BadCredentialsException e) {

			loginAttemptService.updateFailAttempts(authentication.getName());
			throw e;

		} catch (LockedException e) {

			throw e;

		}
	}
}
