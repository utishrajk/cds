package com.feisystems.bham.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.feisystems.bham.util.Constant;

@Configuration
@PropertySource("classpath:/META-INF/spring/database.properties")
public class ApplicationConfig {

	@Autowired
	protected Environment environment;

	public String getHostNameUrl() {
		return environment.getProperty(Constant.ENVIRONMENT);
	}

	public String getLogin() {
		return environment.getProperty(Constant.LOGIN);
	}

	public String getRegister() {
		return environment.getProperty(Constant.REGISTER);
	}

	public int getTokenExpiryTime() {
		return Integer.parseInt(environment.getProperty(Constant.TOKEN_EXPIRY_TIME));
	}

	public int getAccountLockoutTime() {
		return Integer.parseInt(environment.getProperty(Constant.ACCOUNT_LOCKOUT_TIME));
	}

	public int getMaxAttempts() {
		return Integer.parseInt(environment.getProperty(Constant.MAX_ATTEMPTS));
	}


}
