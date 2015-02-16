package com.feisystems.bham.domain;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.feisystems.bham.service.DataElementsService;
import com.feisystems.bham.service.UsernameService;
import com.feisystems.bham.service.recommendation.RecommendationService;
import com.feisystems.bham.web.DataElementsController;

@Configuration
@EnableWebMvc
public class DataElementsTestContext extends WebMvcConfigurerAdapter {
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public DataElementsController DataElementsController() {
		return new DataElementsController();
	}
	
	@Bean
	public DataElementsService dataElementsService() {
		return Mockito.mock(DataElementsService.class);
	}
	
	@Bean
	public RecommendationService recommendationService() {
		return Mockito.mock(RecommendationService.class);
	}
	
	@Bean
	public UsernameService usernameService() {
		return Mockito.mock(UsernameService.class);
	}
	
	@Bean
	public IndividualProviderRepository individualProviderRepository() {
		return Mockito.mock(IndividualProviderRepository.class);
	}

}
