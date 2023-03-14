-- operations
USE surveydb;

-- 查询用户创建问卷个数
SELECT COUNT(*) FROM `survey_info` WHERE `created_user_id` = '';

-- 查询用户创建的所有问卷
SELECT * FROM `survey_info` WHERE  `created_user_id` = '1';

-- 查询问卷的所有问题
SELECT * FROM `question_info` WHERE  `survey_id` = '1';

-- 查询问题的所有选项
SELECT * FROM `option_info` WHERE `question_id` = '1';

-- 查询问卷的所有问题+选项
SELECT qu.question_id, qu.question, op.content
    FROM `question_info` AS qu, `option_info` AS op
    WHERE qu.`survey_id` = '1'
      AND qu.`question_id` = op.`question_id`;

-- 查询某个问卷的总填写次数
SELECT COUNT(*) FROM `fill_in` WHERE `survey_id` = '';

-- 查询某个问题的总填写次数
SELECT COUNT(*) FROM `fill_in` WHERE `question_id` = '';

-- 查询某个选项的填写次数
SELECT COUNT(*) FROM `fill_in` WHERE `option_id` = '';

-- 查询某个问卷的填写情况
SELECT qu.question_id, qu.question, op.content, COUNT(fi.`option_id`) SUM
    FROM `question_info` AS qu, `option_info` AS op, `fill_in` AS fi
    WHERE qu.`survey_id` = '1'
        AND qu.`question_id` = op.`question_id`
        AND op.`option_id` = fi.`option_id`
    GROUP BY fi.`option_id`;


