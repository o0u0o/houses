package com.aiuiot.common.utils;
/**
 * MD5加盐加密
 * 为什么要使用加盐加密：
 * 因为MD5很容易被暴力破解
 * 将用户密码和字符串同时进行md5
 * 
 * @author aiuiot
 *
 */

import java.nio.charset.Charset;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HashUtils {
	private static final HashFunction FUNCTION = Hashing.md5();
	
	// 编写一个盐 以“aiuiot.com”作为盐 加盐的作用防止md5暴利破解 将用户密码和一个字符串同时md5
	private static final String SALT = "aiuiot.com";
	
	public static String encryPassword(String password) {
		HashCode hashCode = FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
		return hashCode.toString();
	}
	
	//测试加盐加密
	public static void main(String[] args) {
		String pwd = "123456";
		System.out.println(encryPassword(pwd));
	}
	
}
