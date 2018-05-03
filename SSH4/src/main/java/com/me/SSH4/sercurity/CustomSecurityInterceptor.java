package com.me.SSH4.sercurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;





/**
 * 一、拦截请求。<br>继承{@link FilterSecurityInterceptor} 
 * <br>注入{@link CustomSecurityMetadataSource} 和{@link CustomAccessDecisionManager}
 */

@Service
public class CustomSecurityInterceptor extends FilterSecurityInterceptor {


	public CustomSecurityInterceptor(CustomAccessDecisionManager accessDecisionManager,
			CustomSecurityMetadataSource securityMetadataSource) {
		super.setAccessDecisionManager(accessDecisionManager);
		super.setSecurityMetadataSource(securityMetadataSource);
	}
	
	
}
