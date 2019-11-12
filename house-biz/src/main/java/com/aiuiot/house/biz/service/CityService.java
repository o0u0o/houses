package com.aiuiot.house.biz.service;

import org.springframework.stereotype.Service;

import com.aiuiot.house.common.model.City;
import com.google.common.collect.Lists;

/**
 * 
 * @Title:  CityService.java   
 * @Package com.aiuiot.house.biz.service   
 * @Description:    CityService   
 * @author: ZerOneth 
 * @date:   2019年6月23日 上午10:26:01   
 * @version V1.0
 */
@Service
public class CityService {

	public Object getAllCitys() {
		City city = new City();
		city.setCityCode("110000");
		city.setCityName("北京");
		city.setId(1);
		return Lists.newArrayList(city);
	}

}
