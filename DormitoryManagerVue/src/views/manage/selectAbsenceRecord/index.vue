<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" :model="condition" class="demo-form-inline">
      <el-form-item label="学生姓名">
        <el-input v-model="condition.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="缺勤日期">
        <el-date-picker
          v-model="condition.absentDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择日期">
        </el-date-picker>
        <el-button @click="printTable">打印表格</el-button>
      </el-form-item>
      
      <!-- 新增班级选择器 -->
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
  @change="handleCascaderChange"
/>


        <el-select
          v-else
          v-model="condition.classId"
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

      <el-form-item>
        <el-button type="primary" @click="handleSearch">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据表格 -->
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100"></el-table-column>
      <el-table-column label="性别" width="80">
        <template slot-scope="scope">
          {{ scope.row.gender === 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80"></el-table-column>
      <el-table-column label="所属班级" width="200">
        <template slot-scope="scope">
          {{ getClassName(scope.row.class_id) }}
        </template>
      </el-table-column>
      <el-table-column prop="symptoms" label="症状"></el-table-column>
      <el-table-column prop="hospital" label="就诊医院"></el-table-column>
      <el-table-column label="缺勤日期" width="120">
        <template slot-scope="scope">
          {{ formatDate(scope.row.absent_date) }}
        </template>
      </el-table-column>
      <el-table-column label="返校日期" width="120">
        <template slot-scope="scope">
          {{ formatDate(scope.row.return_date) }}
        </template>
      </el-table-column>
      
      <!-- 操作列 -->
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑对话框 -->
    <el-dialog title="编辑记录" :visible.sync="dialogVisible">
      <el-form :model="currentRecord">
        <el-form-item label="是否治愈">
          <el-radio-group v-model="currentRecord.is_cured">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否传染病">
          <el-radio-group v-model="currentRecord.is_infectious">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmUpdate">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { 
  getAllRecords,
  searchRecords,
  deleteRecord,
  updateAbsenceRecord
} from '@/api/absenceRecord'
import { list } from '@/api/faculty'


export default {
  data() {
    return {
      tableData: [],
      condition: {
        name: '',
        absentDate: null,
        classId: null
      },
      dialogVisible: false,
      currentRecord: {},
      // 新增班级选择相关数据
      selectMode: 'cascade',
      facultyOptions: [],
      flatClasses: [],
      cascaderProps: {
        value: 'id',
        label: 'name',
        children: 'children',
        checkStrictly: true
      }
    }
  },
  async created() {
    await this.loadData()
    // 加载班级数据
    const { data } = await list()
    this.facultyOptions = data
    this.flatClasses = this.flattenClasses(data)
  },
  methods: {

printTable() {
      window.print();
    },

     handleCascaderChange(valueArr) {
    // 取最后一级的班级ID
    this.condition.classId = valueArr.length > 0 ? valueArr[valueArr.length - 1] : null
  },

    // 加载数据
    async loadData() {
      try {
        const { data } = await getAllRecords()
        this.tableData = data
      } catch (error) {
        this.$message.error('数据加载失败')
      }
    },

    // 班级数据扁平化
    flattenClasses(tree) {
      const result = []
      const walk = (nodes, path = []) => {
        nodes.forEach(node => {
          const currentPath = [...path, node.name]
          if (node.children?.length) {
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
    },

    // 获取班级名称
    getClassName(classId) {
      const classInfo = this.flatClasses.find(item => item.id === classId)
      return classInfo ? classInfo.fullName : '未知班级'
    },

    // 日期格式化
  formatDate(dateString) {
    if (!dateString) return ''
    const date = new Date(dateString)
    // 处理Safari兼容性问题
    const pad = n => n.toString().padStart(2, '0')
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`
  },

    // 条件查询
    async handleSearch() {
      try {
        // 转换参数结构匹配后端接口
        const params = {
          name: this.condition.name,
        beginAbsentDate: this.condition.absentDate,
        endAbsentDate: this.condition.absentDate,
        classId: this.condition.classId // 直接传递单个值
        }
        
        const { data } = await searchRecords(params)
        this.tableData = data
      } catch (error) {
        this.$message.error('查询失败')
      }
    },


    // 重置查询
    resetSearch() {
      this.condition = {
        name: '',
        absentDate: null,
        classId: null
      }
      this.loadData()
    },

    // 删除记录
    async handleDelete(index, row) {
      try {
        await deleteRecord(row.id)
        this.$message.success('删除成功')
        this.tableData.splice(index, 1)
      } catch (error) {
        this.$message.error('删除失败')
      }
    },

    // 打开编辑对话框
    handleEdit(index, row) {
      this.currentRecord = { ...row }
      this.dialogVisible = true
    },

    // 确认更新
    async confirmUpdate() {
      try {
        await updateAbsenceRecord(this.currentRecord)
        this.$message.success('更新成功')
        this.dialogVisible = false
        this.loadData()
      } catch (error) {
        this.$message.error('更新失败')
      }
    }
  }
}
</script>
