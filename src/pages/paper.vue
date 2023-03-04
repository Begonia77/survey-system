<template>
  <div>
    <el-row :gutter="20">
      <!--左侧导航区域-->
      <el-col :span="6" class="left-all">
        <div class="left-all-div">
          <el-button
            @click="$router.push({ name: 'paper-edit' })"
            type="primary"
            style="width: 250px; height: 50px"
          >
            <span class="left-word">
              <i class="el-icon-plus"></i>&nbsp;&nbsp;创建问卷
            </span>
          </el-button>
          <div class="left-nav">
            <ul>
              <li>
                <a @click="searchPaperByStatus(0)"
                  ><i class="myi el-icon-tickets"></i>&nbsp;全部问卷</a
                >
              </li>
              <li>
                <a @click="searchPaperByStatus(1)"
                  ><i class="myi el-icon-document-checked"></i>&nbsp;已发布</a
                >
              </li>
              <li>
                <a @click="searchPaperByStatus(2)"
                  ><i class="myi el-icon-edit-outline"></i>&nbsp;未发布</a
                >
              </li>
              <li>
                <a @click="searchPaperByStatus(3)"
                  ><i class="myi el-icon-delete"></i>&nbsp;已结束</a
                >
              </li>
            </ul>
          </div>
        </div>
      </el-col>

      <!--右侧问卷列表区域-->
      <el-col :span="17" class="main-all">
        <div class="main-top">
          <h2>问卷列表</h2>
          <el-input
            style="width: 300px"
            placeholder="搜索问卷"
            v-model="search_text"
            @keyup.enter.native="searchPaperByTitle()"
          >
            <i
              slot="prefix"
              @click="searchPaperByTitle()"
              class="el-input__icon el-icon-search"
            ></i>
            <i
              v-if="search_text !== ''"
              slot="suffix"
              @click="searchReset()"
              class="el-input__icon el-icon-circle-close"
            ></i>
          </el-input>
        </div>
        <div class="main-content">
          <div v-if="papers.length === 0" align="center">
            <el-empty description="您的问卷空空如也！"></el-empty>
          </div>
          <el-card
            v-for="(paper, index) in paperList"
            :key="index"
            shadow="hover"
            class="box-card"
            :body-style="{ padding: '12px' }"
          >
            <div class="card-top">
              <el-tooltip content="预览问卷" placement="left" effect="light">
                <span style="font-weight: 500; font-size: 17px; cursor: pointer"
                  ><a @click="prePaper(paper.paper_id)">{{
                    paper.title
                  }}</a></span
                >
              </el-tooltip>
              <span style="font-size: small">
                <span>问卷ID:{{ paper.paper_id }}</span>
                <el-divider direction="vertical"></el-divider>
                <span>答卷: {{ paper.ansPapers }}</span>
                <el-divider direction="vertical"></el-divider>
                <span>创建时间：{{ paper.createTime }}</span>
                <el-divider direction="vertical"></el-divider>

                <el-tag
                  v-if="QS_TYPE.IS_PUBLIC.value === paper.paperStatus.value"
                  type="success"
                  size="mini"
                  >已发布</el-tag
                >
                <el-tag
                  v-if="QS_TYPE.UN_PUBLIC.value === paper.paperStatus.value"
                  type="warning"
                  size="mini"
                  >未发布</el-tag
                >
                <el-tag
                  v-if="QS_TYPE.IS_STOP.value === paper.paperStatus.value"
                  type="danger"
                  size="mini"
                  >已结束</el-tag
                >
              </span>
            </div>
            <el-divider></el-divider>
            <div class="card-content">
              <div>
                <template
                  v-if="QS_TYPE.IS_PUBLIC.value === paper.paperStatus.value"
                >
                  <el-dropdown @command="sendPaper" trigger="click">
                    <span class="el-dropdown-link">
                      发送问卷<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item
                        :command="beforeSendPaper(paper.paper_id, '1')"
                        >链接</el-dropdown-item
                      >
                      <el-dropdown-item
                        :command="beforeSendPaper(paper.paper_id, '2')"
                        >二维码
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                  <el-divider direction="vertical"></el-divider>
                </template>
                <template
                  v-if="QS_TYPE.UN_PUBLIC.value !== paper.paperStatus.value"
                >
                  <el-dropdown @command="DataHandle" trigger="click">
                    <span class="el-dropdown-link">
                      统计答卷<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item
                        :command="beforeDataHandle(paper.paper_id, '1')"
                        >数据统计
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                  <el-divider direction="vertical"></el-divider>
                </template>
                <el-dropdown @command="PaperHandle" trigger="click">
                  <span class="el-dropdown-link">
                    操作问卷<i class="el-icon-arrow-down el-icon--right"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item
                      :command="beforePaperHandle(paper.paper_id, '1')"
                      >创建为模板
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div>
                <el-button
                  @click="stopPaper(paper.paper_id)"
                  v-if="QS_TYPE.IS_PUBLIC.value === paper.paperStatus.value"
                  type="warning"
                  size="mini"
                  >结束
                </el-button>
                <el-button
                  @click="beforePublicPaper(paper.paper_id, paper.paperStatus)"
                  v-else-if="
                    QS_TYPE.UN_PUBLIC.value === paper.paperStatus.value
                  "
                  type="primary"
                  size="mini"
                  >发布
                </el-button>
                <el-button
                  @click="editPaper(paper.paper_id, paper.paperStatus)"
                  v-if="QS_TYPE.UN_PUBLIC.value === paper.paperStatus.value"
                  type="success"
                  size="mini"
                  >编辑</el-button
                >
                <el-button
                  @click="deletePaper(paper.paper_id)"
                  type="danger"
                  size="mini"
                  >删除</el-button
                >
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>

    <!--显示问卷链接的对话框-->
    <el-dialog
      title="问卷链接"
      :visible.sync="LinkDialogVisible"
      width="30%"
      append-to-body
      center
    >
      <div style="display: flex; justify-content: center">
        <span>{{ paperLink }}</span>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          type="warning"
          size="mini"
          id="linkBtn"
          :data-clipboard-text="paperLink"
          @click="copyAndClose"
          >复制并关闭</el-button
        >
      </span>
    </el-dialog>

    <!--显示问卷二维码的对话框-->
    <el-dialog
      title="问卷二维码"
      :visible.sync="QRCodeDialogVisible"
      width="30%"
      append-to-body
      center
    >
      <div style="display: flex; justify-content: center">
        <span id="qrCode" ref="qrCodeUrl"></span>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          type="warning"
          size="mini"
          @click="QRCodeDialogVisible = false"
          >关&nbsp;&nbsp;闭</el-button
        >
      </span>
    </el-dialog>

    <!--对话框：创建为问卷模板-->
    <el-dialog
      title="创建为问卷模板"
      :visible.sync="dialogFormVisible"
      width="45%"
      append-to-body
    >
      <el-form ref="modelForm" :rules="rules" :model="modelForm">
        <el-form-item label="问卷描述" prop="desc">
          <el-input
            label-width="20%"
            style="width: 80%"
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 10 }"
            v-model="modelForm.desc"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="createPaperModel('modelForm')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import QRCode from "qrcodejs2";
