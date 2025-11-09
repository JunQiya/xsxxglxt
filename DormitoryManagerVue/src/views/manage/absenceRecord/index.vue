<template>
  <el-form :model="form" label-width="120px">
    <!-- 学院部门信息 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="学校" prop="school">
          <el-input v-model="form.school" placeholder="自动填充" :disabled="true" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="二级学院" prop="department">
          <el-input v-model="form.department" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 班级选择器 -->
    <el-form-item label="班级选择">
      <el-radio-group v-model="selectMode">
        <el-radio-button label="cascade">层级选择</el-radio-button>
        <el-radio-button label="search">搜索选择</el-radio-button>
      </el-radio-group>


    <el-cascader
    v-if="selectMode === 'cascade'"
    v-model="cascaderValue" 
    :options="facultyOptions"
    :props="cascaderProps"
    placeholder="请选择学校/学院/专业/班级"
    filterable
    @change="handleCascaderChange" 
    />

      <!-- 搜索选择 -->
      <el-select
        v-else
        v-model="form.classId"
        filterable
        placeholder="输入班级名称搜索"
      >
        <el-option
          v-for="item in flatClasses"
          :key="item.id"
          :label="item.fullName"
          :value="item.id"
        />
      </el-select>
    </el-form-item>

    <!-- 其他表单字段 -->
    <el-form-item label="缺勤日期" prop="absentDate">
      <el-date-picker
        v-model="form.absentDate"
        type="date"
        value-format="yyyy-MM-dd"
      />
    </el-form-item>
    
    <el-form-item label="学生信息">
      <el-input v-model="form.name" placeholder="姓名" />
      <el-radio-group v-model="form.gender">
        <el-radio :label="1">男</el-radio>
        <el-radio :label="0">女</el-radio>
      </el-radio-group>
      <br>年龄：
      <el-input-number v-model="form.age" :min="0" />
    </el-form-item>

        <!-- 登记人信息 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="登记人" prop="registrar">
          <el-input v-model="form.registrar" placeholder="请输入登记人姓名" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="登记电话" prop="registrarPhone">
          <el-input v-model="form.registrarPhone" placeholder="请输入联系电话" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="报送日期" prop="reportDate">
          <el-date-picker
            v-model="form.reportDate"
            type="date"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 就诊信息 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="就诊日期" prop="visitDate">
          <el-date-picker
            v-model="form.visitDate"
            type="date"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="就诊医院" prop="hospital">
          <el-input v-model="form.hospital" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="诊断结果" prop="diagnosis">
          <el-input v-model="form.diagnosis" />
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 其他补充字段 -->
    <el-form-item label="学生电话" prop="phone">
      <el-input v-model="form.phone" />
    </el-form-item>

    <el-form-item label="主要症状" prop="symptoms">
      <el-input type="textarea" v-model="form.symptoms" />
    </el-form-item>

    <el-row :gutter="20">
      <el-col :span="8">
        <el-form-item label="返校时间" prop="returnDate">
          <el-date-picker
            v-model="form.returnDate"
            type="date"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="是否治愈">
          <el-radio-group v-model="form.isCured">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="是否传染病">
          <el-radio-group v-model="form.isInfectious">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>

    <!-- 提交按钮 -->
    <el-form-item>
      <el-button type="primary" @click="submitForm">提交</el-button>
    </el-form-item>

  </el-form>
</template>

<script>
import { list } from '@/api/faculty'
import { addAbsenceRecord } from '@/api/absenceRecord'

export default {
  data() {
    return {
      selectMode: 'cascade',
      cascaderValue: [],  // 新增临时存储数组
      form: {
        classId: null,
        school: '云南旅游职业学院',
        department: '',
        name: '',
        gender: 1,
        age: null,
        absentDate: '',
        registrar: '',
        registrarPhone: '',
        reportDate: '',
        phone: '',
        symptoms: '',
        visitDate: '',
        hospital: '',
        diagnosis: '',
        returnDate: '',
        isCured: 0,
        isInfectious: 0
      },
      cascaderProps: {
        value: 'id',
        label: 'name',
        children: 'children',
        checkStrictly: true
      },
      facultyOptions: [],
      flatClasses: []
    }
  },
  async created() {
    const { data } = await list()
    this.facultyOptions = data
    this.flatClasses = this.flattenClasses(data)
  },
  methods: {

    handleCascaderChange(valueArr) {
    // 取路径数组的最后一个元素（班级ID）
    this.form.classId = valueArr[valueArr.length - 1]
    },

    async submitForm() {
        try {
         // 转换驼峰字段为蛇形命名
        const postData = {
        ...this.form,
        class_id: this.form.classId,
        registrar_phone: this.form.registrarPhone,
        visit_date: this.form.visitDate,
        report_date: this.form.reportDate,
        absent_date: this.form.absentDate,
        return_date: this.form.returnDate,
        is_cured: this.form.isCured,
        is_infectious: this.form.isInfectious
    }
        delete postData.classId          // 对应class_id
        delete postData.registrarPhone   // 对应registrar_phone
        delete postData.reportDate       // 对应report_date
        delete postData.absentDate       // 对应absent_date
        delete postData.returnDate       // 对应return_date
        delete postData.isCured           // 对应is_cured
        delete postData.isInfectious     // 对应is_infectious
        // 在提交前确保classId是单个值
        if (Array.isArray(this.form.classId)) {
        this.form.classId = this.form.classId[this.form.classId.length - 1]
        }
        console.log('最终提交数据:', postData) // 确保这里打印的是转换后的数据
            await addAbsenceRecord(postData)  // 关键修改：提交postData而不是this.form
        this.$message.success('记录添加成功')
        this.resetForm()
        } catch (error) {
        this.$message.error('提交失败')
        }
    },
    resetForm() {
        this.form = {
        // 重置所有字段...
        class_id: null,
        school: '云南旅游职业学院',
        department: '',
        name: '',
        gender: 1,
        age: null,
        absent_date: '',
        registrar: '',
        registrar_phone: '',
        report_date: '',
        phone: '',
        symptoms: '',
        visit_date: '',
        hospital: '',
        diagnosis: '',
        return_date: '',
        is_cured: 0,
        is_infectious: 0
        }
    },

    flattenClasses(tree) {
      const result = []
      const walk = (nodes, path = []) => {
        nodes.forEach(node => {
          const currentPath = [...path, node.name]
          if (node.children && node.children.length > 0) {
            walk(node.children, currentPath)
          } else {
            result.push({
              id: node.id,
              fullName: currentPath.join(' / ')
            })
          }
        })
      }
      walk(tree)
      return result
    }
  }
}
</script>
