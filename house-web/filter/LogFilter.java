package com.aiuiot.house.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 创建一个filter 打印请求日志
 * @author aiuiot
 *
 */
public class LogFilter implements Filter {
	//添加打印日志
	private Logger logger = LoggerFactory.getLogger(Logger.class);

	/**
	 * 容器启动时执行
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

	/**
	 * 请求拦截时候执行
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 打印信息
		logger.info("request请求已经到来");
		// 将request和response转发到下一个filter
		chain.doFilter(request, response);

	}

	/**
	 * 容器销毁执行
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
