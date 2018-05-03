package com.me.SSH4.dao;

import java.util.List;

import com.me.SSH4.modal.SysUser;

public interface SysUserDao {

	SysUser findById(int id);

	SysUser findBySSO(String sso);

	void save(SysUser user);

	void deleteBySSO(String sso);

	List<SysUser> findAllUsers();

}
