package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;

public class PasswordAlreadyUsedException extends AuthenticationException {

	private static final long serialVersionUID = 2161050571235376062L;

	public PasswordAlreadyUsedException(String msg) {
		super(msg);
	}
}
