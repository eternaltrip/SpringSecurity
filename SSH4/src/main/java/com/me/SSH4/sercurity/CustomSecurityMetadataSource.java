package com.me.SSH4.sercurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.me.SSH4.dao.SysPermissionDao;
import com.me.SSH4.modal.SysPermission;
import com.me.SSH4.modal.SysUserProfile;


/**
 * 二、当第一步拦截到请求之后，对拦截的url匹配需要的权限，然后进入{@link CustomAccessDecisionManager} 中再判断用户是否有权限访问url.<br>
 * 该类继承{@link SecurityMetadataSource}
 * <ol>
 * <li>
 * {@link #getAttributes(Object object)}方法获取请求url需要的权限并返回.
 * </li>
 * </ol>
 */
@Service
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private SysPermissionDao sysPermissionDao;
	Map<String , Collection<ConfigAttribute>> config = new HashMap<>();
	
	/**
	 * 该方法目的是给请求url找到对应的权限集合，并返回。没有则返回为null
	 * <ol>
	 * <li>首先从数据库加载出所有权限对应的url，然后再根据用户请求的url返回需要的权限
	 * </li>
	 * </ol>
	 * @param object 封装了request,response,chain的对象。
	 * @return Collection<code><</code>ConfigAttribute<code>></code> 符合ojbect对象中request对应的url的权限集合
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		return getMatcherConfigAttribute(request);
	}

	
	/**
	 * 获取当前url需要的角色(权限)
	 * @param object
	 */
	private Collection<ConfigAttribute> getMatcherConfigAttribute(HttpServletRequest request ) {
		AntPathRequestMatcher antPathRequestMatcher;
		
		if(config == null || config.size() == 0) {
			loadSysAttributes();
		}
		
		Iterator<String> iter = config.keySet().iterator() ;
		while (iter.hasNext()) {
			String url_str = iter.next().toString();
			antPathRequestMatcher = new AntPathRequestMatcher(url_str);
			if(antPathRequestMatcher.matches(request)) {
				return config.get(url_str);
			}
		}
		return null;
	}

	
	/**
	 * 加载系统配置的url以及需要的权限
	 */
	private void loadSysAttributes() {
		List<SysPermission> permissions = sysPermissionDao.findAllSysPermission();
		Collection<ConfigAttribute> atts;
		for (SysPermission sysPermission : permissions) {
			atts = config.get(sysPermission.getUrl());
			if(atts == null) {
				atts = new ArrayList<ConfigAttribute>();
			}
			for (SysUserProfile userProfile : sysPermission.getUserProfiles()) {
				atts.add(new SecurityConfig("ROLE_" + userProfile.getType()));
			}
			config.put(sysPermission.getUrl(), atts);
		}
	}

	
	
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
