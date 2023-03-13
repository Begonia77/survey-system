

-- 题目选项表
DROP TABLE  IF EXISTS `option_info`;
CREATE TABLE `option_info`(
    `option_id` int(10) UNSIGNED AUTO_INCREMENT, -- 选项id(自增
    `question_id` int(10) NOT NULL, -- 所属问题id
    `content` text NOT NULL, -- 选项内容/填写内容

    PRIMARY KEY (`option_id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- Records of option_info
INSERT INTO `option_info`(`question_id`, `content`) VALUES (1, '是');