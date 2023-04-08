CREATE DATABASE IF NOT EXISTS `surveydb`;
USE surveydb;

-- ---------------------------------------------------------------------------------------------------------------------

-- 用户信息表
DROP TABLE IF EXISTS `user_info`;
create table user_info
(
    user_id      bigint auto_increment
        primary key,
    name         varchar(255)                        not null,
    password     varchar(255)                        not null,
    created_time timestamp default CURRENT_TIMESTAMP null,
    constraint name
        unique (name)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- Records of user_info
INSERT INTO `user_info`(`name`, `password`) VALUES ('sa', '123456');

-- ---------------------------------------------------------------------------------------------------------------------

-- 问卷信息表
DROP TABLE IF EXISTS `survey_info`;
create table survey_info
(
    survey_id       bigint auto_increment
        primary key,
    created_user_id bigint                              not null,
    survey_title    varchar(255)                        null,
    remark          varchar(255)                        null,
    created_time    timestamp default CURRENT_TIMESTAMP null,
    state           int       default 0                 null,
    analysis        text                                null
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of survey_info
INSERT INTO `survey_info`(`created_user_id`, `survey_title`, `state`, `remark`) VALUES (1, '大学生午睡调查', 0, NULL);
INSERT INTO `survey_info`(`created_user_id`, `survey_title`, `state`, `remark`) VALUES (1, '大学生早餐调查', 0, NULL);
-- ---------------------------------------------------------------------------------------------------------------------

-- 题目信息表
DROP TABLE IF EXISTS question_info;
create table question_info
(
    survey_id      bigint       not null,
    question_id    bigint auto_increment
        primary key,
    question       text         not null,
    question_order int          not null,
    type           int          not null,
    remark         varchar(255) null
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of question_info
INSERT INTO `question_info`(`survey_id`, `question`, `question_order`, `type`, `remark`) VALUES ( 1, '你是否睡午觉？', 1, 0, NULL);
INSERT INTO `question_info`(`survey_id`, `question`, `question_order`, `type`, `remark`) VALUES ( 1, '午睡一般多少时常？', 2, 3, NULL);
INSERT INTO `question_info`(`survey_id`, `question`, `question_order`, `type`, `remark`) VALUES ( 2, '你是否吃早餐？', 1, 0, NULL);
INSERT INTO `question_info`(`survey_id`, `question`, `question_order`, `type`, `remark`) VALUES ( 2, '你在早餐上花费多少钱？', 2, 3, NULL);
INSERT INTO `question_info`(`survey_id`, `question`, `question_order`, `type`, `remark`) VALUES ( 3, '你一天睡眠有几个小时？', 1, 3, NULL);
-- ---------------------------------------------------------------------------------------------------------------------

-- 题目选项表
DROP TABLE  IF EXISTS `option_info`;
create table option_info
(
    question_id bigint not null,
    option_id   bigint auto_increment
        primary key,
    content     text   not null
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- Records of option_info
INSERT INTO `option_info`(`question_id`, `content`) VALUES (1, '是');
INSERT INTO `option_info`(`question_id`, `content`) VALUES (1, '否');
INSERT INTO `option_info`(`question_id`, `content`) VALUES (2, '10');
INSERT INTO `option_info`(`question_id`, `content`) VALUES (2, '9');

-- ---------------------------------------------------------------------------------------------------------------------

-- 填写记录表
DROP TABLE IF EXISTS `fill_in`;
create table fill_in
(
    fill_id      bigint auto_increment
        primary key,
    survey_id    bigint                              not null,
    question_id  bigint                              not null,
    option_id    bigint                              not null,
    user_id      bigint                              null,
    fill_in_time timestamp default CURRENT_TIMESTAMP null
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of fill_in
INSERT INTO `fill_in`(`survey_id`, `question_id`, `option_id`, `user_id`) VALUES (1, 1, 1, 1);
INSERT INTO `fill_in`(`survey_id`, `question_id`, `option_id`, `user_id`) VALUES (1, 2, 3, 1);
INSERT INTO `fill_in`(`survey_id`, `question_id`, `option_id`, `user_id`) VALUES (1, 1, 2, 2);
INSERT INTO `fill_in`(`survey_id`, `question_id`, `option_id`, `user_id`) VALUES (1, 2, 4, 2);
-- ---------------------------------------------------------------------------------------------------------------------
