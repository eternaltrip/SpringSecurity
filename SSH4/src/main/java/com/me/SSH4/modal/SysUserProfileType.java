package com.me.SSH4.modal;

import java.io.Serializable;

/**
 * 角色类型
 * @author Administrator
 *
 */
public enum SysUserProfileType implements Serializable {
	USER("USER"),
	DBA("DBA"),
	ADMIN("ADMIN");
	
	String userProfileType;
	
	private SysUserProfileType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
}
