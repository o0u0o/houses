package com.aiuiot.house.biz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;

/**
 * Druid连接池的config
 * @author aiuiot
 *
 */

@Configuration
public class DruidConfig {
	
	//该注解作用将外部的配置文件与class进行数据关系进行绑定
	@ConfigurationProperties(prefix="spring.druid")	
	
	//加上bean注解，使其成为一个Spring Bean 括号内配置启动和关闭操作
	@Bean(initMethod = "init", destroyMethod = "close") 
	public DruidDataSource dataSource() {
		//实例化一个连接池
		DruidDataSource dataSource = new DruidDataSource();
		
		//将打印慢日志功能添加到连接池里面
		dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
		
		//返回连接池
		return dataSource;
	}
	
	//慢日志打印filter
	@Bean //使其成为一个Spring bean
	public Filter statFilter() {
		//实例化一个StatFilter
		StatFilter filter = new StatFilter();
		// 设置慢日志的时间 约定时间为 5000 毫秒
		filter.setSlowSqlMillis(5000);
		// 约定是否打印该慢日志 true
		filter.setLogSlowSql(true);
		//是否将日志合并起来
		filter.setMergeSql(true);
		return filter;
	}
	
	/**
	 * 添加一个监控功能 帮助我们分析sql执行的时间 和执行的操作数量的分布
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
	}
	
}
