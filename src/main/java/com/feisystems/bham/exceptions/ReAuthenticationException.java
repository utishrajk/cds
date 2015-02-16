package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ReAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = -420211382345017088L;

	public ReAuthenticationException(String msg) {
		super(msg);
	}
}
