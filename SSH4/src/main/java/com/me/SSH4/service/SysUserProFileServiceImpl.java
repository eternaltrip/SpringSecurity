package com.me.SSH4.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.SSH4.dao.SysUserProfileDao;
import com.me.SSH4.modal.SysUserProfile;



@Service
@Transactional
public class SysUserProFileServiceImpl implements SysUserProFileService {

	@Autowired
	private SysUserProfileDao userProfileDao;
	
	
	@Override
	public List<SysUserProfile> findAll() {
		return userProfileDao.findAll();
	}

	@Override
	public SysUserProfile findByType(String type) {
		return userProfileDao.findByType(type);
	}

	@Override
	public SysUserProfile findById(int id) {
		return userProfileDao.findById(id);
	}

}
