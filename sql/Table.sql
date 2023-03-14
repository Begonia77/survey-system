CREATE DATABASE IF NOT EXISTS `surveydb`;
USE surveydb;


-- ---------------------------------------------------------------------------------------------------------------------

-- 用户信息表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user_info`(
    `user_id`  int     UNSIGNED  AUTO_INCREMENT,  -- 用户id(自增
    `name`     varchar(255) UNIQUE NOT NULL,    -- 用户名
    `password` varchaR(255) NOT NULL,    -- 用户密码
    `created_time` timestamp DEFAULT CURRENT_TIMESTAMP, -- 创建时间

    PRIMARY KEY (`user_id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- Records of user_info
INSERT INTO `user_info`(`name`, `password`) VALUES ('sa', '123456');

-- ---------------------------------------------------------------------------------------------------------------------

-- 问卷信息表
DROP TABLE IF EXISTS `survey_info`;
CREATE TABLE `survey_info`(
    `survey_id` int UNSIGNED AUTO_INCREMENT, -- 问卷id(自增
    `created_user_id` int UNSIGNED NOT NULL , -- 创建用户id
    `survey_title`    varchar(255),    -- 问卷标题 a
    `remark`    varchar(255) DEFAULT NULL, -- 备注
    `created_time`   timestamp   DEFAULT CURRENT_TIMESTAMP,  -- 创建时间

    PRIMARY KEY (`survey_id`)
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of survey_info
INSERT INTO `survey_info`(`created_user_id`, `survey_title`, `remark`) VALUES (1, '大学生午睡调查', NULL);

-- ---------------------------------------------------------------------------------------------------------------------

-- 题目信息表
DROP TABLE IF EXISTS `question_info`;
CREATE TABLE `question_info`(
    `survey_id`   int UNSIGNED NOT NULL, -- 所属问卷id
    `question_id` int UNSIGNED AUTO_INCREMENT, -- 问题id(自增
    `question`    text NOT NULL, -- 题面描述
    `type`        int NOT NULL, -- 题目类型 0为判断 1为单选 2为多选 3为简答填空
    `remark`      varchar(255)  NULL, -- 备注

    PRIMARY KEY (`question_id`)
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of question_info
INSERT INTO `question_info`(`survey_id`, `question`, `type`, `remark`) VALUES ( 1, '你是否睡午觉？', 0, NULL);

-- ---------------------------------------------------------------------------------------------------------------------

-- 题目选项表
DROP TABLE  IF EXISTS `option_info`;
CREATE TABLE `option_info`(
    `question_id` int NOT NULL, -- 所属问题id
    `option_id` int UNSIGNED AUTO_INCREMENT, -- 选项id(自增
    `content` text NOT NULL, -- 选项内容/填写内容

    PRIMARY KEY (`option_id`)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- Records of option_info
INSERT INTO `option_info`(`question_id`, `content`) VALUES (1, '是');
INSERT INTO `option_info`(`question_id`, `content`) VALUES (1, '否');

-- ---------------------------------------------------------------------------------------------------------------------

-- 填写信息表
DROP TABLE IF EXISTS `fill_in`;
CREATE TABLE `fill_in`(
    `fill_id` int   UNSIGNED AUTO_INCREMENT, -- 填写id
    `survey_id` int(10) UNSIGNED NOT NULL , -- 问卷id
    `question_id` int(10) UNSIGNED NOT NULL , -- 问题id
    `option_id` int(10) UNSIGNED NOT NULL , -- 选项id
    `user_id` int(10) UNSIGNED DEFAULT NULL, -- 填写者id
    `fill_in_time`  timestamp DEFAULT  CURRENT_TIMESTAMP, -- 填写时间

    PRIMARY KEY (`fill_id`)
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of fill_in
INSERT INTO `fill_in`(`survey_id`, `question_id`, `option_id`, `user_id`) VALUES (1, 1, 1, 1);
INSERT INTO `fill_in`(`survey_id`, `question_id`, `option_id`, `user_id`) VALUES (1, 1, 2, 1);

-- ---------------------------------------------------------------------------------------------------------------------
