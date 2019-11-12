package com.aiuiot.house.web.interceptor;

import com.aiuiot.house.common.model.User;

/**
 * 用于进行包装
 * @author aiuiot
 *
 */
public class UserContext {

	//USER_HOLDER
	private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();
	
	// 用于在业务代码 设置 User
	public static void setUser(User user) {
		USER_HOLDER.set(user);
	}
	
	//用于在 业务代码 移除 User
	public static void remove() {
		USER_HOLDER.remove();
	}
	
	//在业务代码中 获取 User
	public static User getUser() {
		return USER_HOLDER.get();
	}
	
}
