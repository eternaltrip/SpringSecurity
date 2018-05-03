package com.me.SSH4_secureRESTApi_basic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecuriyConfig extends WebSecurityConfigurerAdapter{

	private static String REALM = "MY_TEST_REALM";
	 
	@Autowired
	protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/user/**").hasAnyRole("ADMIN")
			.and().httpBasic().realmName(REALM).authenticationEntryPoint(getCustomBasicAuthenticationEntryPoint())
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//不设置session
	
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getCustomBasicAuthenticationEntryPoint() {
		return new CustomBasicAuthenticationEntryPoint();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**");
	}
	
	

}
