package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {
	private static final long serialVersionUID = -9072978379438129866L;

	public CaptchaException(String msg) {
		super(msg);
	}
}
