package com.demo.common.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6277385446776233199L;
	String msg;

	public AuthException(String msg) {

		super(msg);
		this.msg = msg;
	}

	public AuthException(String string, String string2) {
		super(string);
	}

	public String getErrorCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
