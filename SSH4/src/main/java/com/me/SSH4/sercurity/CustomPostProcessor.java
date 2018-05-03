package com.me.SSH4.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;



/**
 * 一、实现{@link ObjectPostProcessor}泛型类，并注入{@link FilterSecurityInterceptor}类型。
 * <br> 然后当url请求发起的时候，{@link FilterSecurityInterceptor}类中的doFilter方法开始接手处理。
 *
 */
@Service
public class CustomPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
	
	@Autowired
	private CustomSecurityMetadataSource securityMetadataSource; 
	
	@Autowired
	private CustomAccessDecisionManager accessDecisionManager;

	@Override
	public <T extends FilterSecurityInterceptor> T postProcess(T fsi) {
		fsi.setSecurityMetadataSource(securityMetadataSource);//路径（资源）拦截处理
		fsi.setAccessDecisionManager(accessDecisionManager);//权限决策处理类
		return fsi;
	}

}
