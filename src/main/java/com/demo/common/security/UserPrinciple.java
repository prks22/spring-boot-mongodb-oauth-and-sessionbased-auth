package com.demo.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 4099657264547864946L;

	private String userId;

	private String userName;

	private String role;
	private String password;

	public UserPrinciple() {
	}

	public UserPrinciple(final String userId, final String userName, final String role, final String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;

		this.password = password;

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getScope() {
		return role;
	}

	public void setScope(final String scope) {
		this.role = scope;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		final Role role = new Role(this.role);
		roles.add(role);
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
