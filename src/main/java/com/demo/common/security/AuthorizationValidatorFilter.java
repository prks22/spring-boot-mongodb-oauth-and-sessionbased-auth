package com.demo.common.security;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.common.dto.ErrorResponseDto;
import com.demo.constant.Constant;
import com.demo.utils.Utils;

public class AuthorizationValidatorFilter extends OncePerRequestFilter {
	{

	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		final String header = request.getHeader("Authorization");
		if (request.getRequestURI().contains(Constant.login)) {
			if ((header == null) || !header.startsWith("Basic ")) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.getWriter()
						.write(getErrorMsg(HttpStatus.BAD_REQUEST.value(), "Invalid basic authentication token"));
			} else {
				extractAndDecodeHeader(header, request, response);
			}
		}
		filterChain.doFilter(request, response);
	}

	private void extractAndDecodeHeader(final String header, final HttpServletRequest request,
			final HttpServletResponse res) throws IOException {

		final byte[] base64Token = header.substring(6).getBytes("UTF-8");
		try {
			Base64.getDecoder().decode(base64Token);
		} catch (final IllegalArgumentException e) {
			res.setStatus(HttpStatus.BAD_REQUEST.value());
			res.getWriter().write(getErrorMsg(HttpStatus.BAD_REQUEST.value(), "Invalid basic authentication token"));
		}

	}

	private String getErrorMsg(final int status, final String errorMsg) {
		final ErrorResponseDto responseDto = new ErrorResponseDto();
		responseDto.setMessage(errorMsg);
		responseDto.setErrorCode(status);
		return Utils.convertObjectToJson(responseDto);
	}
}