package com.example.hotel.bl.order;

import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.stereotype.Service;

/**
 * @author qin
 * @date 2020-06-19
 */

public interface CommentService {

    ResponseVO addComment(CommentVO commentVO);

}
