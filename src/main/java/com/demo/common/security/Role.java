package com.demo.common.security;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5923445649487768569L;
	private String role;

	public Role(String role) {
		super();
		this.role = role;
	}

	@Override
	public String getAuthority() {

		return role;
	}

	public void getSetAuthority(String role) {

		this.role = role;
	}

}
