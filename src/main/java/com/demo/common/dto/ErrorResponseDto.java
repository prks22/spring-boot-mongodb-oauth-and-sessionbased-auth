package com.demo.common.dto;

import java.io.Serializable;

public class ErrorResponseDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2088794230934779508L;
	private Integer error;
	private String message;
	public void setMessage(String message) {
	this.message=message;

	}

	public void setErrorCode(int value) {
		this.error=value;

	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}
	

}
