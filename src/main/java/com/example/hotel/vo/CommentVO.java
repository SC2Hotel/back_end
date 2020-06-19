package com.example.hotel.vo;

import lombok.Data;

/**
 * @author qin
 * @date 2020-06-19
 */
@Data
public class CommentVO {
    Integer orderId;
    Double score;
    String content;
}
