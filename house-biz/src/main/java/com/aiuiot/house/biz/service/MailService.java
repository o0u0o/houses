package com.aiuiot.house.biz.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.aiuiot.house.biz.mapper.UserMapper;
import com.aiuiot.house.common.model.User;


/**
 * 
 * @Title:  MailService.java   
 * @Package com.aiuiot.house.biz.service   
 * @Description:    MailService 处理邮箱 需要在pom 文件中引入Spring-Mail相关的起步依赖  
 * @author: ZerOneth 
 * @date:   2019年6月6日 上午9:31:33   
 * @version V1.0
 */
//使用注解标记为Service Bean
@Service
public class MailService {
	
	//注册MailSender 该Bean是起步依赖创建的Bean
	@Autowired
	private JavaMailSender mailSender;
	
	//创建 发送邮件的来源 (在配置文件中)
	@Value("${spring.mail.username}")
	private String from;	//邮件来源
	
	//将配置文件中的域名注入进来
	@Value("${domain.name}")
	private String domainName;
	
	@Autowired
	private UserMapper userMapper;
	
	
	/**
	 * 使用本地缓存 借助于Guava-Catch做本地缓存
	 * 创建 Guava-Catch的对象（本地缓存使用） 
	 * 1、设置最大存储空间为100 设置
	 * 2、过期时间为15分钟 
	 * 3、然后过期后将为正在注册的用户和密码删除，以便与下次注册
	 */
	private final Cache<String, String> registerCache = CacheBuilder.newBuilder()
			.maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES)
			.removalListener(new RemovalListener<String, String>() {

				//过期时触发的操作
				@Override
				public void onRemoval(RemovalNotification<String, String> notification) {
					String email = notification.getValue();
					User user = new User();
					user.setEmail(email);
					List<User> targetUser = userMapper.selectUserByQuery(user);
					if(!targetUser.isEmpty() && Objects.equal(targetUser.get(0).getEnable(), 0)) {
						//userMapper.delete(notification.getValue());
						userMapper.delete(email);
					}
				}
				
			}).build();
	
	/**
	 * 该方法用于发送邮件
	 * @param title
	 * @param url
	 * @param email
	 */
	@Async
	public void sendMail(String title, String url, String email) {
		//构建邮件
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);		// 设置邮件发送来源
		message.setSubject(title);	// 标题
		message.setTo(email);		// 设置收信人
		message.setText(url);		// 设置发送内容
		mailSender.send(message);	// 发送邮件
	}
	
	/**
	 * 该方法处理发送邮件
	 * 1、缓存key-email的关系 本地缓存使用 cache
	 * 2、借助Spring mail 发送邮件
	 * 3、借助异步框架进行异步操作 （需要使用到异步框架 该框架已经继承在SpringBoot 中引入注解即可）
	 * @param email
	 */
	@Async	//开启异步操作
	public void registerNotify(String email) {
		//上传一个key  通过 RandomStringUtils（随机字符串） 生成一个10位的key
		String randomKey = RandomStringUtils.randomAlphabetic(10);	//随机生成字符串 10位
		
		// 将中的随机key和email绑定起来保存在catch 本地缓存 中
		registerCache.put(randomKey, email);
		
		//发送邮件操作（需要引入 Spring-Mail 起步依赖） 
		String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;	//构建激活连接
		
		//发邮件操作 房产平台用户激活
		sendMail("账户激活 - 平台测试", url, email); //参数（标题，邮件内容，email）
		
		//开启异步（在该方法前添加@Async, 且需要在启动类中添加 @EnableAsync 注解）
		
		
	}

	/**
	 * 激活用户
	 * @param key
	 * @return
	 */
	public boolean enable(String key) {
		//从缓存中获取email
		String email = registerCache.getIfPresent(key);
		//如果缓存中不存在，过期 返回false
		if(StringUtils.isBlank(key)) {
			return false;
		}
		User updateUser = new User();
		updateUser.setEmail(email);
		updateUser.setEnable(1);
		userMapper.update(updateUser);
		registerCache.invalidate(key);
		return true;
	}

}
