package com.demo.service.impl;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.demo.common.dto.UserDto;
import com.demo.common.exception.UserException;
import com.demo.manager.UserManager;
import com.demo.service.UserService;

@Component
public class UserServiceImpl implements UserService {
	@Inject
	UserManager UserManager;

	@Override
	public UserDto getUser(final String userId) throws UserException {
		return UserManager.getUser(userId);
	}

	@Override
	public void addUser(final UserDto userDto) throws UserException {
		UserManager.addUser(userDto);

	}

	@Override
	public void updateUser(@Valid final UserDto userDto) throws UserException {
		UserManager.updateUser(userDto);

	}
}
