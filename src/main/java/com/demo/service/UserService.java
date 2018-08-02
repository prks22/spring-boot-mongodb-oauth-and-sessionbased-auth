package com.demo.service;

import java.util.List;

import javax.validation.Valid;

import com.demo.common.dto.UserDto;
import com.demo.common.exception.UserException;

public interface UserService {
	public UserDto getUser(String userId) throws UserException;

	public void addUser(UserDto userDto) throws UserException;

	public void updateUser(@Valid UserDto userDto)throws UserException;
}
