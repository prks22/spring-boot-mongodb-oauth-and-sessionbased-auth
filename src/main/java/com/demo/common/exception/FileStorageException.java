package com.demo.common.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5052188008047411449L;
	String message;
	  int erroreCode;
	  HttpStatus  status;

	  public FileStorageException(final String message, final Throwable throwable) {
	    super(message, throwable);
	    this.message = message;
	  }

	  public FileStorageException(final String message) {
	    super();
	    this.message = message;
	  }

	  public FileStorageException(final String message, final int errorCode) {
	    this.message = message;
	    this.erroreCode = errorCode;
	  }
	  public FileStorageException(final String message, final int errorCode,HttpStatus  status) {
		    this.message = message;
		    this.erroreCode = errorCode;
		    this.status=status;
		  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErroreCode() {
		return erroreCode;
	}

	public void setErroreCode(int erroreCode) {
		this.erroreCode = erroreCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	  

}