package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotVerifiedException extends AuthenticationException {

	private static final long serialVersionUID = 18831070253247069L;
	
	public UserNotVerifiedException(String msg) {
		super(msg);
	}

}
