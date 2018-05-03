package com.me.SSH4.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static String REMEMBER_ME = "remember-me";

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private PersistentTokenRepository tokenRepository;
	
	@Autowired
	private CustomSecurityInterceptor securityInterceptor;
	
	@Autowired
	private CustomPostProcessor customPostProcessor;
	

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);// 数据访问接口
		auth.authenticationProvider(authenticationProvider());// 加密验证提供者
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/static/**").permitAll()
			.antMatchers("/", "/list")
				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
			.antMatchers("/newuser/**", "/delete-user-*")
				.access("hasRole('ADMIN')")
			.antMatchers("/edit-user-*")
				.access("hasRole('ADMIN') or hasRole('DBA')")
			.and()
				.formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("ssoId").passwordParameter("password").permitAll()
			.and()
				.csrf()
			.and()
				.rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository).tokenValiditySeconds(86400)
			.and()
				.exceptionHandling().accessDeniedPage("/Access_Denied");
		 http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
		
//		http.formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("ssoId").passwordParameter("password").permitAll()
//			.and()
//				.exceptionHandling().accessDeniedPage("/login?error")
//			.and()
//				.csrf()
//			.and()
//				.rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository).tokenValiditySeconds(86400)
//			.and()
//				.exceptionHandling().accessDeniedPage("/Access_Denied")
//			.and()
//			.authorizeRequests()
//			.antMatchers("/", "/list")
//				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
//			.antMatchers("/newuser/**", "/delete-user-*")
//				.access("hasRole('ADMIN')")
//			.antMatchers("/edit-user-*")
//				.access("hasRole('ADMIN') or hasRole('DBA')")
//			.anyRequest().authenticated()
//			.withObjectPostProcessor(customPostProcessor);//设置后置处理程序对象
		
	}

	// 所有的访问验证都进行加密，应用中任何地方验证都将对密码进行比对。
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);// 设置访问数据库的接口
		authenticationProvider.setPasswordEncoder(passwordEncoder());// 加密方式
		return authenticationProvider;
	}

	// 密码加密
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// remember me
	@Bean
	public PersistentTokenBasedRememberMeServices rememberMeServices() {
		PersistentTokenBasedRememberMeServices basedRememberMeServices = new PersistentTokenBasedRememberMeServices(
				REMEMBER_ME, userDetailsService, tokenRepository);
		return basedRememberMeServices;
	}
	
//	匿名认证
	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

}
