package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenHasExpiredException extends AuthenticationException {

	private static final long serialVersionUID = 5265825212524457331L;

	public TokenHasExpiredException(String msg) {
		super(msg);
	}

}
