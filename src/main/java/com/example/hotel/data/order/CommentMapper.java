package com.example.hotel.data.order;

import com.example.hotel.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qin
 * @date 2020-06-19
 */
@Mapper
@Repository
public interface CommentMapper {

    Integer insertComment(Comment comment);

    Comment selectByOrderId(Integer orderId);

    List<Comment> selectAllHotelComment(Integer hotelId);

}

