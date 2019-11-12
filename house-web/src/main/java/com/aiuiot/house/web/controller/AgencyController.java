package com.aiuiot.house.web.controller;

import java.util.List;

import com.aiuiot.house.biz.service.HouseService;
import com.aiuiot.house.biz.service.RecommendService;
import com.aiuiot.house.common.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
/**
 * 
 * @Title:  AgencyController.java   
 * @Package com.aiuiot.house.web.controller   
 * @Description:    经纪人Controller  
 * @author: ZerOneth 
 * @date:   2019年7月19日 下午7:10:43   
 * @version V1.0
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiuiot.house.biz.service.AgencyService;
import com.aiuiot.house.common.model.House;
import com.aiuiot.house.common.model.User;
import com.aiuiot.house.common.page.PageData;
import com.aiuiot.house.common.page.PageParams;

/**
 * 
 * @Title:  AgencyController.java   
 * @Package com.aiuiot.house.web.controller   
 * @Description:    经纪人控制类 
 * @author: ZerOneth 
 * @date:   2019年8月1日 下午3:01:36   
 * @version V1.0
 */

@Controller
public class AgencyController {
	
	@Autowired
	private AgencyService agencyService;

	@Autowired
	private RecommendService recommendService;

	@Autowired
	private HouseService houseService;
	

	/**
	 * 访问经纪人列表
	 * @param pageSize 分页大小
	 * @param pageNum 分页数
	 * @param modelMap modelMap对象
	 * @return
	 */
	@RequestMapping("/agency/agentList")
	public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
//		if(pageSize == null) {
//			pageSize = 6;
//		}
		PageData<User> ps =  agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
		//List<House> houses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		//modelMap.put("recomHouses", houses);
		modelMap.put("ps", ps);
		return "/user/agent/agentList";
	}
	

	/**
	 * 访问经纪人详情
	 * @param id
	 * @param modelMap 模版引擎设置的参数
	 * @return
	 */
	@RequestMapping("/agency/agentDetail")
	public String agentDetail(Long id, ModelMap modelMap) {
		User user = agencyService.getAgentDeail(id);
		//获取热门房产
		//List<House> houses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		House query = new House();
		query.setUserId(id);
		query.setBookmarked(false);
		PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3,1));
		if (bindHouse != null){
			modelMap.put("bindHouse", bindHouse.getList());
		}
		//modelMap.put("recomHouses", houses);
		modelMap.put("agent", user);
		//modelMap.put("agencyName", user.getAgencyName());
		return "/user/agent/agentDetail";
	}
	
}
