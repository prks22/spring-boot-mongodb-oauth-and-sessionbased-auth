package com.demo.utils;

import java.io.IOException;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	public static String encodePassword(final String password) {
		final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		final String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}

	public static boolean matchPassword(final String rawPassword, final String encodedPassword) {
		final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	public static <T, S> T mapper(final S source, final Class<T> target) {
		if (source == null) {
			return null;
		}
		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		final T targetObj = modelMapper.map(source, target);
		return targetObj;
	}

	public static <T, S> T mapper(final S source, final T obje) {
		if (source == null) {
			return null;
		}
		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(source, obje);
		return obje;
	}

	public static <T, S> T mapList(final S source, final java.lang.reflect.Type targetListType) {
		if (source == null) {
			return null;
		}
		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		final T targetObj = modelMapper.map(source, targetListType);
		return targetObj;
	}

	public static String convertObjectToJson(final Object object) {
		if (object == null) {
			return null;
		}
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
