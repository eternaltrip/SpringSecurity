package com.me.SSH_OAUTH2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;



/**
 * 2.授权服务器
 * 负责验证凭证,如果正常,则提供令牌(refresh-token以及access-token)
 * 还包括有关注册客户端和可能访问范围和授予类型的信息.令牌存储用于存储令牌
 * @author yjin
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguraion extends AuthorizationServerConfigurerAdapter {

	private static String REALM = "MY_OAUTH_REALM";
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private UserApprovalHandler userApprovalHandler;
	
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		/**
		 * 注册一个客户端,id为"my-trusted-client",密码为secret.角色和范围为允许
		 */
		clients.inMemory()
				.withClient("my-trusted-client")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")//授权的四种类型(密码类型,授权码类型,刷新码类型,简化模式)
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
				.scopes("read", "write", "trust")
				.secret("secret")
				.accessTokenValiditySeconds(120)//访问令牌仅在120s内有效
				.refreshTokenValiditySeconds(600);//刷新令牌仅在600s内有效
	}
	

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
				.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.realm(REALM + "/client");
	}


	
	
	
	
	
}
