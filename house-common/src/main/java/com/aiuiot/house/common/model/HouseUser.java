package com.aiuiot.house.common.model;

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
public class HouseUser {
	private Long id;
	private Long houseId;
	private Long userId;
	private Date createTime;
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
