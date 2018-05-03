package com.me.SSH4.dao;

import java.util.List;

import com.me.SSH4.modal.SysUserProfile;

public interface SysUserProfileDao {
	List<SysUserProfile> findAll();  
    
	SysUserProfile findByType(String type);  
       
	SysUserProfile findById(int id);  
}
