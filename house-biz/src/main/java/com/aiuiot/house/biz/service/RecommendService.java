package com.aiuiot.house.biz.service;

import com.aiuiot.house.common.model.House;
import com.aiuiot.house.common.page.PageParams;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 热门房产
 * @Author aiuiot
 * @Date 2019/10/3 8:14 上午
 **/
@Service
public class RecommendService {

    private static final String HOT_HOUSE_KEY = "hot_house";

    private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Autowired
    private HouseService houseService;

    /**
     * 获取最后添加的房源
     * @return
     */
    public List<House> getLastest() {
        House query = new House();
        query.setSort("create_time");
        List<House> houses = houseService.queryAndSetImg(query, new PageParams(8, 1));
        return houses;
    }

    /**
     * 获取热门房产
     * @param size
     * @return
     */
    public List<House> getHotHouse(Integer size){
        House query = new House();
        List<Long> list = getHot();
        list = list.subList(0, Math.min(list.size(), size));
        if (list.isEmpty()){
            return Lists.newArrayList();
        }
        query.setIds(list);
        final List<Long> order = list;
        List<House> houses = houseService.queryAndSetImg(query, PageParams.build(size, 1));
        Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
           return order.indexOf(hs.getId());
        });
        return houseSort.sortedCopy(houses);
    }

    /**
     * 获取热门
     * @return
     */
    public List<Long> getHot(){
        try {
            Jedis jedis = new Jedis("127.0.0.1");
            Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
            jedis.close();
            List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
            return ids;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return Lists.newArrayList();
        }
    }



}
