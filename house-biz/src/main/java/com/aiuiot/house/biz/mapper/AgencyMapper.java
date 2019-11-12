package com.aiuiot.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aiuiot.house.common.model.User;
import com.aiuiot.house.common.page.PageParams;

/**
 * 
 * @Title:  AgencyService.java   
 * @Package com.aiuiot.house.biz.mapper   
 * @Description:    AgencyMapper   
 * @author: ZerOneth 
 * @date:   2019年6月24日 上午8:28:58   
 * @version V1.0
 */
@Mapper
public interface AgencyMapper {
	
	List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);
}
