package com.aiuiot.house.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 
 * @Title:  ErrorHandler.java   
 * @Package com.aiuiot.house.web.controller   
 * @Description:    异常处理器   
 * @author: ZerOneth 
 * @date:   2019年6月20日 上午10:47:48   
 * @version V1.0
 */
@ControllerAdvice //标记为Controller辅助类
public class ErrorHandler {
	//需要输出一些日志，需要定义loger
	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
	
	@ExceptionHandler(value = {Exception.class,RuntimeException.class})
	public String error500(HttpServletRequest request, Exception e) {
		logger.error(e.getMessage(),e);						//输出堆栈信息
		logger.error(request.getRequestURL() + "遇到 500");	//输出请求路径
		return "error/500";									// 定义返回的错误页面
	}
	
}
