package com.aiuiot.house.biz.mapper;

import com.aiuiot.house.common.model.Comment;
import com.aiuiot.house.common.model.House;
import com.aiuiot.house.common.model.UserMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface CommentMapper {

    int insertUserMsg(UserMsg userMsg);

    int updateHouse(House house);

    int insert(Comment comment);

    /** 查询评论 */
    List<Comment> selectComments(@Param("houseId") long houseId, @Param("size") int size);
}
