package com.feisystems.bham.config;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.feisystems.bham.security.AjaxAuthenticationFailureHandler;
import com.feisystems.bham.security.AjaxAuthenticationSuccessHandler;
import com.feisystems.bham.security.AjaxLogoutSuccessHandler;
import com.feisystems.bham.security.AuthoritiesConstants;
import com.feisystems.bham.security.Http401UnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("customAuthenticationProvider")
	private AuthenticationProvider authenticationProvider;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Configuration
    @Order(1)
    public static class SoapApiConfigurationAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http
            	.antMatcher("/soap/**")
                .csrf().disable();
//                .httpBasic();
        }
    }
	
	
	@Configuration
    public static class RestApiConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
//		private CsrfMatcher csrfRequestMatcher = new CsrfMatcher();

		@Inject
		private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

		@Inject
		private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

		@Inject
		private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

		@Inject
		private Http401UnauthorizedEntryPoint authenticationEntryPoint;

		@Autowired
		@Qualifier("customAuthenticationProvider")
		private AuthenticationProvider authenticationProvider;

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/src/main/webapp/**").antMatchers("/assets/**").antMatchers("/template*js");
		}

		protected void configure(HttpSecurity http) throws Exception {

			http
				.addFilterAfter(new CsrfTokenGeneratorFilter(), CsrfFilter.class)  // Comment to disable CSRF
				
			 	.exceptionHandling()
			 		.authenticationEntryPoint(authenticationEntryPoint)
			 		.accessDeniedPage("/app/403")
			 		.and()

				.httpBasic().and()
				
				.formLogin()
					.loginProcessingUrl("/app/authentication")
					.successHandler(ajaxAuthenticationSuccessHandler)
					.failureHandler(ajaxAuthenticationFailureHandler)
					.usernameParameter("j_username")
					.passwordParameter("j_password").permitAll()
					.and()
	            
//				.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and()
//				.csrf().disable()   // Uncomment to disable CSRF
				
				.logout().logoutUrl("/app/logout")
				
				.logoutSuccessHandler(ajaxLogoutSuccessHandler).deleteCookies("JSESSIONID").permitAll().and()
		
				.authorizeRequests()
		
				.antMatchers("/").permitAll()
				
				.antMatchers("/access").permitAll()
				
				.antMatchers("/feedback/**").permitAll()

				.antMatchers("/app/rest/authenticate").permitAll()
				
				.antMatchers("/app/public/**").permitAll()
				
				.antMatchers("/app/**").authenticated()
				
				.antMatchers("/dataelements*").authenticated().antMatchers("/dataelements/**").authenticated()
				
				.antMatchers("/recommendations*").authenticated().antMatchers("/recommendations/**").authenticated()
		
				.antMatchers("/individualproviders*").authenticated().antMatchers("/individualproviders/**").authenticated()
		
				.antMatchers("/organizationalproviders*").hasRole(AuthoritiesConstants.ADMIN)
				
				.antMatchers("/organizationalproviders/assessment").authenticated()
				
				.antMatchers("/organizationalproviders/**").hasRole(AuthoritiesConstants.ADMIN)
				
				.and()
				
				.sessionManagement()
		            .maximumSessions(1)
		            .maxSessionsPreventsLogin(false);
		}
		
		@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
		private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
		}
		
	    class CsrfMatcher implements RequestMatcher {
	        @Override
	        public boolean matches(HttpServletRequest request) {
	            if (request.getRequestURI().contains("soap"))
	                return true;
	            return true;
	        }
	    }

    }
	
	



}
