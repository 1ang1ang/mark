create  database if not exists mark;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
CREATE TABLE `user` (
  `uid` varchar(40) NOT NULL,
  `phone_num` varchar(20) DEFAULT '""',
  `email` varchar(40) DEFAULT '""',
  `last_login_type` int(11) DEFAULT '0',
  `password` varchar(40) NOT NULL,
  `gender` int(4) DEFAULT '1',
  `name` varchar(20) DEFAULT '""',
  `age` int(11) DEFAULT '0',
  `identity` int(11) NOT NULL DEFAULT '1',
  `register_time` bigint(20) NOT NULL DEFAULT '0',
  `last_login_time` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `phone_unique` (`phone_num`) USING BTREE,
  UNIQUE KEY `email_unique` (`email`) USING BTREE,
  UNIQUE KEY `name_unique` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Records of user
-- ----------------------------