import ConstVal from "../util/const-val";
import dataSource from "../util/dataSource";
import modelSource from "../util/modelSource";
import { nanoid } from "nanoid";

export default {
  name: "paper",
  components: {},
  data() {
    return {
      dialogFormVisible: false,
      LinkDialogVisible: false,
      QRCodeDialogVisible: false,
      paperLink: "",
      search_text: "",
      modelForm: {
        paperId: "",
        user: "",
        desc: "",
      },
      rules: {
        desc: [{ required: true, message: "请输入问卷描述", trigger: "blur" }],
      },
      papers: [],
    };
  },
  // 页面加载时获取所有问卷
  created() {
    const papers = dataSource.getAllPapers();
    this.papers = papers || [];
  },
  // 将所有问卷按照状态排序
  computed: {
    paperList() {
      return this.papers.sort((a, b) => {
        return a.paperStatus.value - b.paperStatus.value;
      });
    },
  },

  methods: {
    // 将问卷创建为模板
    createPaperModel(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.dialogFormVisible = false;
          const paper = dataSource.getPaperById(this.modelForm.paperId);
          const model = {
            id: nanoid(6),
            title: paper.title,
            user: this.modelForm.user,
            desc: this.modelForm.desc,
            content: paper.content,
            qs_count: paper.qs_count,
            footer: paper.footer,
          };
          modelSource.saveModel(model);
        } else {
          return false;
        }
      });
    },

    // 复制并关闭
    copyAndClose() {
      let clipboard = new Clipboard("#linkBtn");

      clipboard.on("success", (e) => {
        this.$message.success("链接已复制");
        // 释放内存
        clipboard.destroy();
      });
      this.LinkDialogVisible = false;
    },

    // 问卷链接和二维码
    sendPaper(command) {
      this.paperLink = `${location.host}/paper/fill/${command.paperId}`;
      switch (command.command) {
        case "1":
          this.LinkDialogVisible = true;
          break;
        case "2": {
          this.$nextTick(() => {
            document.getElementById("qrCode").innerHTML = "";
            new QRCode(this.$refs.qrCodeUrl, {
              text: this.paperLink, // 需要转换为二维码的内容
              width: 100,
              height: 100,
              colorDark: "black", //#000000为黑色
              colorLight: "white",
            });
          });
          this.QRCodeDialogVisible = true;
          break;
        }
      }
    },

    beforeSendPaper(paperId, command) {
      return {
        paperId: paperId,
        command: command,
      };
    },

    // 数据统计
    DataHandle(command) {
      switch (command.command) {
        case "1": {
          
          this.$router.push({
            name: "paper-analyse",
            params: {
              id: command.paperId,
            },
          });
          break;
        }
      }
    },

    beforeDataHandle(paperId, command) {
      return {
        paperId: paperId,
        command: command,
      };
    },

    // 操作问卷
    PaperHandle(command) {
      switch (command.command) {
        case "1": {
          
          this.dialogFormVisible = true;
          this.modelForm.paperId = command.paperId;
          this.modelForm.user = localStorage.getItem("currentUser");
          break;
        }
      }
    },

    beforePaperHandle(paperId, command) {
      return {
        paperId: paperId,
        command: command,
      };
    },

    // 查找问卷
    searchPaperByStatus(flag) {
      this.search_text = "";
      switch (flag) {
        case 0: {
          this.papers = dataSource.getAllPapers() || [];
          break;
        }
        case 1: {
          this.papers = dataSource.getAllPublicPapers() || [];
          break;
        }
        case 2: {
          this.papers = dataSource.getAllUnPublicPapers() || [];
          break;
        }
        case 3: {
          this.papers = dataSource.getAllEndPapers() || [];
          break;
        }
      }
    },

    // 搜索问卷
    searchPaperByTitle() {
      this.papers = dataSource.getPapersByKeyword(this.search_text);
    },
    // 重置搜索框
    searchReset() {
      this.search_text = "";
    },

    // 发布问卷
    beforePublicPaper(paperId, status) {
      this.$confirm("是否发布问卷？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const data = dataSource.getPaperById(paperId);
          data.paperStatus = ConstVal.IS_PUBLIC;
          dataSource.updatePaperById(paperId, data);
          this.reloadPaper();
          this.$message({
            type: "success",
            message: "成功发布!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },

    // 结束问卷
    stopPaper(paperId) {
      this.$confirm("此操作将永久停止该问卷, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const data = dataSource.getPaperById(paperId);
          data.paperStatus = ConstVal.IS_STOP;
          dataSource.updatePaperById(paperId, data);
          this.reloadPaper();
          this.$message({
            type: "success",
            message: "结束成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },

    // 预览问卷
    prePaper(paperId) {
      const data = dataSource.getPaperById(paperId);
      const { content } = data;
      let qsList = [];
      for (let i in content) {
        if (content[i].qs_type === this.QS_TYPE.SELECT_RADIO.value)
          qsList.push({
            key: content[i].qs_order,
            name: "SelectRadio",
          });
        if (content[i].qs_type === this.QS_TYPE.SELECT_MULTI.value)
          qsList.push({
            key: content[i].qs_order,
            name: "SelectMulti",
          });
        if (content[i].qs_type === this.QS_TYPE.SELECT_DROP.value)
          qsList.push({
            key: content[i].qs_order,
            name: "SelectDrop",
          });
        if (content[i].qs_type === this.QS_TYPE.FILL_SINGLE.value)
          qsList.push({
            key: content[i].qs_order,
            name: "FillSingle",
          });
        if (content[i].qs_type === this.QS_TYPE.FILL_MULTI.value)
          qsList.push({
            key: content[i].qs_order,
            name: "FillMulti",
          });
        if (content[i].qs_type === this.QS_TYPE.FILL_DATE.value)
          qsList.push({
            key: content[i].qs_order,
            name: "FillDate",
          });
        if (content[i].qs_type === this.QS_TYPE.SHORTCUT_NAME.value)
          qsList.push({
            key: content[i].qs_order,
            name: "NameStr",
          });
        if (content[i].qs_type === this.QS_TYPE.SHORTCUT_GENDER.value)
          qsList.push({
            key: content[i].qs_order,
            name: "Gender",
          });
        if (content[i].qs_type === this.QS_TYPE.SHORTCUT_PHONE.value)
          qsList.push({
            key: content[i].qs_order,
            name: "Phone",
          });
      }
      this.$router.push({
        name: "paper-view",
        params: { paperInfo: data, preQsList: qsList, pre: "true" },
      });
    },

    // 编辑问卷
    editPaper(paperId, status) {
      if (status.value === this.QS_TYPE.IS_PUBLIC.value) {
        this.$message.warning("问卷已发布，无法编辑");
        return false;
      }

      if (status.value === this.QS_TYPE.IS_STOP.value) {
        this.$message.warning("问卷已结束，无法编辑");
        return false;
      }

      // 跳转到问卷编辑页面
      this.$router.push({
        name: "paper-edit",
        params: { edit: "true", paperId: paperId },
      });
    },

    // 删除问卷
    deletePaper(paperId) {
      this.$confirm("此操作将永久删除该问卷所有数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          dataSource.deletePaperById(paperId);
          this.reloadPaper();
          this.$message.success("删除成功");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    // 重新加载问卷列表
    reloadPaper() {
      this.papers = dataSource.getAllPapers();
    },
  },
};
</script>

<style lang="scss" scoped>
.el-dropdown-link {
  cursor: pointer;
}

.left-word {
  i {
    font-weight: bolder;
  }

  font-weight: bolder;
  font-size: larger;
}

.left-nav {
  margin-top: 20px;

  ul {
    list-style: none;
    margin: 0 auto;
    padding: 0;
    width: 250px;

    li {
      text-align: left;
      cursor: pointer;

      a,
      a:visited {
        background-color: #f7f7f7; /* 背景色 */
        border: 1px #e9eef3 solid; /* 边框 */
        color: #333333; /* 文字颜色 */
        display: block; /* 此元素将显示为块级元素，此元素前后会带有换行符 */
        line-height: 40px; /* 行高 */
        padding: 4px 50px; /* 内部填充的距离 */
        text-decoration: none; /* 不显示超链接下划线 */
        white-space: nowrap; /* 对于文本内的空白处，不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 */
      }
    }
  }
}

.left-nav ul li a:hover {
  background-color: #f0f0f0; /* 背景色 */
  font-weight: bold;
  text-decoration: none; /* 不显示超链接下划线 */
  .myi {
    font-weight: bold;
  }
}

.main-top {
  h2 {
    margin: 0;
    padding: 0;
  }

  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  height: 50px;
  line-height: 50px;
}

.el-divider--horizontal {
  margin: 12px 0;
}

.main-content {
  .box-card {
    margin-bottom: 15px;

    .card-top {
      display: flex;
      justify-content: space-between;
      height: 32px;
      line-height: 32px;

      a,
      a:active,
      a:visited {
        text-decoration: none;
        color: #333333;
      }

      a:hover {
        color: #1e649f;
      }
    }

    .card-content {
      display: flex;
      justify-content: space-between;
      height: 30px;
      line-height: 30px;
    }
  }
}
</style>
