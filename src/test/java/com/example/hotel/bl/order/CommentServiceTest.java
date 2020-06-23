package com.example.hotel.bl.order;

import com.example.hotel.po.Comment;
import com.example.hotel.vo.CommentVO;
import com.example.hotel.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author qin
 * @date 2020-06-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Test
    public void addComment1() {
        CommentVO commentVO = new CommentVO();
        commentVO.setOrderId(4);
        commentVO.setScore(5.0);
        commentVO.setContent("good");
        ResponseVO responseVO = commentService.addComment(commentVO);
        assertFalse(responseVO.getSuccess());
    }


    @Test
    public void getOrderComment(){
        Comment comment = commentService.getComment(4);
        Assert.assertTrue(4.5==comment.getScore());
        Assert.assertEquals("挺好的", comment.getContent());
    }

    @Test
    public void getHotelComment(){
        List<Comment> commentList = commentService.getAllHotelComment(3);
        Assert.assertTrue(commentList.size()==1);
        Assert.assertEquals("挺好的", commentList.get(0).getContent());
    }

}