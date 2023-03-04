const MODELS = "model";

// 保存模板
function saveModel(model) {
  const lastList = getAllModels();

  lastList.push(model);

  localStorage.setItem(MODELS, JSON.stringify(lastList));
}

// 获取所有模板
function getAllModels() {
  return JSON.parse(localStorage.getItem(MODELS) || "[]");
}

// 通过id获取模板
function getModelById(id) {
  const models = getAllModels();
  return models.find((model) => model.id === id);
}

// 通过搜索关键字获取模板
function getModelsByKeyword(keyword) {
  const models = getAllModels();
  return models.filter((model) => model.title.includes(keyword));
}

// 通过id找到该模板的所有问题
function getQuestionsById(id) {
  const model = getModelById(id);
  return model.content;
}

// 通过modelId和questionId找到该问题
function getQuestionById(modelId, questionId) {
  const questions = getQuestionsById(modelId);
  return questions.find((qs) => qs.qs_number === questionId);
}

export default {
  saveModel,
  getAllModels,
  getModelById,
  getModelsByKeyword,
  getQuestionsById,
  getQuestionById,
};
