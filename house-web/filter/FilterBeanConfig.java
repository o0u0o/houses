package com.aiuiot.house.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterBeanConfig {
	
	/**
	 * 创建Spring bean的步骤
	 * 1、构造filter
	 * 2、配置拦截的urlPattern
	 * 3、利用FilterRegistrationBean 进行包装
	 * @return
	 */
	@Bean	//该注解将返回对象识别为Spring Bean
	public FilterRegistrationBean logFilter() {
		// 创建实例
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new LogFilter());
		List<String> urlList = new ArrayList<String>();
		// 设置默认拦截所有
		urlList.add("*");
		filterRegistrationBean.setUrlPatterns(urlList);
		return filterRegistrationBean;
	}

}
