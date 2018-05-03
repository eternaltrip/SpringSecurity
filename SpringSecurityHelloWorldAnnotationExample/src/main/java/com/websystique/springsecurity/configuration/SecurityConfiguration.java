package com.websystique.springsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



/**第二步
 * 除了框架配置，这里是security配置的第一步。（这里是是用java的配置方法，也可以使用xml的方式进行配置。）
 * 
 * 1 @EnableWebSecurity这里配置创建一个叫 springSecurityFilterChain 的servlet过滤器，
 * 来对项目中的所有安全相关的事项负责（所有的url，验证用户名密码，表单重定向等）
 * 相当于web.xml中的配置
 * 	<filter>
	    <filter-name>springSecurityFilterChain</filter-name>
	    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>
 * 
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

	
	
	/**2
	 * configureGlobalSecurity这个方法是为AuthenticationManagerBuilder配置用户授权和角色信息
	 * AuthenticationManagerBuilder（权限管理创建器）负责所有权限请求的AuthenticationManager（权限管理器）
	 * 这里只是配置再内存中的权限验证。后期可以改为基于数据的权限验证
	 * 
	 */
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("bill").password("bill").roles("USER");  
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");  
        auth.inMemoryAuthentication().withUser("dba").password("dba").roles("ADMIN","DBA");
	}
	
	
	/**
	 * 3
	 * 重写configue方法，来配置httpsecurity基于http请求的安全认证。
	 * 默认是拦截所有请求。也可以通过配置实现其他的具体拦截。如下
	 * 看到‘/’ & ‘/home’这种Url配置是不安全的，任何人都可以访问。
	 * 只有具有ADMIN权限的用户才可以访问符合‘/admin/**’的url。只能够同时具有ADMIN 和 DBA权限的人才可以访问符合‘/db/**’ 的Url 。
	 * formLogin 方法提供了基于表单的权限验证，将会产生一个默认的对用户的表单请求。也可以自定义登录表单。
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()  
	        .antMatchers("/", "/home").permitAll()// 
	        .antMatchers("/admin/**").access("hasRole('ADMIN')")  
	        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")  
	        .and().formLogin()  
	        .and().exceptionHandling().accessDeniedPage("/Access_Denied");  
	}
	
	/**
	 * 2,3的配置相当于spring.xml中
	 * <s:http auto-config="true">
		<s:intercept-url pattern="/" access="permitAll"/>
		<s:intercept-url pattern="/home" access="permitAll"/>
		<s:intercept-url pattern="/admin**" access="hasRole('ADMIN')"/>
		<s:intercept-url pattern="/dba**" access="hasRole('ADMIN') and hasRole('DBA')"/>
		<s:form-login authentication-failure-url="/Access_Denied"/>
	</s:http>
	
	<s:authentication-manager>
		<s:authentication-provider>
			<s:user-service>
				<s:user name="bill" password="abc123" authorities="ROLE_USER"/>
				<s:user name="admin" password="root123" authorities="ROLE_ADMIN"/>
				<s:user name="dba" password="root123" authorities="ROLE_ADMIN,ROLE_DBA"/>
			</s:user-service>
		</s:authentication-provider>
	</s:authentication-manager>

	 * 
	 */
	
	

}
