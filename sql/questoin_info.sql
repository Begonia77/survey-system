

-- 题目信息表
DROP TABLE IF EXISTS `question_info`;
CREATE TABLE `question_info`(
    `survey_id`   int(10) UNSIGNED NOT NULL, -- 所属问卷id
    `question_id` int(10) UNSIGNED AUTO_INCREMENT, -- 问题id(自增
    `question`    text NOT NULL, -- 题面描述
    `type`        int NOT NULL, -- 题目类型 0为判断 1为单选 2为多选 3为简答填空
    `remark`      varchar(150)  NULL, -- 备注

    PRIMARY KEY (`question_id`)
)ENGINE = InnoDB DEFAULT  CHARSET = utf8;

-- Record of question_info
INSERT INTO `question_info`(`survey_id`, `question_id`, `question`, `type`, `remark`) VALUES ( 1, NULL, '你是否睡午觉？', 0, NULL);
