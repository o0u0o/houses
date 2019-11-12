package com.aiuiot.house.web.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aiuiot.house.common.model.User;

/**
 * 拦截器
 * @author aiuiot
 * 
 * Spring拦截器的编写步骤
 * 1、实现 HandlerInterceptor 接口，编写拦截器
 * 2、将拦截器加入 WebMvcConfig 并绑定url path pattern 
 * 3、将拦截器注册
 */
@Component //使该class 成为 SpringBean
public class AuthActionInterceptor implements HandlerInterceptor{

	/**
	 * 在controller之前执行的
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 处理，如果用户未登录 重定向到登录页面
		/**
		 * 1、通过 UserContext 获取用户
		 * 2、判断 User 对象是否为空，如果为空 重定向到登录页面
		 */
		User user = UserContext.getUser();
		//判断用户对象是否为空
		if(user == null) {
			String msg = URLEncoder.encode("请先登录", "utf-8");	//将现有的URL 记录下来，返回提示信息
			String target = URLEncoder.encode(request.getRequestURI().toString(),"utf8");
			// 如果是get 请求
			if("GET".equalsIgnoreCase(request.getMethod())) {
				response.sendRedirect("/accounts.signin?errorMsg="+ msg +" &target="+ target);
			} else { //如果是post 请求
				response.sendRedirect("/accounts.signin?errorMsg="+ msg);
			}
			
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
