package com.feisystems.bham.domain;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.feisystems.bham.infrastructure.ApplicationConfig;
import com.feisystems.bham.service.IndividualProviderService;
import com.feisystems.bham.service.VerificationTokenService;
import com.feisystems.bham.web.AccountController;

@Configuration
@EnableWebMvc
public class IndividualProviderTestContext extends WebMvcConfigurerAdapter {
	
	private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
		messageSource.setUseCodeAsDefaultMessage(true);

		return messageSource;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public AccountController accountController() {
		return new AccountController();
	}
	
	@Bean
	public IndividualProviderService patientService() {
		return Mockito.mock(IndividualProviderService.class);
	}
	
	@Bean
	public VerificationTokenService verificationTokenService() {
		return Mockito.mock(VerificationTokenService.class);
	}
	
	@Bean
	public ApplicationConfig applicationConfig() {
		return Mockito.mock(ApplicationConfig.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return Mockito.mock(PasswordEncoder.class);
	}

}
