package com.feisystems.bham.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AlreadyVerifiedException extends AuthenticationException {

	private static final long serialVersionUID = 6278051680479605699L;

	public AlreadyVerifiedException(String msg) {
        super(msg);
    }
}
