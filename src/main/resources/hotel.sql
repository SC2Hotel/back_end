SET MODE MYSQL;

DROP TABLE IF EXISTS `Coupon`;
CREATE TABLE `Coupon` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `description` varchar(255) DEFAULT NULL,
                          `hotelId` int(11) DEFAULT '-1',
                          `couponType` int(11) NOT NULL,
                          `couponName` varchar(255) NOT NULL,
                          `target_money` int(11) DEFAULT NULL,
                          `discount` double DEFAULT NULL,
                          `status` int(11) DEFAULT NULL,
                          `start_time` datetime DEFAULT NULL,
                          `end_time` datetime DEFAULT NULL,
                          `discount_money` int(11) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `Coupon` VALUES (2,'满500-100优惠',2,3,'满减优惠券',500,0,1,NULL,NULL,100);
INSERT INTO `Coupon` VALUES (3,'满500-100优惠',3,3,'满减优惠券',500,0,1,NULL,NULL,100);


DROP TABLE IF EXISTS `Hotel`;
CREATE TABLE `Hotel` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `hotelName` varchar(255) NOT NULL,
                         `hotelDescription` varchar(255) DEFAULT NULL,
                         `address` varchar(255) DEFAULT NULL,
                         `bizRegion` varchar(255) DEFAULT NULL,
                         `hotelStar` varchar(255) DEFAULT NULL,
                         `phoneNum` varchar(31) DEFAULT NULL,
                         `rate` double DEFAULT NULL,
                         `manager_id` int(11) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `Hotel` VALUES (1,'汉庭酒店','欢迎您入住',NULL,'XiDan','Four',1829373819,4.8,1),(2,'儒家酒店','欢迎您入住','南京市鼓楼区珠江路268号','XiDan','Four',1829373819,4.8,2),(3,'桂圆酒店','欢迎您入住','南京市栖霞区珠江路268号','XiDan','Four',1829553719,4.8,6),(4,'南大会议国际中心','欢迎您入住','南京市栖霞区仙林大道163号','XiDan','Four',1829663719,4.8,3);


DROP TABLE IF EXISTS `OrderList`;
CREATE TABLE `OrderList` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `userId` int(11) DEFAULT NULL,
                             `hotelId` int(11) DEFAULT NULL,
                             `hotelName` varchar(255) DEFAULT NULL,
                             `checkInDate` varchar(255) DEFAULT NULL,
                             `checkOutDate` varchar(255) DEFAULT NULL,
                             `roomType` varchar(255) DEFAULT NULL,
                             `roomNum` int(255) DEFAULT NULL,
                             `peopleNum` int(255) DEFAULT NULL,
                             `haveChild` tinytext,
                             `createDate` varchar(255) DEFAULT NULL,
                             `price` decimal(65,0) DEFAULT NULL,
                             `clientName` varchar(255) DEFAULT NULL,
                             `phoneNumber` varchar(255) DEFAULT NULL,
                             `orderState` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

INSERT INTO `OrderList` VALUES (1,4,3,'桂圆酒店','2020-06-03','2020-06-04','Family',1,1,0,'2020-05-24',399.0,'qin','15521232123','已执行'),(2,4,3,'桂圆酒店','2020-06-05','2020-06-06','Family',1,1,0,'2020-05-24',399.0,'qin','15521232123','已撤销'),(3,4,3,'桂圆酒店','2020-06-07','2020-06-08','Family',1,1,0,'2020-05-24',399.0,'qin','15521232123','超过最迟延时入住期限'),(4,4,3,'桂圆酒店','2020-06-08','2020-06-09','Family',1,1,0,'2020-05-24',399.0,'qin','15521232123','已评价'),(5,4,3,'桂圆酒店','2020-06-15','2020-06-25','Family',1,1,0,'2020-05-24',399.0,'qin','15521232123','已执行'),(6,4,3,'桂圆酒店','2020-07-02','2020-07-10','Family',1,1,0,'2020-05-24',399.0,'qin','15521232123','已预订');
INSERT INTO `OrderList` values (7,4,3,'桂圆酒店','2020-06-27','2020-07-01','Family',1,1,0,'2020-05-24',399.0,'qin','15521232123','异常');

DROP TABLE IF EXISTS `Room`;

CREATE TABLE `Room` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `price` double DEFAULT NULL,
                        `curNum` int(11) DEFAULT NULL,
                        `total` int(11) DEFAULT NULL,
                        `hotel_id` int(11) DEFAULT NULL,
                        `roomType` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `Room` VALUES (2,199,20,20,1,'BigBed'),(3,299,30,30,1,'DoubleBed'),(4,399,10,10,1,'Family'),(6,399,10,10,2,'Family'),(7,399,8,10,3,'Family');


DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(24) NOT NULL,
                        `username` varchar(255) DEFAULT NULL,
                        `phonenumber` varchar(255) DEFAULT NULL,
                        `credit` double DEFAULT NULL,
                        `usertype` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

INSERT INTO `User` VALUES (4,'1012681@qq.com','4QrcOUm6Wau+VuBX8g+IPg==','测试一号','12345678919',-99.5,'commonSeniorClient'),(5,'123@qq.com','4QrcOUm6Wau+VuBX8g+IPg==','测试二号','12345678911',100,'Client'),(6,'333@qq.com','4QrcOUm6Wau+VuBX8g+IPg==',NULL,NULL,NULL,'HotelManager'),(7,'admin@qq.com','4QrcOUm6Wau+VuBX8g+IPg==','admin',NULL,NULL,'Admin');

DROP TABLE IF EXISTS `Vip`;
CREATE TABLE `Vip` (
                       user_id int primary key,
                       type int COMMENT '类型 1 表示普通会员 2 表示企业会员',
                       message varchar(30) COMMENT '会员登记信息 普通会员登记生日 yyyy-MM-dd 企业会员登记企业名'
);
INSERT INTO Vip VALUES (4,1,'2000-01-01');

DROP TABLE IF EXISTS `Comment`;
CREATE TABLE `Comment`(
                          order_id int primary key,
                          score double,
                          content varchar(255)
)CHARSET=utf8;
INSERT INTO Comment VALUES (4,4.5,'挺好的');

drop table if exists `CreditChange`;
create table `CreditChange`
(
    id        int primary key,
    userId    int       default null,
    reason    varchar(255) NOT NULL,
    changeNum double    DEFAULT NULL,
    orderId   int       default null,
    credit    double    default null,
    time      datetime default null
)CHARSET=utf8;;

insert into `CreditChange` values
(1, 4, '初始化', 100, null, 100, '2020-05-30 09:07:03' ),
(2, 4, '执行订单', 399.0, 1, 499, '2020-06-03 12:00:00'),
(3, 4, '撤销订单', -199.5, 2, 299.5, '2020-06-05 15:13:00'),
(4, 4, '未按时入住', -399.0, 3, -99.5, '2020-06-07 22:00:00'),
(5, 4, '执行订单', 399.0, 4, 299.5, '2020-06-08 11:30:00'),
(6, 4, '未按时入住', -399.0, 5, -99.5, '2020-06-12 22:00:00'),
(7, 4, '延时入住',399.0,5, 299.5, '2020-06-15 13:00:00' ),
(8, 4, '未按时入住', -399.0, 7, -99.5, '2020-06-27 22:00:00' );
