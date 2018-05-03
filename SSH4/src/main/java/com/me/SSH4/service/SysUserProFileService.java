package com.me.SSH4.service;

import java.util.List;

import com.me.SSH4.modal.SysUserProfile;


public interface SysUserProFileService {
	List<SysUserProfile> findAll();

	SysUserProfile findByType(String type);

	SysUserProfile findById(int id);
}
