package com.me.SSH_OAUTH2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;



/**
 * 启用全局方法安全性，如果我们要使用它们，将激活@PreFilter，@PostFilter，@PreAuthorize @PostAuthorize注释。
 * @author yjin
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true , proxyTargetClass = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @SuppressWarnings("unused")
	@Autowired
	private OAuth2SecurityConfiguration securityConfiguration;

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}
	
	
	
}
