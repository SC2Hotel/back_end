package com.example.hotel.blImpl.order;

import com.example.hotel.bl.order.CommentService;
import com.example.hotel.data.order.CommentMapper;
import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qin
 * @date 2020-06-19
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public ResponseVO addComment(CommentVO commentVO) {
        if(commentVO.getScore()<0){
            return ResponseVO.buildFailure("评分不能低于0");
        }
        if(null==commentMapper.selectByOrderId(commentVO.getOrderId())){
            Comment comment = new Comment();
            BeanUtils.copyProperties(commentVO,comment);
            commentMapper.insertComment(comment);
        }else {
            return ResponseVO.buildFailure("已评价");
        }
        return ResponseVO.buildSuccess();
    }
}
