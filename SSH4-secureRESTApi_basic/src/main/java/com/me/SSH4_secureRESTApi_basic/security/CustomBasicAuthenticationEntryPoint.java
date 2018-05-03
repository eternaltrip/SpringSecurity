package com.me.SSH4_secureRESTApi_basic.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;



public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint  {

	private static String REALM = "MY_TEST_REALM";
	
	@Override
	public void commence(final HttpServletRequest request,final  HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName(REALM);
        super.afterPropertiesSet();
	}

	

}
