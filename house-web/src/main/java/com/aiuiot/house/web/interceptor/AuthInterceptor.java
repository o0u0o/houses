package com.aiuiot.house.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aiuiot.house.common.constants.CommonConstants;
import com.aiuiot.house.common.model.User;
import com.google.common.base.Joiner;

/**
 * 登录鉴权 拦截器
 * 实现HandlerInterceptor
 * @author aiuiot
 *
 */
//定义为SpringBean 
@Component
public class AuthInterceptor implements HandlerInterceptor{

	/**
	 * 在Controller执行之前进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//获取全部的Parameter
		Map<String, String[]> map = request.getParameterMap();
		map.forEach((k, v)->{
			if(k.equals("errorMsg")||k.equals("successMsg")||k.equals("target")) {
				//将参数设置到Attribute
				request.setAttribute(k, Joiner.on(",").join(v));
			}
		});
		
		String reqUri = request.getRequestURI();
		//判断uri 是否是一个静态資源请求 以及error请求 
		if(reqUri.startsWith("/static")||reqUri.startsWith("/error")) {
			return true; //不需要拦截
		}
		
		//获取session 参数 true 如果没有session则自动创建
		HttpSession session = request.getSession(true);

		//通过session对象获取用户对象
		User user = (User)session.getAttribute(CommonConstants.USER_ATTRIBUTE);

		// 判断用户对象是否为空
		if(user != null) {
			//不为空，将user写入ThreadLocal
			UserContext.setUser(user);
		}
		return true;
	}

	/**
	 * 在Controller执行之后进行拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	/**
	 * 在页面渲染之后进行拦截
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//将 UserContext的用户移除掉
		UserContext.remove();
	}

}
