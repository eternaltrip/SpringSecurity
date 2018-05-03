package com.websystique.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * 第四步。
 * 添加springmvc的配置类
 * 以下配置说明，该配置是一个springmvc的架构
 * 扫描com.websystique.springsecurity下的所有带有标签注入的类
 * @author Administrator
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.websystique.springsecurity")
public class HelloWorldConfiguration {
	
	@Bean
	public ViewResolver resolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");  
        viewResolver.setSuffix(".jsp");  
		
		
		return viewResolver;
		
	}
	

}
