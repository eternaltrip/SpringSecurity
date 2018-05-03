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
	private CustomSuccessHandler customSuccessHandler;
	
	
	@Autowired
	protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
		auth.inMemoryAuthentication().withUser("dba").password("dba").roles("DBA","ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/","/home").access("hasRole('USER')")
			.antMatchers("/admin/**").access("hasRole('ADMIN')")
			.antMatchers("/db/**").access("hasRole('DBA') and hasRole('ADMIN')")
			//此类和前几篇文章类似，只是下面这点有区别：formLogin().loginPage("/login").successHandler(customSuccessHandler)
			//重点是customSuccessHandler，这个类定义了处理customSuccessHandler的逻辑。在本例中根据 角色USER/ADMIN/DBA重定向到home/admin/db 
			.and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
			.usernameParameter("ssoId").passwordParameter("password")
			.and().csrf()
			.and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}
	
	
	
	

}
