package com.me.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 *  <code>@EnableGlobalMethodSecurity 可以配置多个参数:
	
	prePostEnabled :决定Spring Security的前注解是否可用 [@PreAuthorize,@PostAuthorize,..] 
	secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用
	jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用.
	@Secured 源于 Spring之前版本.它有一个局限就是不支持Spring EL表达式.
	@PreAuthorize 注解适合进入方法前的权限验证， @PreAuthorize可以将登录用户的roles/permissions参数传到方法中。
	@PostAuthorize 注解使用并不多，在方法执行后再进行权限验证。 
	例子看@com.me.springsecurity.service.SysUserService
 * </code>
 * @author Administrator
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomSuccessHandler customSuccessHandler;

	//下面的这段注入必须这样写，注入接口，然后使用qualifier引入实现类。不能直接用实现类注入，否则启动会报注入类型不满足（Unsatisfied）错误。
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}
	
	//加入密码验证
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	//加密器
	@Bean
	public PasswordEncoder  passwordEncoder() {
		 return new BCryptPasswordEncoder();  
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
        .antMatchers("/", "/home").permitAll()
        .antMatchers("/admin/**","/newuser").access("hasRole('ADMIN')")
        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
        .and().formLogin().loginPage("/login")
        .usernameParameter("ssoId").passwordParameter("password")//.successHandler(customSuccessHandler)
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }

	
	
}
