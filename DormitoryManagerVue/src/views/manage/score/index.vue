<template>
  <div class="router-container">
    <div class="container">
      <div class="inner_container">
        <div class="table" v-loading="loading">
          <div class="score_table">
            <div class="room_selector">
              <div class="room_selector">
                <span>选择楼栋：</span>
                <el-cascader :options="bdOptions" v-model="selectedtNode" @change="bdChange"></el-cascader>
              </div>
              <div class="room_selector">
                <span>选择宿舍：</span>
                <el-select v-model="roomValue" clearable>
                  <el-option v-for="item in roomOptions" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </div>

            </div>
            <el-form ref="scoreForm" :model="formData">
              <el-form-item label="整体环境评分：" prop="score.global">
                <el-input-number v-model="formData.score.global_score" :min="0" :max="10" />
              </el-form-item>
              <el-form-item label="建筑设施评分：" prop="score.building">
                <el-input-number v-model="formData.score.building_score" :min="0" :max="10" />
              </el-form-item>
              <el-form-item label="床铺卧具评分：" prop="score.bed">
                <el-input-number v-model="formData.score.bed_score" :min="0" :max="10" />
              </el-form-item>
              <el-form-item label="室内用品评分：" prop="score.indoor">
                <el-input-number v-model="formData.score.indoor_score" :min="0" :max="10" />
              </el-form-item>
            </el-form>
          </div>
          <div class="img_box">
            <el-upload ref="upload" action="#" :file-list="formData.fileList" :class="{ 'hide-upload': hideUpload }"
              list-type="picture-card" :limit="limit" :auto-upload="false" :on-change="handleImgChange" :on-remove="handleRemove">
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
                <span class="el-upload-list__item-actions">
                  <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
                    <i class="el-icon-download"></i>
                  </span>
                  <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleRemove(file)">
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
            <el-button v-has-permi="" type="primary" @click="submit" v-has-permi="['score:saveOrUpdate']">提交</el-button>
            <el-button v-has-permi="" type="warning" @click="reset">重置</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { list } from "@/api/building";
  import { listByBId, saveOrUpdate } from "@/api/score"

  export default {
    name: "index",
    data() {
      return {
        loading: undefined,
        // 宿舍列表
        roomOptions: [],
        roomValue: '',
        // 三级菜单列表
        selectedtNode: [],
        bdOptions: [],
        // 表单
        formData: {
          score: { global_score: 0, building_score: 0, bed_score: 0, indoor_score: 0 },
          roomId: '',
          fileList: []
        },
        // 图片上传
        dialogImageUrl: '',
        dialogVisible: false,
        disabled: false,
        hideUpload:false,
        limit:1
      };
    },
    methods: {
      // 提交数据
      submit() {
        this.$refs.scoreForm.validate(async valid => {

          const _this = this

          if (!valid || !this.roomValue) {
            this.$message.error('请填写完整表单并选择宿舍')
            return
          }

          // 构建FormData
          const formData = new FormData();
          // 将 scores 转为 Blob 并指定 JSON 类型
          const scoresBlob = new Blob(
            [JSON.stringify(this.formData.score)],
            { type: 'application/json' }
          );
          formData.append('score', scoresBlob);
          formData.append('roomId', this.roomValue);

          // 添加图片（仅限1张）
          if (this.$refs.upload.uploadFiles.length > 0) {
            const file = this.$refs.upload.uploadFiles[0].raw;
            formData.append('file', file);
          }

          for (let [key, value] of formData.entries()) {
            console.log(key, value); // 确认字段名是 "score" 且值是 JSON 字符串
          }

          saveOrUpdate(formData).then(res => {
            if (res.statusCode === 200) {
              this.$message({
                message: res.msg,
                type: 'success'
              });
            }
          })
          _this.listData()
          _this.reset()
        })
      },
      // 重置表单
      reset() {
        this.$refs.scoreForm.resetFields();
        this.$refs.upload.clearFiles();
        this.formData.fileList = [];
        this.selectedtNode = [];
        this.roomOptions = [];
        this.roomValue = '';
        this.formData.score.global_score = 0;
        this.formData.score.bed_score = 0;
        this.formData.score.building_score = 0;
        this.formData.score.indoor_score = 0;
        this.hideUpload = false
      },
      // 三级菜单触发方法
      async bdChange(value) {
        this.loading = true
        const { data } = await listByBId(value[value.length - 1])
        this.roomOptions = this.formatData(data)
        console.log(this.roomOptions)
        this.loading = false
      },
      // 图片上传相关方法
      handleImgChange(file,fileList) {
        this.hideUpload = fileList.length >= this.limit; 
        this.formData.fileList = fileList;
      },
      // 图片删除
      handleRemove(file) {
        this.formData.fileList = [];
        this.hideUpload = false
      },
      // 图片预览
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      // 图片下载
      handleDownload(file) {
        console.log(file);
        console.log(this.roomValue)
      },
      // 格式化数据
      formatData(list) {
        return list.map(item => {
          const node = {
            value: item.id,
            label: item.number || item.name
          };
          // 仅当存在子节点且为数组时，递归格式化子节点
          if (Array.isArray(item.children) && item.children.length > 0) {
            node.children = this.formatData(item.children);
          }

          return node;
        });
      },
      async listData() {
        this.loading = true
        const { data } = await list()
        console.log(this.formatData(data))
        this.bdOptions = this.formatData(data)
        this.loading = false
      }
    },
    created() {
      this.listData()
    }
  }
</script>

<style scoped>
  .table {
    padding: 10px;
    display: flex;
  }

  .score_table {
    flex: 1;
    margin-left: 5%;
  }

  .img_box {
    flex: 2
  }

  .router-container {
    height: 100%;
  }

  .room_selector {
    margin-bottom: 8%;
  }

  .el-rate {
    line-height: 2.5;
    /* 重置行高 */
    vertical-align: middle;
    /* 垂直对齐 */
  }

  .el-button {
    margin-top: 5%;
  }

  /* 隐藏上传按钮 */
  ::v-deep .hide-upload .el-upload--picture-card {
    display: none !important;
  }
</style>