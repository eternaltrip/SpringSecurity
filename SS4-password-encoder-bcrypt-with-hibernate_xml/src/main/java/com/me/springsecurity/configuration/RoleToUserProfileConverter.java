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
	 * 这个地方，主要是为了给SysUser这个对象注入SysUserProfile。
	 * 在注册页面中，需要给用户选择角色。而选择的角色是数据库角色的一个id号，并不是完整的角色。
	 * 所以这里通过这个自定义的类型转化（实际上是通过id号去数据库进行查询的结果并返回就好）来把角色的具体信息注入到SysUser。
	 * 这样做的目的其中之一便是，验证传入的id是否有效。
	 * 
	 * 
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
