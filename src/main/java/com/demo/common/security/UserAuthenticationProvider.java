package com.demo.common.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.demo.common.exception.AuthException;
import com.demo.entity.User;
import com.demo.enums.Roles;
import com.demo.repository.UserRepository;
import com.demo.utils.Utils;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserRepository userRepository;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		final String email = authentication.getName();
		final String password = authentication.getCredentials().toString();
		final Optional<User> user = userRepository.findByMobileOrEmail(email, email);
		if (!user.isPresent()) {
			throw new AuthException("Invalid username or password", "401");
		}
		if (!Utils.matchPassword(password, user.get().getPassword())) {
			throw new AuthException("Invalid username or password", "401");
		}
		final UserPrinciple userPrinciple = new UserPrinciple(user.get().getId(), user.get().getName(),
				Roles.ROLE_USER.name(), user.get().getPassword());
		final List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		final Role role = new Role(Roles.ROLE_USER.name());
		roles.add(role);
		return new UsernamePasswordAuthenticationToken(userPrinciple, password, roles);

	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}