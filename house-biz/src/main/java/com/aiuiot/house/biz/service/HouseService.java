package com.aiuiot.house.biz.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.aiuiot.house.common.constants.HouseUserType;
import com.google.common.base.Joiner;
import org.apache.commons.collections.CollectionUtils;
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

	@Autowired
	private FileService fileService;
	
	/**
	 * 查询house
	 * 1.查询小区
	 * 2.添加图片服务器的地址前缀
	 * 3.构建分页结果
	 * @param query
	 * @param pageParams
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
	

	/**
	 * 获取所有社区(小区)
	 * @return
	 */
	public Object getAllCommunitys() {
		//获取所有的 community 为空
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

	/**
	 * 添加房产
	 * step1：添加房产图片
	 * step2：添加户型图片
	 * step3：插入房产信息
	 * step4：绑定用户和房产的关系
	 * @param house
	 * @param user
	 */
	public void addHouse(House house, User user) {
		//判断用户有没有上传房屋图片
		if (CollectionUtils.isNotEmpty(house.getHouseFiles())){
			String images = Joiner.on(",").join(fileService.getImgPaths(house.getHouseFiles()));
			house.setImages(images);
		}
		//处理户型图片
		if (CollectionUtils.isNotEmpty(house.getFloorPlanFiles())){
			String images = Joiner.on(",").join(fileService.getImgPaths(house.getFloorPlanFiles()));
			house.setFloorPlan(images);
		}
		BeanHelper.onInsert(house);
		houseMapper.insert(house);
		bindUser2House(house.getId(), user.getId(), false);
	}

	/**
	 * 绑定用户和房产的关系
	 * @param houseId 房产ID
	 * @param userId 用户ID
	 * @param isCollect 是否收藏
	 */
	private void bindUser2House(Long houseId, Long userId, boolean isCollect) {
		HouseUser existHouseUser = houseMapper.selectHouseUser(userId, houseId, isCollect? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);
		if (existHouseUser != null){
			return;
		}
		HouseUser houseUser = new HouseUser();
		houseUser.setHouseId(houseId);
		houseUser.setUserId(userId);
		houseUser.setType(isCollect ? HouseUserType.BOOKMARK.value : HouseUserType.SALE.value);
		BeanHelper.setDefaultProp(houseUser, HouseUser.class);
		BeanHelper.onInsert(houseUser);
		houseMapper.insertHouseUser(houseUser);
	}
}