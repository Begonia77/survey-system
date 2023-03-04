/*选择题 1xx*/
const SELECT_RADIO = { desc: "单选题", value: "11" };
const SELECT_MULTI = { desc: "多选题", value: "12" };
const SELECT_DROP = { desc: "下拉题", value: "13" };

/*填空题 2xx*/
const FILL_SINGLE = { desc: "单行填空", value: "21" };
const FILL_MULTI = { desc: "多行填空", value: "22" };
const FILL_DATE = { desc: "日期", value: "23" };

/*快捷题目 3xx*/
const SHORTCUT_NAME = { desc: "姓名", value: "31" };
const SHORTCUT_GENDER = { desc: "性别", value: "32" };
const SHORTCUT_PHONE = { desc: "手机号", value: "33" };

/*问卷状态*/
const UN_PUBLIC = { desc: "未发布", value: 0 };
const IS_PUBLIC = { desc: "已发布", value: 1 };
const IS_STOP = { desc: "已结束", value: 2 };

export default {
  SELECT_RADIO,
  SELECT_MULTI,
  SELECT_DROP,
  FILL_SINGLE,
  FILL_MULTI,
  FILL_DATE,
  SHORTCUT_NAME,
  SHORTCUT_GENDER,
  SHORTCUT_PHONE,
  UN_PUBLIC,
  IS_PUBLIC,
  IS_STOP,
};
