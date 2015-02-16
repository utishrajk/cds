package com.feisystems.bham.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.Authority;
import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.LoginAttempt;
import com.feisystems.bham.domain.LoginAttemptRepository;
import com.feisystems.bham.exceptions.UserNotFoundException;
import com.feisystems.bham.exceptions.UserNotVerifiedException;
import com.feisystems.bham.infrastructure.ApplicationConfig;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class IndividualProviderDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(IndividualProviderDetailsService.class);
	public static final String ROLE_USER = "ROLE_USER";

	@Inject
	private IndividualProviderRepository individualProviderRepository;

	@Autowired
	ApplicationConfig config;

	@Autowired
	LoginAttemptRepository loginAttemptRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		log.debug("Authenticating {}", login);
		String lowercaseLogin = login.toLowerCase();

		IndividualProvider userFromDatabase = individualProviderRepository.findByUserName(login);

		if (userFromDatabase == null) {
			throw new UserNotFoundException("User " + lowercaseLogin + " was not found in the database");
		} else if (!userFromDatabase.isVerified()) {
			throw new UserNotVerifiedException("User has not been verified. Please check your email your email to verify your email.");
		}

		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		for (Authority authority : userFromDatabase.getAuthorities()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
			grantedAuthorities.add(grantedAuthority);
		}

		String password = userFromDatabase.getCredential();
		boolean enabled = userFromDatabase.isVerified();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;

		LoginAttempt loginAttempt = loginAttemptRepository.findByUsername(login);

		boolean accountNonLocked = userFromDatabase.isAccountNonLocked();
		if (!accountNonLocked && loginAttempt != null) {
			DateTime lastModified = new DateTime(loginAttempt.getLastModified());
			if (hasExpired(calculateExpiryDate(lastModified))) {
				accountNonLocked = true;
				userFromDatabase.setAccountNonLocked(true);
				individualProviderRepository.save(userFromDatabase);

				loginAttempt.setAttempts(0);
				loginAttemptRepository.save(loginAttempt);
			}
		}

		return new User(lowercaseLogin, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);

	}

	private DateTime calculateExpiryDate(DateTime date) {
		return date.plusMinutes(config.getAccountLockoutTime());
	}

	public boolean hasExpired(DateTime date) {
		return date.isBeforeNow();
	}

}
