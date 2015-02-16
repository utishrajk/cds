package com.feisystems.bham.service;

import com.feisystems.bham.domain.LoginAttempt;

public interface LoginAttemptService {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);

	LoginAttempt getUserAttempt(String username);

}
