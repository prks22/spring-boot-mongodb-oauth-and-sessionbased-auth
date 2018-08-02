package com.demo.common.exception;

public class UserException extends Exception {

 
  private static final long serialVersionUID = 7356258433235263154L;
  String message;
  int erroreCode;

  public UserException(final String message, final Throwable throwable) {
    super(message, throwable);
    this.message = message;
  }

  public UserException(final String message) {
    super();
    this.message = message;
  }

  public UserException(final String message, final int errorCode) {
    this.message = message;
    this.erroreCode = errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public int getErroreCode() {
    return erroreCode;
  }

  public void setErroreCode(final int erroreCode) {
    this.erroreCode = erroreCode;
  }


}
