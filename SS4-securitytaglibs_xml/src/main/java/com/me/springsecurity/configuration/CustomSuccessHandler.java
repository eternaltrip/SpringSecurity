package com.me.springsecurity.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 处理登录成功，不同的角色的跳转问题 拓展SimpleUrlAuthenticationSuccessHandler类的，重写了handle()方法，
 * 简单的调用重定向使用配置的RedirectStrategy，其中通过determineTargetUrl方法返回对应的url 。
 * 此方法从Authentication 对象中提取角色然后根据 角色构建 对应的url.最后在 Spring Security
 * 负责所有重定向事务的RedirectStrategy （重定向策略）来重定向请求到指定的url
 *
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * 重写handler方法
	 */
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String targeturl = determineTargetUrl(authentication);
		if(response.isCommitted()) {
			System.out.println("Can't redirect");
			return;
		}
		//这个方法对url进行了encode处理。然后调用response.sendRedirect(url)
		redirectStrategy.sendRedirect(request, response, targeturl);
	}

	
	//实行不同的跳转
	protected String determineTargetUrl(Authentication authentication) {
		String url = "";

		Collection<? extends GrantedAuthority> collections = authentication.getAuthorities();

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority grantedAuthority : collections) {
			roles.add(grantedAuthority.getAuthority());
		}

		
		
		if (isDba(roles)) {
			url = "/db";
		} else if (isAdmin(roles)) {
			url = "/admin";
		} else if (isUser(roles)) {
			url = "/home";
		} else {
			url = "/accessDenied";
		}

		return url;

	}

	
	//判断角色类型
	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_USER")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

	private boolean isDba(List<String> roles) {
		if (roles.contains("ROLE_DBA")) {
			return true;
		}
		return false;
	}

	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

}
