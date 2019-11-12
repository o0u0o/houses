package com.aiuiot.house.biz.mapper;

import com.aiuiot.house.common.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {

    /** 查询评论 */
    List<Comment> selectComments(@Param("houseId") long houseId, @Param("size") int size);
}
