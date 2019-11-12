package com.aiuiot.house.web;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.aiuiot.house.biz.service.UserService;
import com.aiuiot.house.common.model.User;
/**
 * 
 * @Title:  AuthTest.java   
 * @Package com.aiuiot.house.web   
 * @Description:    @RunWith(SpringRunner.class) 是junit的注解  
 * 					@SpringBootTest 可以使用Spring的特性
 * @author: ZerOneth 
 * @date:   2019年6月20日 下午8:18:34   
 * @version V1.0
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=  WebEnvironment.RANDOM_PORT)	//随机启动端口
@SpringBootTest(webEnvironment=  WebEnvironment.DEFINED_PORT)	//启动默认端口
public class AuthTest{
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testAuth() {
		User user = userService.auth("jobs@techgeng.com", "123456");
		//断言 user 不为空
		assert user != null;
		System.out.println(user.getName()+" "+user.getEmail());
	}
}
