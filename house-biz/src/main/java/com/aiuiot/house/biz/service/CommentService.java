package com.aiuiot.house.biz.service;

import com.aiuiot.house.biz.mapper.CommentMapper;
import com.aiuiot.house.common.model.Comment;
import com.aiuiot.house.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 评论
 * @Author aiuiot
 * @Date 2019/10/3 9:50 上午
 **/
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;


    @Autowired
    private UserService userService;


    /** 获取评论 */
    public List<Comment> getHouseComments(Long houseId, int size){
        List<Comment> comments = commentMapper.selectComments(houseId, size);
        comments.forEach(comment -> {
            User user = userService.getUserById(comment.getUserId());
            comment.setAvatar(user.getAvatar());
            comment.setUserName(user.getName());
        });
        return comments;
    }
}
