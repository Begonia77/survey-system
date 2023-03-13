

-- 填写信息表
DROP TABLE IF EXISTS `fill_in`;
CREATE TABLE `fill_in`(
    `survey_id` int(10) UNSIGNED NOT NULL , -- 问卷id
    `question_id` int(10) UNSIGNED NOT NULL , -- 问题id
    `option_id` int(10) UNSIGNED NOT NULL , -- 选项id
    `user_id` int(10) UNSIGNED DEFAULT NULL, -- 填写者id
    `fill_in_time`  timestamp DEFAULT  CURRENT_TIMESTAMP -- 填写时间

    -- PRIMARY KEY (`survey_id`, `question_id`, `option_id`)
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of fill_in