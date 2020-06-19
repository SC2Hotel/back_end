package com.example.hotel.po;

import lombok.Data;

/**
 * @author qin
 * @date 2020-06-19
 */
@Data
public class Comment {
    Integer orderId;
    Double score;
    String content;
}
