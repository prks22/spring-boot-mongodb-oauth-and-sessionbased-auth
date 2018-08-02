package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.dto.UserDto;
import com.demo.common.exception.UserException;
import com.demo.common.security.Role;
import com.demo.common.security.UserPrinciple;
import com.demo.enums.Roles;
import com.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j

public class UserController {
	@Inject
	private UserService userService;

	// @Secured can be used to authorized the user basis on its access level
	// permission
	// @Secured("ROLE_ADMIN")
	@GetMapping("/user")
	public UserDto getUsers() throws UserException {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userService.getUser(((UserPrinciple) authentication.getPrincipal()).getUserId());

	}

	@PostMapping("/signup")
	public void signup(@Valid @RequestBody final UserDto userDto) throws UserException {
		userService.addUser(userDto);

	}

	@PostMapping("/login")
	public void login() throws UserException {

	}

	@PostMapping("/logout")
	public void logout(final HttpSession session) throws UserException {
		session.invalidate();
	}

	@GetMapping("/user/facebook")
	public String getRedirectUrl(@RequestParam("code") final String code, @RequestParam("state") final String state,
			final HttpServletRequest request, final HttpServletResponse response) throws UserException {
		oauth(code, state);
		// hard coded to user info ater getting token from facebook and make it
		// authenticated in system so that existing flow keep intact.
		// you can access the user info from facebook using token and keep it
		// into session using below piece of code
		final UserPrinciple userPrinciple = new UserPrinciple("1", "test", Roles.ROLE_USER.name(), "password");
		final List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		final Role role = new Role(Roles.ROLE_USER.name());
		roles.add(role);
		final UsernamePasswordAuthenticationToken username = new UsernamePasswordAuthenticationToken(userPrinciple,
				"password", roles);
		SecurityContextHolder.getContext().setAuthentication(username);
		final HttpSession newSession = request.getSession();
		response.setHeader("x-auth-token", newSession.getId());
		return "success";

	}

	// below info is hard coded you can set this properties using configuration
	public void oauth(final String code, final String state) {

		final AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
		resource.setUserAuthorizationUri("https://www.facebook.com/dialog/oauth");
		resource.setAccessTokenUri("https://graph.facebook.com/oauth/access_token");
		resource.setClientId("233668646673605");
		resource.setClientSecret("33b17e044ee6a4fa383f46ec6e28ea1d");
		final AccessTokenRequest request = new DefaultAccessTokenRequest();
		request.setAuthorizationCode(code);
		request.setStateKey(state);
		request.setPreservedState("http://localhost:8080/users/user/facebook");
		final AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();
		final OAuth2AccessToken accessToken = provider.obtainAccessToken(resource, request);
		System.out.println(accessToken.getValue());
	}

	@PutMapping("/user")
	public void updateUser(@Valid @RequestBody final UserDto userDto) throws UserException {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userDto.setId(((UserPrinciple) authentication.getPrincipal()).getUserId());
		userService.updateUser(userDto);

	}

}
