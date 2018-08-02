package com.demo.manager;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.demo.common.dto.UserDto;
import com.demo.common.exception.UserException;

@Component
public interface UserManager {

	public List<UserDto> getUsers() throws UserException;

	public void addUser(UserDto userDto) throws UserException;

	public UserDto getUser(String userId) throws UserException;

	public void updateUser(@Valid UserDto userDto) throws UserException;

}
