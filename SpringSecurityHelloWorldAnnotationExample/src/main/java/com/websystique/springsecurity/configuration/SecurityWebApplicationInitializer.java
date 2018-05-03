package com.websystique.springsecurity.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


/**
 * 第一步
 * 定制初始化war包中的springSecurityFilter（第一步中的）注册类。
 * 配置之后会对所有的请求都进行拦截，然后使用第一步中的具体拦截方式进行处理（根据请求的url不同，来判断是否进行验证）
 * 对应也有xml的配置方式。
 * 相当于web.xml中
 * 	<filter-mapping>
	    <filter-name>springSecurityFilterChain</filter-name>
	    <url-pattern>/*</url-pattern>
	</filter-mapping>
 * @author Administrator
 *
 */

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
