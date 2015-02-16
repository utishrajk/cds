package com.feisystems.bham.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.feisystems.bham.exceptions.UserNotFoundException;
import com.feisystems.bham.exceptions.UserNotVerifiedException;
import com.feisystems.bham.util.CommonUtility;

/**
 * Returns a 401 error code (Unauthorized) to the client, when Ajax
 * authentication fails.
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private MessageSource messageSource;

	Locale locale = LocaleContextHolder.getLocale();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		response.setContentType("APPLICATION_JSON");
		String errorMessage = null;

		if (exception instanceof LockedException) {

			errorMessage = messageSource.getMessage("exception.account.locked", null, locale);

			setResponse(response, errorMessage);

		} else if (exception instanceof BadCredentialsException) {

			errorMessage = messageSource.getMessage("exception.invalid.login", null, locale);

			setResponse(response, errorMessage);

		} else if (exception instanceof InternalAuthenticationServiceException) {

			if (CommonUtility.getRoot(exception).getClass().equals(UserNotVerifiedException.class)) {
				errorMessage = messageSource.getMessage("exception.user.notverified", null, locale);
				setResponse(response, errorMessage);

			} else if (CommonUtility.getRoot(exception).getClass().equals(UserNotFoundException.class)) {
				errorMessage = messageSource.getMessage("exception.invalid.login", null, locale);
				setResponse(response, errorMessage);

			}

		} else {

			errorMessage = messageSource.getMessage("exception.authentication.failed", null, locale);

			setResponse(response, errorMessage);

		}

	}

	private void setResponse(HttpServletResponse response, String errorMessage) throws IOException {
		response.setStatus(401);
		response.getWriter().print(errorMessage);
	}

}
