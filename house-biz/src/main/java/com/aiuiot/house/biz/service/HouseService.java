package com.aiuiot.house.biz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aiuiot.common.utils.BeanHelper;
import com.aiuiot.house.biz.mapper.HouseMapper;
import com.aiuiot.house.common.model.Community;
import com.aiuiot.house.common.model.House;
import com.aiuiot.house.common.model.HouseUser;
import com.aiuiot.house.common.model.User;
import com.aiuiot.house.common.model.UserMsg;
import com.aiuiot.house.common.page.PageData;
import com.aiuiot.house.common.page.PageParams;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 
 * @Title:  HouseService.java   
 * @Package com.aiuiot.house.biz.service   
 * @Description:    HouseService   
 * @author: ZerOneth 
 * @date:   2019年6月21日 上午8:44:41   
 * @version V1.0
 */
@Service
public class HouseService {
	
	@Value("${file.prefix}")
	private String imgPrefix;
	
	@Autowired
	private HouseMapper houseMapper;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private MailService mailService;
	
	/**
	 * 查询house
	 * 1.查询小区
	 * 2.添加图片服务器的地址前缀
	 * 3.构建分页结果
	 * @param query
	 * @param build
	 */
	public PageData<House> queryHouse(House query, PageParams pageParams) {
		List<House> houses = Lists.newArrayList();
		if(!Strings.isNullOrEmpty(query.getName())) {
			Community community = new Community();
			community.setName(query.getName());
			List<Community> communites = houseMapper.selectCommunity(community);
			if(!communites.isEmpty()) {
				query.setCommunityId(communites.get(0).getId());
			}
		}
		houses = queryAndSetImg(query,pageParams);
		Long count = houseMapper.selectPageCount(query);
		return PageData.buildPage(houses, count, pageParams.getPageSize(), pageParams.getPageNum());
		
	}

	public List<House> queryAndSetImg(House query, PageParams pageParams) {
		List<House> houses = houseMapper.selectPageHouses(query, pageParams);
		houses.forEach(h ->{
			h.setFirstImg(imgPrefix + h.getFirstImg());
			h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
			h.setFloorPlanList(h.getFloorPlanList().stream().map(pic -> imgPrefix + pic).collect(Collectors.toList()));
		});
		
		return houses;
	}
	
	//获取所有社区
	public Object getAllCommunitys() {
		Community community = new Community();
		return houseMapper.selectCommunity(community);
	}

	
	public HouseUser getHouseUser(Long houseId) {
		HouseUser houseUser = houseMapper.selectSaleHouseUser(houseId);
		return houseUser;
	}
	
	//根据ID查询房屋信息 获取房屋详情
	public House queryOneHouse(Long id) {
		House query = new House();
		query.setId(id);
		List<House> houses = queryAndSetImg(query, PageParams.build(1, 1));
		if(!houses.isEmpty()) {
			return houses.get(0);
		}
		return null;	//如果为空，return null
	}

	//添加用户留言信息
	public void addUserMsg(UserMsg userMsg) {
		BeanHelper.onInsert(userMsg);
		houseMapper.insertUserMsg(userMsg);
		User agent = agencyService.getAgentDeail(userMsg.getAgentId());
		mailService.sendMail("来自用户"+userMsg.getEmail()+"的留言", userMsg.getMsg(), agent.getEmail());
	}

}