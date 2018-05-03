package com.me.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.me.springsecurity.service.CustomUserDetailsService;


/**
 * 第一步，注册一个名叫springSecurityChain的servlet过滤器。
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomSuccessHandler customSuccessHandler;

	
	//下面的这段注入必须这样写，注入接口，然后使用qualifier引入实现类。不能直接用实现类注入，否则启动会报注入类型不满足（Unsatisfied）错误。
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	

	@Autowired
	protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home").permitAll()
				.antMatchers("/admin/**").access("hasRole('ADMIN')")
				.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
				.and().formLogin().loginPage("/login").usernameParameter("ssoId").passwordParameter("password")
				.and().csrf()
				.and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}

}
