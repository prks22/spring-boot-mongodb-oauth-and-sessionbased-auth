package com.demo.enums;

public enum ErrorCode {
  USER_NOTFOUND("User does not exit", 404),
  ACCESS_DENIED("not allowed",501), VALIDATION_FAIL("Field is missing",422 ),
  INVALID_USER_OR_PASSWORD("Invalid username or password", 401), UNIQUE_USERNAME("Username or email must be unique.",409), UNIQUE_EMAIL("This email is already register.",409);
  private final int errorCode;
  private final String errorMessage;

  ErrorCode(final String errorMessage, final int errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }



}
