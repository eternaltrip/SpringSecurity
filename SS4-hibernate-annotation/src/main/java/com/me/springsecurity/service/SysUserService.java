package com.me.springsecurity.service;

import com.me.springsecurity.model.SysUser;

public interface SysUserService {
	SysUser findById(int id);

	SysUser findBySso(String sso);
}
