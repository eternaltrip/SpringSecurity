package com.me.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



/**
 * 第一步，注册一个名叫springSecurityChain的servlet过滤器。
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication().withUser("dba").password("dba").roles("DBA","ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/home").permitAll()
			.antMatchers("/admin/**").access("hasRole('ADMIN')")
			.antMatchers("/db/**").access("hasRole('DBA') and hasRole('ADMIN')")
			.and().formLogin().loginPage("/login")
			.usernameParameter("ssoId").passwordParameter("password")
			.and().csrf()
			.and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}
	
	
	
	

}
