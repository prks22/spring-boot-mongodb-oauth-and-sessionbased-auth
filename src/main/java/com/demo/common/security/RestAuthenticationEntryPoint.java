package com.demo.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.demo.common.dto.ErrorResponseDto;
import com.demo.common.exception.AuthException;
import com.demo.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authenticationException) throws IOException, ServletException {
		response.setContentType("application/json");
		final ErrorResponseDto responseDto = new ErrorResponseDto();
		responseDto.setMessage(authenticationException.getMessage());
		responseDto.setErrorCode(HttpStatus.UNAUTHORIZED.value());
		if (authenticationException instanceof AuthException) {
			final String errorCode = ((AuthException) authenticationException).getErrorCode();
			if (errorCode != null) {
				responseDto.setErrorCode(Integer.parseInt(errorCode));
			}
		}
		response.getWriter().write(Utils.convertObjectToJson(responseDto));
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}

	
}