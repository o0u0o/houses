package com.aiuiot.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *  配置类，配置多个拦截器的执行顺序
 *  需要集成 WebMvcConfigurerAdapter
 * @author aiuiot
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	//注入进拦截器 authActionInterceptor
	@Autowired
	private AuthActionInterceptor authActionInterceptor;
	
	//注入进拦截器 authInterceptor
	@Autowired
	private AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//添加拦截去 registry 有顺序 依次
		registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**"); 	//排除拦截的url
		registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile"); 				//需要拦截的请求（需要登录才可访问）
		super.addInterceptors(registry);
	}

}
