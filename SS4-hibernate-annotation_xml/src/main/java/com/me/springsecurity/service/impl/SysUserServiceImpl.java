package com.me.springsecurity.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.springsecurity.dao.SysUserDao;
import com.me.springsecurity.model.SysUser;
import com.me.springsecurity.service.SysUserService;


@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {
		
	@Autowired
	private SysUserDao sysUserDao;

	
	@Override
	public SysUser findById(int id) {
		return sysUserDao.findById(id);
	}
	@Override
	public SysUser findBySso(String sso) {
		return sysUserDao.findBySSO(sso);
	}
	


}
