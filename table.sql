SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
                        `itemId` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一商品id',
                        `itemType` varchar(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品种类',
                        `itemDes` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品描述',
                        `itemNums` int DEFAULT '100' COMMENT '商品上架数量',
                        `itemImg` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品图片',
                        `itemprice` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
                        `deleted` int DEFAULT '0',
                        `version` int DEFAULT '0',
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`itemId`)
) ENGINE=InnoDB AUTO_INCREMENT=1534725929174130701 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
                          `recordId` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一记录id',
                          `itemId` bigint DEFAULT NULL COMMENT '商品唯一id',
                          `userId` bigint DEFAULT NULL COMMENT '用户唯一id',
                          `recordNums` int DEFAULT '1' COMMENT '商品成交数量',
                          `recordTime` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易时间',
                          `recordMoney` decimal(10,2) DEFAULT NULL COMMENT '总成交额',
                          `itemDes` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品描述',
                          `itemPrice` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
                          `itemImg` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品图片',
                          `deleted` int DEFAULT '0',
                          `version` int DEFAULT '0',
                          `create_time` datetime DEFAULT NULL,
                          `update_time` datetime DEFAULT NULL,
                          PRIMARY KEY (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `userId` bigint NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
                        `userGrade` int DEFAULT '0' COMMENT '用户等级',
                        `userBranch` int DEFAULT '0' COMMENT '用户积分',
                        `userName` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名称',
                        `userPwd` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密码',
                        `userSex` int DEFAULT NULL COMMENT '用户性别',
                        `userAge` int DEFAULT '18' COMMENT '用户年龄',
                        `userWork` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户工作',
                        `userPhone` varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户电话',
                        `userAddress` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户收货地点',
                        `userMoney` decimal(10,2) DEFAULT '10000000.00' COMMENT '用户余额',
                        `userSpend` decimal(10,2) DEFAULT '0.00' COMMENT '用户消费额',
                        `perm` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'user:primary' COMMENT '用户权限',
                        `userCount` int DEFAULT '0' COMMENT '订单总数',
                        `deleted` int DEFAULT '0',
                        `version` int DEFAULT '0',
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
