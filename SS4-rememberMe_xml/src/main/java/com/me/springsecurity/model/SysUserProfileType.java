package com.me.springsecurity.model;


/**
 * 角色类型
 * @author Administrator
 *
 */
public enum SysUserProfileType {
	USER("USER"), DBA("DBA"), ADMIN("ADMIN");

	String userProfileType;

	
	private SysUserProfileType() {
	}

	private SysUserProfileType(String userProfileType) {
		this.userProfileType = userProfileType;
	}

	
	private void UserProfileType(String userProfileType){  
	        this.userProfileType = userProfileType;  
	    }

	public String getUserProfileType() {
		return userProfileType;
	}
	
}
