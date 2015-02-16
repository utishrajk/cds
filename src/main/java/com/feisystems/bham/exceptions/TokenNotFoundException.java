package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 6780440987824929529L;

	public TokenNotFoundException(String msg) {
		super(msg);
	}

}
