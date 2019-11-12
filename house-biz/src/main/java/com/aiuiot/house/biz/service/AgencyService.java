package com.aiuiot.house.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aiuiot.house.biz.mapper.AgencyMapper;
import com.aiuiot.house.common.model.User;
import com.aiuiot.house.common.page.PageData;
import com.aiuiot.house.common.page.PageParams;

/**
 * 
 * @Title:  AgencyService.java   
 * @Package com.aiuiot.house.biz.service   
 * @Description:    AgencyService   
 * @author: ZerOneth 
 * @date:   2019年6月24日 上午8:17:16   
 * @version V1.0
 */
@Service
public class AgencyService {

	@Autowired
	private AgencyMapper agencyMapper;
	
	@Value("${file.prefix}")
	private String imgPrefix;
	
	/**
	 * 访问user表，获取详情
	 * 添加用户头像
	 * @param userId
	 * @return
	 */
	public User getAgentDeail(Long userId) {
		User user = new User();
		user.setId(userId);	
		user.setType(2);	//2-表示经纪人
		List<User> list = agencyMapper.selectAgent(user, PageParams.build(1, 1));
		//添加用户头像
		setImg(list);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	//设置图片
	private void setImg(List<User> list) {
		list.forEach(i ->{
			i.setAvatar(imgPrefix + i.getAvatar());
		});
	}

	/**
	 * 获取所有经纪人
	 * @param pageParams
	 * @return
	 */
	public PageData<User> getAllAgent(PageParams pageParams) {
		List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
		setImg(agents);
		//获取总数
		Long count = agencyMapper.selectAgentCount(new User());
		return PageData.buildPage(agents, count, pageParams.getPageSize(), pageParams.getPageNum());
	}

}
