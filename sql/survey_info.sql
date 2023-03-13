

-- 问卷信息表
DROP TABLE IF EXISTS `survey_info`;
CREATE TABLE `survey_info`(
    `survey_id` int(10) UNSIGNED AUTO_INCREMENT, -- 问卷id(自增
    `created_user_id` int(10) UNSIGNED NOT NULL , -- 创建用户id
    `survey_title`    varchar(50),    -- 问卷标题 a
    `remark`    varchar(150) DEFAULT NULL, -- 备注
    `created_time`   timestamp   DEFAULT CURRENT_TIMESTAMP,  -- 创建时间

    PRIMARY KEY (`survey_id`)
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of survey_info
INSERT INTO `survey_info`(`created_user_id`, `survey_title`, `remark`) VALUES (1, '大学生午睡调查', NULL);