package com.me.springsecurity.service;

import java.util.List;

import com.me.springsecurity.model.SysUserProfile;

public interface SysUserProFileService {
	List<SysUserProfile> findAll();

	SysUserProfile findByType(String type);

	SysUserProfile findById(int id);
}
