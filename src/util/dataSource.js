import Constants from "./const-val";
// 存储所有问卷的数据，每个问卷的数据结构如下：
// {
//    paper_id: '', // 问卷id，唯一标识符
// qs_count: 0,  // 总题目数
// title: '问卷标题',
// content: [], // 问卷内容，每个元素是一个题目，每个题目的数据结构如下：
// {
//   qs_number: 0, // 题目id，唯一标识符
//   qs_order: 0, // 题目序号
//   qs_type: '', // 题目类型，单选、多选、填空，同时也是组件名字
//   qs_title: '', // 题目标题
//   qs_option: [], // 题目选项，每个元素是一个选项，大概率是一个字符串
// }
// footer: '感谢您参与问卷调查，祝您生活愉快！',
// }
//
const PAPERS = "papers";

// 保存问卷
function savePaper(paper) {
  const lastList = getAllPapers();

  lastList.push(paper);

  localStorage.setItem(PAPERS, JSON.stringify(lastList));
}

// 获取所有问卷
function getAllPapers() {
  return JSON.parse(localStorage.getItem(PAPERS) || "[]");
}

// 获取所有未发布的问卷
function getAllUnPublicPapers() {
  const papers = getAllPapers();
  return papers.filter((paper) => paper.paperStatus.value === 0);
}

// 获取所有已发布的问卷
function getAllPublicPapers() {
  const papers = getAllPapers();
  return papers.filter((paper) => paper.paperStatus.value === 1);
}

// 获取所有已结束的问卷
function getAllEndPapers() {
  const papers = getAllPapers();
  return papers.filter((paper) => paper.paperStatus.value === 2);
}

// 通过搜索关键字获取问卷
function getPapersByKeyword(keyword) {
  const papers = getAllPapers();
  return papers.filter((paper) => paper.title.includes(keyword));
}

// 通过id获取问卷
function getPaperById(id) {
  const papers = getAllPapers();
  return papers.find((paper) => paper.paper_id === id);
}

// 通过id更新问卷
function updatePaperById(id, paper) {
  const papers = getAllPapers();

  const index = papers.findIndex((paper) => paper.paper_id === id);

  papers[index] = paper;

  localStorage.setItem(PAPERS, JSON.stringify(papers));

  return papers;
}

// 通过id删除问卷
function deletePaperById(id) {
  const papers = getAllPapers();

  const index = papers.findIndex((paper) => paper.paper_id === id);

  papers.splice(index, 1);

  localStorage.setItem(PAPERS, JSON.stringify(papers));

  return papers;
}

// 通过paperId找到该问卷所有的题目
function findQuestionsByPaperId(id) {
  const paper = getPaperById(id);

  return paper.content;
}

// 通过paperId和qsId找到对应的题目
function findQuestionByPaperIdAndQsId(paperId, qsId) {
  const questions = findQuestionsByPaperId(paperId);

  return questions.find((qs) => qs.qs_number === qsId);
}

// 通过paperId和qsId找到对应的题目，然后更新题目的内容
function updateQuestionByPaperIdAndQsId(paperId, qsId, qs) {
  const questions = findQuestionsByPaperId(paperId);

  const index = questions.findIndex((qs) => qs.qs_number === qsId);

  questions[index] = qs;

  const paper = getPaperById(paperId);

  paper.content = questions;

  updatePaperById(paperId, paper);

  return paper;
}

// 通过paperId和qsId找到对应的题目，然后删除题目
function deleteQuestionByPaperIdAndQsId(paperId, qsId) {
  const questions = findQuestionsByPaperId(paperId);

  const index = questions.findIndex((qs) => qs.qs_number === qsId);

  questions.splice(index, 1);

  const paper = getPaperById(paperId);

  paper.content = questions;

  updatePaperById(paperId, paper);

  return paper;
}

// 通过paperId和答案数组，更新问卷的答案
function saveAnswerToPaper(paperId, answers) {
  const paper = getPaperById(paperId);
  for (const answer of answers) {
    // 找到答案对应的题目
    const qs = paper.content.find((qs) => qs.qs_number === answer.qs_number);

    if (qs.qs_type === Constants.SELECT_RADIO.value) {
      // 如果题目没有提交人数，就初始化提交人数
      qs.submit_person = qs.submit_person ? qs.submit_person + 1 : 1;
      // 如果题目没有答案，就初始化答案
      if (!qs.answers)
        qs.answers = qs.qs_option.map((option) => ({
          option,
          subtotal: 0,
          percentage: 0,
        }));
      const selectedOption = qs.qs_option[+answer.qs_option];
      const i = qs.answers.findIndex(
        (answer) => answer.option === selectedOption
      );
      qs.answers[i].subtotal++;
      qs.answers[i].percentage =
        (qs.answers[i].subtotal / qs.submit_person) * 100;
      qs.answers.forEach((answer) => {
        answer.percentage = (answer.subtotal / qs.submit_person) * 100;
      });
    } else if (qs.qs_type === Constants.SELECT_MULTI.value) {
      qs.submit_person = qs.submit_person ? qs.submit_person + 1 : 1;
      if (!qs.answers)
        qs.answers = qs.qs_option.map((option) => ({
          option,
          subtotal: 0,
          percentage: 0,
        }));
      for (const option of answer.qs_option) {
        const selectedOption = qs.qs_option[+option];
        const i = qs.answers.findIndex(
          (answer) => answer.option === selectedOption
        );
        qs.answers[i].subtotal++;
        qs.answers[i].percentage =
          (qs.answers[i].subtotal / qs.submit_person) * 100;
        qs.answers.forEach((answer) => {
          answer.percentage = (answer.subtotal / qs.submit_person) * 100;
        });
      }
    } else if (qs.qs_type === Constants.SELECT_DROP.value) {
      qs.submit_person = qs.submit_person ? qs.submit_person + 1 : 1;
      if (!qs.answers)
        qs.answers = qs.qs_option.map((option) => ({
          option,
          subtotal: 0,
          percentage: 0,
        }));
      const selectedOption = qs.qs_option[+answer.qs_option];
      const i = qs.answers.findIndex(
        (answer) => answer.option === selectedOption
      );
      qs.answers[i].subtotal++;
      qs.answers[i].percentage =
        (qs.answers[i].subtotal / qs.submit_person) * 100;
      qs.answers.forEach((answer) => {
        answer.percentage = (answer.subtotal / qs.submit_person) * 100;
      });
    } else {
      qs.submit_person = qs.submit_person ? qs.submit_person + 1 : 1;
      qs.qs_option = qs.qs_option
        ? [...qs.qs_option, { option: answer.qs_option }]
        : [answer.qs_option];
    }

    // updateQuestionByPaperIdAndQsId(paperId, answer.qs_number, qs)
  }

  paper.ansPapers++; // 答卷数加一
  updatePaperById(paperId, paper);

  return paper;
}

export default {
  savePaper,
  getAllPapers,
  getAllUnPublicPapers,
  getAllPublicPapers,
  getAllEndPapers,
  getPapersByKeyword,
  getPaperById,
  updatePaperById,
  deletePaperById,
  findQuestionsByPaperId,
  findQuestionByPaperIdAndQsId,
  updateQuestionByPaperIdAndQsId,
  deleteQuestionByPaperIdAndQsId,
  saveAnswerToPaper,
};
