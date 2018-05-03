package com.me.springsecurity.dao;

import com.me.springsecurity.model.SysUser;

public interface SysUserDao {
	SysUser findById(int id);

	SysUser findBySSO(String sso);
}
