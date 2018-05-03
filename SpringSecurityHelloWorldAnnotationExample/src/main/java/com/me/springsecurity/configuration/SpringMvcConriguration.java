package com.me.springsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;



/**
 * 配置springmvc的注解扫描包路径，并配置view返回路径
 * 并配置静态文件映射路径（需要继承WebMvcConfigurerAdapter）
 * @author Administrator
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.me.**")
public class SpringMvcConriguration  extends WebMvcConfigurerAdapter{  

	//jsp页面路径
	@Bean
	public ViewResolver resolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	/**
	 * 设置静态文件路径
	 * @param registry
	 */
	@Override  
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  
        registry.addResourceHandler("/statics/**").addResourceLocations("/statics/");  
    }  

}
