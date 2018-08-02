package com.demo.enums;

public enum Roles {
	ROLE_ADMIN(1), ROLE_USER(2);
	private final long roleId;
	Roles(long id){
		this.roleId=id;
	}
	public long getRoleId() {
		return roleId;
	}
	

}
