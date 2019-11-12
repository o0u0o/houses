package com.aiuiot.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aiuiot.house.common.model.User;

@Mapper
public interface UserMapper {
	
	//查询用户
	public List<User> selectUsers();

	//插入用户
	public int insert(User account);

	//删除用户
	public int delete(String email);

	//更新用户
	public int update(User updateUser);

	public List<User> selectUserByQuery(User user);
	
}
