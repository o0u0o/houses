package com.aiuiot.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiuiot.house.biz.service.UserService;
import com.aiuiot.house.common.model.User;

@Controller
public class HelloController {
	
	
	
	@RequestMapping("hello")
	public String hello(ModelMap modelMap) {
//		//获取用户 并存入集合
//		List<User> users = userService.getUsers();
//		//获取一个用户
//		User one =  users.get(0);
		
		//手动搞一个500 错误
//		if(one != null) {
//			throw new IllegalArgumentException("自己搞得异常");
//		}
		
		//放到 modelMap 中
		//modelMap.put("user", one);
		//返回freemarker 的名字 hello
		return "hello";
	}
	

	
	/**
	 * 登录页面跳转
	 */
//	@RequestMapping("/accounts/signin")
//	public String singin() {
//		return "user/accounts/singnin";
//	}
	
}
