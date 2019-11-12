package com.aiuiot.house.biz.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aiuiot.common.utils.BeanHelper;
import com.aiuiot.common.utils.HashUtils;
import com.aiuiot.house.biz.mapper.UserMapper;
import com.aiuiot.house.common.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;

@Service
public class UserService {

	//将 FileService 注入进来
	@Autowired
	private FileService fileService;
	
	//将MailService 注入进来
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Value("${file.prefix}")
	private String imgPrefix;

	public List<User> getUsers(){
		return userMapper.selectUsers();
	}

	/**
	 * 添加用户功能
	 * 1、插入数据库，非激活状态；密码加盐MD5；保存头像到本地
	 * 2、生产key，绑定email
	 * 3、发送邮件给用户
	 * @param account
	 * @returnzz
	 */
	@Transactional(rollbackFor = Exception.class) //添加事务注解
	public boolean addAccount(User account) {
		//对用户密码进行加盐加密操作
		account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
		//将用户头像保存到本地 fileService
		List<String> imgList = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
		//判断 imgList（头像图片的路径） 是否为空
		if(!imgList.isEmpty()) {
			//将图片的相对路径存入就行了
			account.setAvatar(imgList.get(0));
		}
		//BeanHelper是一个工具类，作用将默认值填充进去
		BeanHelper.setDefaultProp(account, User.class);
		BeanHelper.onInsert(account); 	//将创建时间设置进去
		account.setEnable(0);			//0-未激活状态
		//调用userMapper的insert()方法进行用户插入
		userMapper.insert(account);	
		//发送邮件
		mailService.registerNotify(account.getEmail());
		return true;
	}

	public boolean enable(String key) {
		//如果合法 更新用户
		return mailService.enable(key);
	}

	/**
	 * 用户名和密码验证操作的方法
	 * @param username 传入的用户名
	 * @param password 传入的密码
	 * @return
	 */
	public User auth(String username, String password) {
		//构造查询对象
		User user = new User();
		user.setEmail(username);
		user.setPasswd(HashUtils.encryPassword(password));
		user.setEnable(1);	//被激活的用户
		List<User> list = getUserByQuery(user);
		//如果不为空
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public List<User> getUserByQuery(User user) {
		List<User> list = userMapper.selectUserByQuery(user);
		//对用户图像进行处理 一下语法是Java8的新特性
		//将相对路径加入一个前缀
		list.forEach(u ->{
			u.setAvatar(imgPrefix + u.getAvatar());
		});
		return list;
	}

	public void updateUser(User updateUser, String email) {
		updateUser.setEmail(email);
		BeanHelper.onUpdate(updateUser);
		userMapper.update(updateUser);
	}

	/** 通过ID获取 */
	public User getUserById(Long id) {
		User queryUser = new User();
		queryUser.setId(id);
		List<User> users = getUserByQuery(queryUser);
		if (!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}
	
	
}
