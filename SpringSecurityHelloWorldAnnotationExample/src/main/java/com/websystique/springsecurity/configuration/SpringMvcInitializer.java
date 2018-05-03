package com.websystique.springsecurity.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 第五步
 * 添加initializer（初始化器）类
 * 
 * 注意初始化器继承自AbstractAnnotationConfigDispatcherServletInitializer ，
 * 它是所有WebApplicationInitializer 实现的基类. 
 * 在Servlet 3.0 环境下，通过实现WebApplicationInitializer 来配置ServletContext 
 * 这意味着我们将不使用web.xml而且将在支持servlet3.0容器下发布此应用
 * @author Administrator
 *
 */

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	
	/**
	 * 配置springmvc
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class[] {HelloWorldConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	

}
