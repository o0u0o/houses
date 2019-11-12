package com.aiuiot.house.web.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiuiot.common.utils.HashUtils;
import com.aiuiot.house.biz.service.UserService;
import com.aiuiot.house.common.constants.CommonConstants;
import com.aiuiot.house.common.model.User;
import com.aiuiot.house.common.result.ResultMsg;

/**
 * 
 * @Title:  UserController.java   
 * @Package com.aiuiot.house.web.controller   
 * @Description:     User 控制器   
 * @author: ZerOneth 
 * @date:   2019年6月6日 上午8:09:04   
 * @version V1.0
 */

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 账户注册功能
	 * 注册提交流程：1.注册验证 2.发送邮件 3.验证失败重定向到注册页面
	 * 注册页获取流程：根据account对象为依据，判断是否注册页获取请求
	 * @param account
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("accounts/register") //路径需要和表单页面的一致 ModelMap返回状态数据
	public String accountsRegister(User account, ModelMap modelMap) {
		// 判断 账号传递的信息为空 或者 姓名为空 认为获取页注册的请求 直接返回注册页面
		if(account == null || account.getName() == null) {
			//返回路径为注册页面
			return "/user/accounts/register";
		}
		
		//用户验证 校验密码长度 邮箱 确认密码等
		ResultMsg resultMsg = UserHelper.validate(account);
		//成功返回注册提交也，且将用户添加到数据库 否则
		if(resultMsg.isSuccess() && userService.addAccount(account)) {
			modelMap.put("email", account.getEmail());
			//返回 注册成功页 模版
			return "/user/accounts/registerSubmit";
		}else {
			//如果注册失败! 重定向到注册页面
			return "redirect:/accounts/register?" + resultMsg.asUrlParams();
		}
		
	}
	
	/**
	 * 激活
	 * @param key
	 * @return
	 */
	@RequestMapping("accounts/verify")
	public String verify(String key) {
		boolean result = userService.enable(key);
		//判断
		if(result) {
			//成功 重定向到首页
			return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
		} else {
			//失败 重定向到注册页面，让用户重新填写
			return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
		}
	}
	
	//----------------------------登录流程----------------------------
	/**
	 * 登录接口
	 * @param req 获取参数
	 * @return
	 */
	@RequestMapping("/accounts/signin")
	public String singin(HttpServletRequest req) {
		
		//获取参数
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String target = req.getParameter("target");
		
		//登录页面的请求
		if(username == null || password == null) {
			req.setAttribute("target", target);
			return "/user/accounts/signin";
		}
		
		//用户名密码验证
		User user = userService.auth(username, password);
		if(user == null) {
			//认证失败，让用户重新填写该表单
			return "redirect:/accounts/signin?" + "target="+ target + "&username=" + username +"&"+ ResultMsg.errorMsg("用户名或者密码错误").asUrlParams();
		} else {
			//验证成功，存入session中
			HttpSession session = req.getSession(true);	//获取session true为强制创建一个
			session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);	//登录的用户
			session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
			return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
		}
	}
	
	/**
	 * 登出接口
	 * @param request
	 * @return
	 */
	@RequestMapping("accounts/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		// 将session内容删除 invalidate 注销 session
		session.invalidate();
		return "redirect:/index";
	}
	
	//---------------------------- 个人信息页 ----------------------------
	/**
	 * 1、能够提供页面信息
	 * 2、更新用户信息
	 * @param updateUser
	 * @param model
	 * @return
	 */
	@RequestMapping("accounts/profile")
	public String profile(HttpServletRequest req, User updateUser, ModelMap model) {
		//判断是提供页面信息 还是更新用户请求 如果email为空
		if(updateUser.getEmail() == null) {
			return "/user/accounts/profile";
		}
		//否则是更新用户请求
		userService.updateUser(updateUser, updateUser.getEmail()); 
		//将更新后的用户放在session内
		User query = new User();
		query.setEmail(updateUser.getEmail());
		List<User> users = userService.getUserByQuery(query);
		req.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
		return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
	}
	
	/**
	 * 修改密码操作
	 * @param email
	 * @param password
	 * @param newPassword
	 * @param confirmPassword
	 * @param model
	 * @return
	 */
	@RequestMapping("accounts/changePassword")
	public String changePassword(String email, String password, String newPassword, String confirmPassword, ModelMap model) {
		//判断原始密码是否正确
		User user = userService.auth(email, password);
		if(user == null || !confirmPassword.equals(newPassword)) {
			return "redirct:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
		}
		User updateUser = new User();
		updateUser.setPasswd(HashUtils.encryPassword(newPassword));
		userService.updateUser(updateUser, email);
		return "redirect:/accounts/profile?"+ResultMsg.successMsg("更新成功").asUrlParams();
	} 
	
}
