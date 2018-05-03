package com.me.springsecurity.dao;

import java.util.List;

import com.me.springsecurity.model.SysUserProfile;



public interface SysUserProfileDao {
	List<SysUserProfile> findAll();  
    
	SysUserProfile findByType(String type);  
       
	SysUserProfile findById(int id);  
}
