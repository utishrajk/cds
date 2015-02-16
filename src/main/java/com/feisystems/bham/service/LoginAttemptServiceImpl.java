package com.feisystems.bham.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.bham.domain.IndividualProvider;
import com.feisystems.bham.domain.IndividualProviderRepository;
import com.feisystems.bham.domain.LoginAttempt;
import com.feisystems.bham.domain.LoginAttemptRepository;
import com.feisystems.bham.infrastructure.ApplicationConfig;

@Service
@Transactional
public class LoginAttemptServiceImpl implements LoginAttemptService {

	@Autowired
	IndividualProviderRepository individualProviderRepository;

	@Autowired
	LoginAttemptRepository loginAttemptRepository;

	@Autowired
	ApplicationConfig config;

	@Override
	public void updateFailAttempts(String username) {
		LoginAttempt loginAttempt = loginAttemptRepository.findByUsername(username);

		if (loginAttempt == null) {
			loginAttempt = new LoginAttempt();
			loginAttempt.setAttempts(1);

			loginAttempt.setUsername(username);
		} else {

			int currentAttempts = loginAttempt.getAttempts();
			currentAttempts++;
			loginAttempt.setAttempts(currentAttempts);

			if (loginAttempt.getAttempts() >= config.getMaxAttempts()) {

				IndividualProvider user = individualProviderRepository.findByUserName(username);
				user.setAccountNonLocked(false);
				individualProviderRepository.save(user);
			}

		}

		loginAttempt.setLastModified(new Date());
		loginAttemptRepository.save(loginAttempt);
	}

	@Override
	public void resetFailAttempts(String username) {
		LoginAttempt loginAttempt = loginAttemptRepository.findByUsername(username);
		if (loginAttempt != null) {
			loginAttempt.setAttempts(0);
			loginAttempt.setLastModified(new Date());
			loginAttemptRepository.save(loginAttempt);

			IndividualProvider user = individualProviderRepository.findByUserName(username);
			user.setAccountNonLocked(true);
		}
	}

	@Override
	public LoginAttempt getUserAttempt(String username) {
		LoginAttempt loginAttemp = loginAttemptRepository.findByUsername(username);
		return loginAttemp;
	}

}
