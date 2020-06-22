package com.example.hotel.bl.order;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qin
 * @date 2020-06-19
 */

public interface CommentService {

    ResponseVO addComment(CommentVO commentVO);

    /**
     * 返回某个订单的评论
     * @param orderId
     * @return
     */
    Comment getComment(Integer orderId);

    /**
     * 返回某个酒店的所有评论
     * @param hotelId
     * @return
     */
    List<Comment> getAllHotelComment(Integer hotelId);
}
