USE surveydb;

-- 用户信息表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user_info`(
    `user_id`  int(10)     UNSIGNED  AUTO_INCREMENT,  -- 用户id(自增
    `name`     varchar(10) NOT NULL,    -- 用户名
    `password` varchar(20) NOT NULL,    -- 用户密码

    PRIMARY KEY (`user_id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- Records of user_info
INSERT INTO `user_info`(`name`, `password`) VALUES ('sa', '123456');