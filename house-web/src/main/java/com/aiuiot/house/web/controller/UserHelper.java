package com.aiuiot.house.web.controller;


import org.apache.commons.lang3.StringUtils;

import com.aiuiot.house.common.model.User;
import com.aiuiot.house.common.result.ResultMsg;

import freemarker.template.utility.StringUtil;

//一个工具类（帮助类）
public class UserHelper {
	
	/**
	 * 返回ResultMsg对象 ResultMsg对象的作用是用来返回页面信息（成功 错误）
	 */
	//如果验证通过返回空消息
	public static ResultMsg validate(User account) {
		
		// 判断email是否存在
		if(StringUtils.isBlank(account.getEmail())) {
			return ResultMsg.errorMsg("Email 有误!");
		}
		
		// 判断name是否存在
		if(StringUtils.isBlank(account.getName())) {
			return ResultMsg.errorMsg("名字有误!");
		}
		
		// 确认密码和密码要保持一致
		if(StringUtils.isBlank(account.getConfirmPasswd()) || StringUtils.isBlank(account.getPasswd())) {
			return ResultMsg.errorMsg("名字有误!");
		}
		
		//判断密码长度 密码长度 不能小于6位
		if(account.getPasswd().length() < 6) {
			return ResultMsg.errorMsg("密码大于6位!");
		}
		
		//验证成功返回空串
		return ResultMsg.successMsg("");
		
	}
}
