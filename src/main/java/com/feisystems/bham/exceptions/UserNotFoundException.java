package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;


public class UserNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = -420211385783017099L;

	public UserNotFoundException(String msg) {
		super(msg);
	}
}
