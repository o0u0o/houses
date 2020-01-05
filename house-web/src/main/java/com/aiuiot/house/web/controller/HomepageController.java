package com.aiuiot.house.web.controller;

import com.aiuiot.house.biz.service.RecommendService;
import com.aiuiot.house.common.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aiuiot.house.biz.service.UserService;

import java.util.List;

/**
 * 
 * @Title:  HomepageController.java   
 * @Package com.aiuiot.house.web.controller   
 * @Description:    处理首页跳转   
 * @author: ZerOneth 
 * @date:   2019年7月1日 下午3:38:14   
 * @version V1.0
 */
@Controller
public class HomepageController {
	
	  @Autowired
	  private RecommendService recommendService;
	  
	  @Autowired
	  private UserService userService;

	/**
	 * 账号注册
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	  public String accountsRegister(ModelMap modelMap){
	  	//查询新上房源
	    List<House> houses =  recommendService.getLastest();
	    modelMap.put("recomHouses", houses);
	    return "/homepage/index";
	  }


	/**
	 * 首页跳转
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("")
	  public String index(ModelMap modelMap){
	    return "redirect:/index";
	  }
	

}
