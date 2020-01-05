package com.aiuiot.house.biz.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aiuiot.house.common.model.Community;
import com.aiuiot.house.common.model.House;
import com.aiuiot.house.common.model.HouseUser;
import com.aiuiot.house.common.model.UserMsg;
import com.aiuiot.house.common.page.PageParams;
/**
 * 
 * @Title:  HouseMapper.java   
 * @Package com.aiuiot.house.biz.mapper   
 * @Description:    HouseMapper 接口  
 * @author: ZerOneth 
 * @date:   2019年6月21日 上午11:01:10   
 * @version V1.0
 */
@Mapper
public interface HouseMapper {

	//查询房产 需添加注解
	public List<House> selectPageHouses(@Param("house")House house,@Param("pageParams")PageParams pageParams);

	//返回总数
	public Long selectPageCount(@Param("house")House query);

	public List<Community> selectCommunity(Community community);
	
	//public HouseUser selectSaleHouseUser(@Param("id") Long houseId);
	
	public HouseUser selectSaleHouseUser(@Param("id") Long houseId);
	


	public void insertUserMsg(UserMsg userMsg);

	// 添加房产
	public int insert(House house);

	public HouseUser selectHouseUser(@Param("userId") Long userId, @Param("id") Long houseId, @Param("type") Integer type);

	//插入房产
	public int insertHouseUser(HouseUser houseUser);

}
