package com.demo.manager.impl;

import static com.demo.enums.ErrorCode.USER_NOTFOUND;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.common.dto.UserDto;
import com.demo.common.exception.UserException;
import com.demo.entity.User;
import com.demo.enums.ErrorCode;
import com.demo.manager.UserManager;
import com.demo.repository.UserRepository;
import com.demo.utils.Utils;

@Component
@Transactional
public class UserManagerImpl implements UserManager {
	@Autowired
	UserRepository userReposityory;

	@Override
	public List<UserDto> getUsers() throws UserException {
		/*
		 * final List<User> datas = userReposityory.findByFirstname("prakash");
		 * java.lang.reflect.Type targetListType = new
		 * TypeToken<List<UserDto>>() { }.getType(); List<UserDto> userDto =
		 * Utils.mapList(datas, targetListType); if (userDto.isEmpty()) { throw
		 * new UserException(USER_NOTFOUND.getErrorMessage(),
		 * USER_NOTFOUND.getErrorCode()); }
		 */
		return null;
	}

	@Override
	public void addUser(final UserDto userDto) throws UserException {
		final User user = Utils.mapper(userDto, User.class);
		user.setPassword(Utils.encodePassword(userDto.getPassword()));
		try {
			userReposityory.save(user);
		} catch (final Exception e) {
			throw new UserException(ErrorCode.UNIQUE_EMAIL.getErrorMessage(), ErrorCode.UNIQUE_EMAIL.getErrorCode());
		}
	}

	@Override
	public UserDto getUser(final String userId) throws UserException {
		final Optional<User> user = userReposityory.findById(userId);
		if (!user.isPresent()) {
			throw new UserException(USER_NOTFOUND.getErrorMessage(), USER_NOTFOUND.getErrorCode());
		}
		final UserDto userDto = Utils.mapper(user.get(), UserDto.class);
		userDto.setPassword(null);
		return userDto;
	}

	@Override
	public void updateUser(@Valid final UserDto userDto) throws UserException {
		final Optional<User> user = userReposityory.findById(userDto.getId());
		final User updatedAudioProfile = Utils.mapper(userDto, user.get());
		if (userDto.getPassword() != null) {
			updatedAudioProfile.setPassword(Utils.encodePassword(userDto.getPassword()));
		} else {
			updatedAudioProfile.setPassword(user.get().getPassword());
		}
		userReposityory.save(updatedAudioProfile);

	}
}
