package com.aiuiot.house.web.controller;

import com.aiuiot.house.biz.service.*;
import com.aiuiot.house.common.constants.CommonConstants;
import com.aiuiot.house.common.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiuiot.house.common.model.House;
import com.aiuiot.house.common.model.HouseUser;
import com.aiuiot.house.common.model.UserMsg;
import com.aiuiot.house.common.page.PageData;
import com.aiuiot.house.common.page.PageParams;

import java.util.List;

/**
 * 
 * @Title:  HouseController.java   
 * @Package com.aiuiot.house.web.controller   
 * @Description:    房屋控制器   
 * @author: ZerOneth 
 * @date:   2019年6月21日 上午8:42:47   
 * @version V1.0
 */
@Controller
public class HouseController {
	
	//将 HouseService 注入进来
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private CityService cityService;
	
	//经纪人Service
	@Autowired
	private AgencyService agencyService;

	@Autowired
	private CommentService commentService;

	//@Autowired
	//private RecommendService recommendService;

	@Autowired
	private RecommandService recommandService;
	
	//------------------- 房产列表方法 --------------------
	/**
	 * 功能:
	 * 1.实现分页
	 * 2.支持小区搜索 类型搜索 
	 * 3.支持排序
	 * 4.支持展示图片、价格、标题、地址等信息
	 * 使用model的就是entity实体类
	 * @return
	 */
	@RequestMapping("/house/list")
	public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
		//对house进行查询
		PageData<House> ps = houseService.queryHouse(query,PageParams.build(pageSize, pageNum));
		List<House> hotHouses = recommandService.getHotHouse(CommonConstants.RECOM_SIZE);//3个热门房产
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("ps", ps);
		modelMap.put("vo", query);
		return "/house/listing";
	}
	
	@RequestMapping("/house/toAdd")
	public String toAdd(ModelMap modelMap) {
		modelMap.put("city", cityService.getAllCitys());
		modelMap.put("communitys", houseService.getAllCommunitys());
		return "/house/add";
	}
	
	@RequestMapping("/house/add")
	public String doAdd() {
		//待完成
		return "redirect:/house/ownlist";
	}
	
	@RequestMapping("/house/ownlist")
	public String owmlist() {
		
		return "/house/ownlist";
	}
	
	//------------------- 房产详情 --------------------
	/**
	 * step1:查询房屋详情
	 * step2:查询关联经纪人
	 * @param id 
	 * @return
	 */
	@RequestMapping("house/detail")
	public String houseDetail(Long id, ModelMap modelMap) {
		House house = houseService.queryOneHouse(id);	//查询房屋信息
		HouseUser houseUser = houseService.getHouseUser(id);
		recommandService.increase(id);	//点击详情页热度加1
		List<Comment> comments = commentService.getHouseComments(id,8);
		//查询经纪人
		if(houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
			modelMap.put("agent", agencyService.getAgentDeail(house.getUserId()));
		}

		List<House> reHouses = recommandService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", reHouses);
		modelMap.put("house", house);
		modelMap.put("commentList", comments);
		return "/house/detail";
	}
	
	//留言功能
	@RequestMapping("house/leaveMsg")
	public String houseMsg(UserMsg userMsg) {
		houseService.addUserMsg(userMsg);	//添加用户留言信息 （与激活邮件类似）
		return "redirect:/house/detail?id=" + userMsg.getHouseId();
	}
}
