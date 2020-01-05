package com.aiuiot.house.common.model;

import lombok.Data;

import java.util.Date;

/**
 * 
 * @Title: HouseUser.java
 * @Package com.aiuiot.house.common.model
 * @Description: 房屋-用户
 * @author: ZerOneth
 * @date: 2019年7月19日 下午6:20:19
 * @version V1.0
 */
@Data
public class HouseUser {

	private Long id;

	/**
	 * 房产ID
	 */
	private Long houseId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 创见时间
	 */
	private Date createTime;

	/**
	 * 类型 代表收藏还是售卖
	 */
	private Integer type;

}
