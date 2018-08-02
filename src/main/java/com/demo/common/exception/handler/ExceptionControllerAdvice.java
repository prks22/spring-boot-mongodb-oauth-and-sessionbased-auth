package com.demo.common.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.common.dto.ErrorResponseDto;
import com.demo.common.exception.UserException;
import com.demo.enums.ErrorCode;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> exceptionHandler(final Exception ex) {
    final ErrorResponseDto error = new ErrorResponseDto();
    error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    error.setMessage("Please contact your administrator");
    log.error(""+ex);
    return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UserException.class)
  public ResponseEntity<ErrorResponseDto> userExceptionHandler(final UserException ex) {
    final ErrorResponseDto error = new ErrorResponseDto();
    error.setErrorCode(ex.getErroreCode());
    error.setMessage(ex.getMessage());
    log.error(""+ex);
    return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
 
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponseDto> userExceptionHandler(final DataIntegrityViolationException ex) {
    final ErrorResponseDto error = new ErrorResponseDto();
    error.setErrorCode(HttpStatus.CONFLICT.value());
    error.setMessage(ex.getCause().getCause().getMessage().substring(ex.getCause().getCause().getMessage().indexOf(": Key")).replace("(root)", ""));
    log.error(""+ex);
    return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> userExceptionHandler(final MethodArgumentNotValidException ex) {
    final ErrorResponseDto error = new ErrorResponseDto();
    error.setErrorCode(ErrorCode.VALIDATION_FAIL.getErrorCode());
    String errorMsg=ex.getMessage().substring(ex.getMessage().lastIndexOf("[]; default message ["));
    		String field=errorMsg.substring( errorMsg.indexOf("message [")+9,errorMsg.indexOf("]]"));
    error.setMessage(field+": must not be empty.");
    log.error(""+ex);
    return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.EXPECTATION_FAILED);
  }
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponseDto> userExceptionHandler(final AccessDeniedException ex) {
     final ErrorResponseDto error = new ErrorResponseDto();
    error.setErrorCode(ErrorCode.ACCESS_DENIED.getErrorCode());
    error.setMessage(ErrorCode.ACCESS_DENIED.getErrorMessage());
    log.error(""+ex);
    return new ResponseEntity<ErrorResponseDto>(error, HttpStatus.UNAUTHORIZED);
  }
}
