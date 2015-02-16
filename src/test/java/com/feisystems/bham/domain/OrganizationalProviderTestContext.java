package com.feisystems.bham.domain;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.feisystems.bham.service.OrganizationalProviderService;
import com.feisystems.bham.web.OrganizationalProviderController;

@Configuration
@EnableWebMvc
public class OrganizationalProviderTestContext extends WebMvcConfigurerAdapter {

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
	public OrganizationalProviderController organizationalProviderController() {
		return new OrganizationalProviderController();
	}

	@Bean
	public OrganizationalProviderService organizationalProviderService() {
		return Mockito.mock(OrganizationalProviderService.class);
	}

}
