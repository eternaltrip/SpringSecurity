package com.me.springsecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.me.springsecurity.model.SysUser;
import com.me.springsecurity.model.SysUserProfile;


/**
 * 获取用户登录和权限验证
 * @author Administrator
 *
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserService sysUserService;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
		SysUser sysUser = sysUserService.findBySso(ssoId);  
		System.out.println("User : "+sysUser);
		if(sysUser == null){
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		User user = new User(sysUser.getSsoId(),sysUser.getPassword(),sysUser.getState().equals("Active"),true,true,true,getGrantedAuthorities(sysUser));
		return user;
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(SysUser sysUser){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (SysUserProfile sysUserProfile : sysUser.getUserProfiles()) {
			System.out.println("UserProfile : "+ sysUserProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + sysUserProfile.getType()));
		}
		System.out.println("authorities :" + authorities);
		return authorities;
	}
	

}
