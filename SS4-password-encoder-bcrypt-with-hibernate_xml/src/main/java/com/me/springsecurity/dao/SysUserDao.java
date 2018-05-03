package com.me.springsecurity.dao;

import com.me.springsecurity.model.SysUser;

public interface SysUserDao {
	void save(SysUser user);

	SysUser findById(int id);

	SysUser findBySSO(String sso);
}
