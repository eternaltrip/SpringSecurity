package com.me.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.me.springsecurity.model.SysUserProfile;
import com.me.springsecurity.service.SysUserProFileService;

@Component
public class RoleToUserProfileConverter implements Converter<Object, SysUserProfile> {

	@Autowired
	SysUserProFileService userProfileService;

	/**
	 * Gets UserProfile by Id
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public SysUserProfile convert(Object object) {
		Integer id = Integer.parseInt((String) object);
		SysUserProfile profile = userProfileService.findById(id);
		System.out.println("Profile : " + profile);
		return profile;
	}

	/*
	 * Gets UserProfile by type
	 * 
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.
	 * Object)
	 */
	/*
	 * public UserProfile convert(Object element) { String type = (String)element;
	 * UserProfile profile= userProfileService.findByType(type);
	 * System.out.println("Profile ... : "+profile); return profile; }
	 */

}
