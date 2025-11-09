<template>
  <div class="application-container">
    <!-- 全局操作按钮 -->
    <el-button type="primary" @click="dialogVisible = true" class="action-btn">
      <i class="el-icon-plus"></i> 新建申请
    </el-button>

    <!-- 主对话框 -->
    <el-dialog title="大学生走读申请表" :visible.sync="dialogVisible" width="90%" :before-close="handleClose"
      class="custom-dialog">
      <!-- 顶部信息栏 -->
      <div class="header-bar">
        <span>申请编号：{{ formData.id }}</span>
        <span>当前状态：{{ statusMap[formData.status] }}</span>
        <el-radio-group v-model="formData.printType" class="print-control">
          <el-radio :label="'preview'">预览模式</el-radio>
          <el-radio :label="'edit'">编辑模式</el-radio>
        </el-radio-group>
      </div>

      <!-- 主表单区域 -->
      <el-form :model="formData" :rules="rules" ref="form" label-width="150px" class="main-form">
        <!-- 学生信息 -->
        <el-card class="info-card">
          <div slot="header" class="card-header">
            <i class="el-icon-user"></i> 学生基本信息
          </div>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="学号" prop="student_no">
                <el-input v-model="formData.student_no" :disabled="formData.status !== 'draft'"
                  @blur="validateStudentNo"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="姓名" prop="studentName">
                <el-input v-model="formData.studentName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="性别" prop="gender">
                <el-select v-model="formData.gender">
                  <el-option label="男" value="male"></el-option>
                  <el-option label="女" value="female"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="所在院系" prop="department">
            <el-input v-model="formData.department"></el-input>
          </el-form-item>
          <el-col :span="8">
            <el-form-item label="所属班级" prop="className">
              <el-input v-model="formData.className"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电话" prop="phone">
              <el-input v-model="formData.phone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="家长姓名" prop="parentName">
              <el-input v-model="formData.parentName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="家长电话" prop="parentPhone">
              <el-input v-model="formData.parentPhone"></el-input>
            </el-form-item>
          </el-col>
        </el-card>

        <!-- 走读信息 -->
        <el-card class="info-card">
          <div slot="header" class="card-header">
            <i class="el-icon-location"></i> 走读信息登记
          </div>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="校外居住地址" prop="address">
                <el-input type="textarea" v-model="formData.address"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="紧急联系人" prop="emergencyContact">
                <el-input v-model="formData.emergency_contact"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="紧急联系人电话" prop="emergencyPhone">
                <el-input v-model="formData.emergencyPhone"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="走读时段" prop="timePeriod">
            <el-date-picker v-model="formData.time_period" type="daterange" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-col :span="24">
            <el-form-item label="走读原因" prop="reason">
              <el-input type="textarea" v-model="formData.reason"></el-input>
            </el-form-item>
          </el-col>
        </el-card>

        <!-- 审批材料 -->
        <!-- <el-card class="info-card">
          <div slot="header" class="card-header">
            <i class="el-icon-document"></i> 申请材料
          </div>
          <el-upload
            action="#"
            list-type="picture-card"
            :file-list="formData.attachments"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-upload="beforeUpload"
            :auto-upload="false"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <div slot="tip" class="el-upload__tip">支持上传：身份证、租房合同、房产证明</div>
        </el-card> -->

        <!-- 审批流程 -->
        <el-card class="info-card">
          <div slot="header" class="card-header">
            <i class="el-icon-s-custom"></i> 审批流程
          </div>

          <el-form-item label="家长意见" prop="parentOpinion">
            <el-input type="textarea" v-model="formData.parentOpinion" placeholder="请填写家长确认意见"></el-input>
          </el-form-item>
          <el-form-item label="家长同意" prop="parentAgree">
            <el-select v-model="formData.parent_agree">
              <el-option label="已同意" value="已同意"></el-option>
              <el-option label="已拒绝" value="已拒绝"></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="辅导员审核" prop="counselorStatus">
            <el-select v-model="formData.counselorStatus">
              <el-option label="待审核" value="pending"></el-option>
              <el-option label="同意" value="approved"></el-option>
              <el-option label="不同意" value="rejected"></el-option>
            </el-select>
          </el-form-item>
                    <el-form-item label="二级学院审核" prop="college">
            <el-select v-model="formData.college">
              <el-option label="待审核" value="pending"></el-option>
              <el-option label="同意" value="approved"></el-option>
              <el-option label="不同意" value="rejected"></el-option>
            </el-select>
          </el-form-item> -->
        </el-card>

        <!-- 电子签名 -->
        <el-card class="signature-card">
          <div slot="header" class="card-header">
            <i class="el-icon-edit"></i> 电子签名
          </div>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="学生签字" prop="studentSignature">
                <el-input type="text" v-model="formData.studentSignature" placeholder="请在此签名"
                  class="signature-input"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="家长签字" prop="parentSignature">
                <el-input type="text" v-model="formData.parentSignature" placeholder="请在此签名"
                  class="signature-input"></el-input>
              </el-form-item>
            </el-col></el-row>
        </el-card>

        <!-- 备注信息 -->
        <el-alert title="注意事项" type="warning" show-icon :closable="false" class="notice-alert">
          <template #content>
            <ul class="notice-list">
              <li>• 申请有效期最长不超过一学年</li>
              <li>• 需提供正规租房合同复印件</li>
              <li>• 紧急联系人需填写有效联系方式</li>
            </ul>
          </template>
        </el-alert>
      </el-form>

      <!-- 操作按钮 -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
          提交
        </el-button>
        <el-button @click="dialogVisible = false">关闭</el-button>

      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {
    createApplication,
    updateApplication,
    uploadFile
  } from '@/api/leaveApplication'
  import { list } from '@/api/student'

  export default {
    data() {
      return {
        isEdit: false,
        dialogVisible: false,
        submitLoading: false,
        statusMap: {
          draft: '草稿',
          submitted: '已提交',
          approved: '已批准',
          rejected: '已拒绝'
        },
        formData: {
          id: null,
          student_no: '',
          student_name: '',  // 统一使用蛇形命名
          gender: 'male',
          department: '',
          class_name: '',    // 统一使用蛇形命名
          phone: '',
          parent_name: '',   // 统一使用蛇形命名
          parent_phone: '',  // 统一使用蛇形命名
          address: '',
          emergency_contact: '',  // 统一使用蛇形命名
          emergency_phone: '',    // 添加紧急联系人电话字段
          time_period: [],        // 统一使用蛇形命名
          attachments: [],
          parent_opinion: '',  // 统一使用蛇形命名
          parent_agree: '已同意',  // 新增家长同意选项，默认值为'已同意'
          counselor_status: '待审核',
          college_status: '待审核',
          student_signature: '',  // 统一使用蛇形命名
          parent_signature: '',    // 统一使用蛇形命名
          status: 'draft',
          reason: ''
        },
        rules: {
          student_no: [
            { required: true, message: '学号必填', trigger: 'blur' },
            { pattern: /^\d{11}$/, message: '学号必须为11位数字' }
          ],
          studentName: [
            { required: true, message: '姓名必填', trigger: 'blur' },
            { min: 2, max: 10, message: '姓名长度2-10字符' }
          ],
          phone: [
            { required: true, message: '电话必填', trigger: 'blur' },
            { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号码' }
          ],
          parentPhone: [
            { required: true, message: '家长电话必填', trigger: 'blur' },
            { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号码' }
          ],
          address: [
            { required: true, message: '地址必填', trigger: 'blur' },
            { min: 10, message: '地址需完整填写' }
          ],
          time_period: [
            { validator: this.validateDateRange, trigger: 'change' }
          ],
          parent_opinion: [
            { required: true, message: '家长意见必填', trigger: 'blur' }
          ],
          reason: [
            { required: true, message: '走读原因必填', trigger: 'blur' }
          ],
          emergencyPhone: [
            { required: true, message: '紧急联系人电话必填', trigger: 'blur' },
            { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号码' }
          ],
        }
      }
    },
    computed: {
      // 驼峰命名转蛇形命名（双向绑定）
      studentId: {
        get() { return this.formData.student_no },
        set(value) { this.formData.student_no = value }
      },
      studentName: {
        get() { return this.formData.student_name },
        set(value) { this.formData.student_name = value }
      },
      department: {
        get() { return this.formData.department },
        set(value) { this.formData.department = value }
      },
      parentContact: {
        get() { return this.formData.emergency_contact },
        set(value) { this.formData.emergency_contact = value }
      },
      className: {
        get() { return this.formData.class_name },
        set(value) { this.formData.class_name = value }
      },
      phone: {
        get() { return this.formData.phone },
        set(value) { this.formData.phone = value }
      },
      parentName: {
        get() { return this.formData.parent_name },
        set(value) { this.formData.parent_name = value }
      },
      parentPhone: {
        get() { return this.formData.parent_phone },
        set(value) { this.formData.parent_phone = value }
      }
    },
    methods: {

      handleClose(done) { // 接收 done 参数
        if (this.formData.status === 'draft') {
          // 草稿状态允许直接关闭
          done();
        } else {
          // 其他状态需要确认关闭
          this.$confirm('未提交的修改将丢失，确认关闭吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            done(); // 用户确认关闭
          }).catch(() => {
            // 用户取消关闭，不执行 done()
          });
        }
      },
      // 日期范围验证
      validateDateRange(rule, value, callback) {
        if (!value || value.length !== 2) {
          callback(new Error('请选择完整的时间段'))
        } else if (value[0] > value[1]) {
          callback(new Error('结束日期不能早于开始日期'))
        } else {
          callback()
        }
      },
      async validateStudentNo() {
        if (!this.formData.student_no || this.formData.student_no.trim() === '') {
          return
        }

        try {
          const response = await list({ entity: { number: this.formData.student_no } })
          if (response.data.list.length === 0) {
            this.$message.error('该学号不存在')
            this.formData.student_no = ''  // 清空输入
          } else {
            const studentInfo = response.data.list[0]
            // 自动填充学生信息
            this.formData.studentName = studentInfo.name
            this.formData.className = studentInfo.faculty.name
            this.formData.phone = studentInfo.phone
            this.$message.success('学号验证成功，已自动填充学生信息')
          }
        } catch (error) {
          console.error('学号验证失败:', error)
          this.$message.error('学号验证失败，请稍后重试')
        }
      },

      // 附件处理
      async handleAttachments() {
        if (this.formData.attachments.length === 0) return []

        try {
          const uploadPromises = this.formData.attachments.map(async file => {
            const formData = new FormData()
            formData.append('file', file.raw)
            formData.append('apply_id', this.formData.id)
            return await uploadFile(formData)
          })

          return await Promise.all(uploadPromises)
        } catch (error) {
          this.$message.error('附件上传失败')
          throw error
        }
      },

      // 表单重置
      resetForm() {
        this.formData = {
          id: null,
          student_no: '',
          student_name: '',
          gender: 'male',
          department: '',
          address: '',
          emergency_contact: '',
          emergency_phone: '',  // 重置紧急联系人电话字段
          time_period: [],
          attachments: [],
          parent_opinion: '',
          parent_agree: '已同意',  // 重置家长同意选项
          counselor_status: '待审核',
          college_status: '待审核',
          student_signature: '',
          parent_signature: '',
          status: 'draft',
          reason: ''
        }
        this.$nextTick(() => {
          this.$refs.form.clearValidate()
        })
      },

      // 编辑模式
      editApplication(row) {
        this.isEdit = true
        this.formData = { ...row }
        this.dialogVisible = true
      },

      // 提交处理
      async handleSubmit() {
        try {
          // 先进行表单验证
          await this.$refs.form.validate()

          // 检查student_no是否为空
          if (!this.formData.student_no || this.formData.student_no.trim() === '') {
            this.$message.error('学号不能为空')
            return
          }


          // 处理附件上传
          const attachments = await this.handleAttachments()

          // 构造请求数据时，确保字段名一致
          const requestData = {
            applyId: this.formData.id,
            studentNo: this.formData.student_no,
            studentName: this.formData.student_name || this.formData.studentName,
            gender: this.formData.gender,
            department: this.formData.department,
            className: this.formData.class_name || this.formData.className,
            phone: this.formData.phone,
            parentName: this.formData.parent_name || this.formData.parentName,
            parentPhone: this.formData.parent_phone || this.formData.parentPhone,
            address: this.formData.address,
            startDate: this.formData.time_period[0] ? new Date(this.formData.time_period[0]).toISOString() : null,
            endDate: this.formData.time_period[1] ? new Date(this.formData.time_period[1]).toISOString() : null,
            reason: this.formData.reason.trim(),
            emergencyContact: this.formData.emergency_contact || this.formData.emergencyContact,
            emergencyPhone: this.formData.emergency_phone,  // 使用formData中的字段名
            parentOpinion: this.formData.parent_opinion || this.formData.parentOpinion,
            parentAgree: this.formData.parent_agree,  // 将家长同意选项加入请求数据
            counselorStatus: this.formData.counselor_status,
            collegeStatus: this.formData.college_status,
            studentSignature: this.formData.student_signature || this.formData.studentSignature,
            parentSignature: this.formData.parent_signature || this.formData.parentSignature,
            status: this.isEdit ? this.formData.status : 'submitted',
            createTime: new Date(),
            updateTime: new Date(),
            idCardPath: this.formData.id_card_path,
            contractPath: this.formData.contract_path,
            otherFilePath: this.formData.other_file_path
          };

          // 调试信息
          console.log('Request Data:', requestData)

          // 调用API
          if (this.isEdit) {
            await updateApplication(requestData)
            this.$message.success('申请已更新')
          } else {
            const response = await createApplication(requestData)
            console.log('API Response:', response)
            this.$message.success('申请已创建')
          }

          // 重置状态
          this.dialogVisible = false
          this.isEdit = false
          this.resetForm()
          this.$emit('refresh-list')

        } catch (error) {
          console.error('提交失败:', error)
          this.$message.error('操作失败：' + (error.response?.data?.message || '未知错误'))
        } finally {
          this.submitLoading = false
        }
      }
    }
  }
</script>

<style scoped>
  .application-container {
    padding: 20px;
    background-color: #f5f7fa;
    min-height: calc(100vh - 40px);
  }

  .signature-input {
    width: 100%;
    min-height: 40px;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
    padding: 8px;
    transition: border-color 0.2s;
  }

  .signature-input:focus {
    border-color: #409EFF;
  }

  .info-card {
    margin-bottom: 25px;
    border: none;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s;
  }

  .info-card:hover {
    box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.15);
  }
</style>