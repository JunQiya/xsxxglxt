<template>
    <div class="app-container">
        <!-- 新增搜索表单 -->
        <el-form :inline="true" class="search-form">
            <el-form-item label="班级">
                <el-input v-model="searchClass" placeholder="输入班级名称" clearable/>
            </el-form-item>
            <el-form-item label="姓名">
                <el-input v-model="searchName" placeholder="输入学生姓名" clearable/>
            </el-form-item>
            <el-form-item label="学号">
                <el-input v-model="searchStudentNo" placeholder="输入学生学号" clearable/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="searchByClass">按班级查</el-button>
                <el-button type="success" @click="searchByName">按姓名查</el-button>
                <el-button type="warning" @click="searchByStudentNo">按学号查</el-button>
                <el-button type="info" @click="searchApproved">显示已通过</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="applications" v-loading="loading">
            <el-table-column prop="studentName" label="学生姓名" width="120"></el-table-column>
            <el-table-column prop="className" label="班级" width="150"></el-table-column>
            <el-table-column prop="department" label="院系" width="150"></el-table-column>
            <el-table-column prop="phone" label="联系电话" width="120"></el-table-column>
            <el-table-column prop="startDate" label="开始日期" width="120" :formatter="formatDate"></el-table-column>
            <el-table-column prop="endDate" label="结束日期" width="120" :formatter="formatDate"></el-table-column>
            <el-table-column prop="reason" label="请假原因" min-width="200"></el-table-column>
            <el-table-column prop="counselorStatus" label="审批状态" width="100">
                <template #default="scope">
                    <el-tag :type="scope.row.counselorStatus === '待审核' ? 'warning' : 'success'">
                        {{ scope.row.counselorStatus }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
                <template #default="scope">
                    <el-button type="primary" size="small" @click="handleApprove(scope.row)">审批通过</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { 
    getCounselorPendingApplications,
    approveByCounselor,
    getByClassName,
    getByStudentName,
    getByStudentNoAndStatus,
    getApprovedApplications
} from '@/api/leaveApplication'

export default {
    data() {
        return {
            applications: [],
            loading: false,
            searchClass: '',
            searchName: '',
            searchStudentNo: ''
        }
    },
    created() {
        this.fetchPendingApplications()
    },
    methods: {
        async fetchPendingApplications() {
            this.loading = true
            try {
                const res = await getCounselorPendingApplications()
                console.log('完整响应:', res)
                
                // 修改判断条件和数据路径
                if (res?.statusCode === 200 && Array.isArray(res.data)) {
                    this.applications = res.data
                    console.log('处理后的数据:', this.applications)
                } else {
                    this.$message.error(res?.msg || '数据格式异常')  // 使用msg字段
                }
            } catch (error) {
                console.error('请求异常:', error.response || error)
                this.$message.error(error.message || '请求失败')
            } finally {
                this.loading = false
            }
        },
        async handleApprove(row) {
            try {
                // 使用封装好的API方法
                await approveByCounselor(row.applyId)
                this.$message.success('审批成功')
                // 刷新列表数据
                await this.fetchPendingApplications()
            } catch (error) {
                console.error('审批失败:', error)
                // 显示更详细的错误信息
                this.$message.error(error.response?.data?.msg || '审批操作失败')
            }
        },
                async searchByClass() {
            if (!this.searchClass) {
                this.$message.warning('请输入班级名称')
                return
            }
            await this.handleSearch(() => getByClassName(this.searchClass))
        },

        async searchByName() {
            if (!this.searchName) {
                this.$message.warning('请输入学生姓名')
                return
            }
            await this.handleSearch(() => getByStudentName(this.searchName))
        },

        async searchByStudentNo() {
            if (!this.searchStudentNo) {
                this.$message.warning('请输入学生学号')
                return
            }
            await this.handleSearch(() => getByStudentNoAndStatus(this.searchStudentNo))
        },
                // 通用查询处理
        async handleSearch(apiCall) {
            this.loading = true
            try {
                const res = await apiCall()
                if (res?.statusCode === 200 && Array.isArray(res.data)) {
                    this.applications = res.data
                } else {
                    this.$message.error(res?.msg || '查询失败')
                }
            } catch (error) {
                this.$message.error(error.message || '查询失败')
            } finally {
                this.loading = false
            }
        },
    

        async searchApproved() {
            await this.handleSearch(getApprovedApplications)
        },
        formatDate(row, column, cellValue) {
            if (cellValue) {
                return new Date(cellValue).toLocaleDateString()
            }
            return ''
        }
    }
}
</script>

<style>
.el-table {
    margin-top: 20px;
    min-height: 400px;
}
</style>