package com.me.SSH4.sercurity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;



/**
 * 三、权限决策器<br>
 * 该类实现{@link AccessDecisionManager}.
 * 主要是判断用户是否拥有请求url对应的权限
 * 通过{@link #decide(Authentication, Object, Collection)}方法判断用户是否相应权限。
 *
 */
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager{

	
	/**
	 *判断用户是否具有请求url的权限，如果有则返回，没有则抛出异常（但后面的程序还是会继续执行）。
	 *@param authentication 用户所拥有的权限
	 *@param object 包含客户端发起的请求的requset信息，可转换为 <code>HttpServletRequest request = ((FilterInvocation) object).getHttpRequest(); </code>
	 *@param configAttributes 当前请求url需要的权限。通过{@link FilterInvocationSecurityMetadataSourceService}类中的{@link #getAttributes(Object object)}方法获取
	 *
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null || configAttributes.size() == 0) {
			return ;
		}
		//Collection<GrantedAuthority> authorities =(Collection<GrantedAuthority>) authentication.getAuthorities();
		
		List<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		
		List<String> urlRoles = configAttributes.stream().map(ConfigAttribute :: getAttribute).collect(Collectors.toList());
		

		
		for (String urlRole : urlRoles) {
			if(userRoles.contains(urlRole)) {
				return;
			}
		}
		
		throw new AccessDeniedException("no right access");
	}

	
	/**
	 * 判断AccessDecisionManager是否支持传递的ConfigAttribute。
	 */
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	/**
	 * 判断是否支持"目标资源对象"的类型。
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		
		return true;
	}

}
