package com.aiuiot.house.common.constants;

/**
 * @Author aiuiot
 * @Date 2020/1/5 8:14 下午
 * @Descripton: 类型
 **/
public enum  HouseUserType {

    SALE(1),
    BOOKMARK(2),   //收藏
    ;

    public final Integer value;

    private HouseUserType(Integer value){
        this.value = value;
    }
}
