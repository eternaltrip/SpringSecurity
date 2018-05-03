package com.me.SSH4.service;

import java.util.List;

import com.me.SSH4.modal.SysUser;

public interface SysUserService {
	SysUser findById(int id);

	SysUser findBySSO(String sso);
	

	void saveUser(SysUser SysUser);

	void updateUser(SysUser SysUser);

	void deleteUserBySSO(String sso);

	List<SysUser> findAllUsers();

	boolean isUserSSOUnique(Integer id, String sso);

}
