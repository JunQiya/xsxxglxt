<template>
    <div class="router-container">
        <div class="container">
            <div class="inner_container" v-loading="loading">
                <!-- 搜索筛选区域 -->
                <el-form :inline="true" ref="queryForm" label-width="80px">
                    <el-form-item label="宿舍楼栋">
                        <!-- <el-cascader :options="bdOptions" v-model="selectedNode" @change="bdChange"
                            clearable></el-cascader> -->
                        <el-cascader :options="bdOptions" v-model="selectedNode" @change="bdChange"></el-cascader>
                    </el-form-item>
                    <el-form-item label="宿舍号">
                        <!-- <el-input v-model="listQuery.number" placeholder="输入宿舍号" clearable></el-input> -->
                        <el-input v-model="listQuery.number" @keyup.enter="handleSearch"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleSearch">查询</el-button>
                        <el-button @click="resetQuery">重置</el-button>
                    </el-form-item>
                </el-form>

                <!-- 数据表格区域 -->
                <el-table :data="tableData" border height="700">
                    <el-table-column label="序号" width="80">
                        <template slot-scope="scope">
                            {{ (listQuery.page - 1) * listQuery.rows + scope.$index + 1 }}
                        </template>
                    </el-table-column>
                    <!-- <el-table-column prop="building.name" label="所属宿舍楼" show-overflow-tooltip></el-table-column> -->
                    <el-table-column label="所属宿舍楼" show-overflow-tooltip>
                        <template slot-scope="{row}">
                            {{ buildingMap.get(row.building.id) || row.building.name }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="room.number" label="宿舍号" width="120"></el-table-column>
                    <el-table-column prop="ce_score" label="综合评分" width="280">
                        <template slot-scope="{row}">
                            <el-rate v-model="row.ce_score" :max="10" disabled :allow-half="true" />
                        </template>
                    </el-table-column>
                    <el-table-column prop="rated_at" label="检查时间" width="180"></el-table-column>
                    <el-table-column label="操作" width="120" fixed="right">
                        <template slot-scope="scope">
                            <el-button type="text" @click="showDetail(scope.row)">详情</el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- 分页组件 -->
                <el-pagination :current-page.sync="listQuery.page" :page-sizes="[10, 20, 30]"
                    :page-size.sync="listQuery.rows" :total="total" @size-change="handleSizeChange"
                    @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper">
                </el-pagination>

                <!-- 评分详情弹窗 -->
                <el-dialog :title="currentRoom" :visible.sync="dialogVisible" width="60%">
                    <div class="dialog-content">
                        <div class="score-section">
                            <!-- 复用原有评分组件 -->
                            <div class="rate-item" v-for="(score, key) in scoreLabels" :key="key">
                                <span>{{ score.label }}：</span>
                                <el-rate v-model="currentScores[key]" :max="10" disabled :allow-half="true" show-score
                                    text-color="#ff9900" score-template="{value}分" />
                            </div>
                        </div>
                        <div class="image-section">
                            <h3>宿舍照片</h3>
                            <img :src="currentImage" alt="宿舍照片" class="room-image">
                        </div>
                    </div>
                </el-dialog>
            </div>
        </div>
    </div>
</template>

<script>
    import { list } from "@/api/building";
    import { listRoomsWithScores, scoreByRid } from "@/api/score"; // 假设新增的接口listRoomsWithScores

    export default {
        data() {
            return {
                buildingMap: new Map(),
                listQuery: {
                    page: 1,
                    rows: 10,
                    buildingId: null,
                    number: ''
                },
                tableData: [],
                total: 0,
                // 弹窗相关数据
                dialogVisible: false,
                currentScores: {},
                currentImage: '',
                scoreLabels: {
                    global_score: { label: '整体环境' },
                    building_score: { label: '建筑设施' },
                    bed_score: { label: '床铺卧具' },
                    indoor_score: { label: '室内用品' },
                    ce_score: { label: '综合评分' }
                },
                // 楼栋选择相关
                bdOptions: [],
                selectedNode: []
            }
        },
        computed: {
            currentRoom() {
                return this.currentScores.building && this.currentScores.room
                    ? `${this.currentScores.building.name} - ${this.currentScores.room.number}`
                    : '宿舍详情';
            }
        },
        methods: {
            // 获取宿舍列表数据
            async fetchData() {
                this.loading = true;
                try {
                    const res = await listRoomsWithScores(
                        this.listQuery.page,
                        this.listQuery.rows,
                        this.listQuery.buildingId,
                        this.listQuery.number
                    );

                    this.tableData = res.content;
                    this.total = res.totalElements;

                    console.log(this.tableData)

                    // 同步分页状态（防止后端修正参数）
                    this.listQuery.page = res.pageNumber;
                    this.listQuery.rows = res.pageSize;
                } catch (error) {
                    console.error('数据加载失败:', error);
                } finally {
                    this.loading = false;
                }
                this.loading = false
            },
            // 显示详情弹窗
            async showDetail(row) {

                try {
                    const res = await scoreByRid(row.room_id);
                    const imgName = res.data[0].img_name;
                    this.$set(row, 'img_name', imgName);

                    this.currentScores = { ...row };
                    this.currentImage = process.env.VUE_APP_IMG_PREFIX + imgName;
                    this.dialogVisible = true;
                } catch (error) {
                    console.error("获取图片失败",error)
                }
            },

            // 分页相关方法
            handleSizeChange(val) {
                this.listQuery.rows = val;
                this.fetchData();
            },
            handleCurrentChange(val) {
                this.listQuery.page = val;
                this.fetchData();
            },
            // 搜索相关
            handleSearch() {
                this.listQuery.page = 1;
                this.fetchData();
                console.log(this.listQuery.buildingId)
            },
            resetQuery() {
                this.listQuery = {
                    page: 1,
                    rows: 10,
                    buildingId: null,
                    number: ''
                };
                this.selectedNode = [];
                this.fetchData();
            },
            // 格式化数据
            // formatData(list) {
            //     return list.map(item => {
            //         const node = {
            //             value: item.id,
            //             label: item.number || item.name
            //         };
            //         // 仅当存在子节点且为数组时，递归格式化子节点
            //         if (Array.isArray(item.children) && item.children.length > 0) {
            //             node.children = this.formatData(item.children);
            //         }

            //         return node;
            //     });
            // },
            formatData(list) {
                return list.map(item => ({
                    value: item.id,
                    label: item.name,
                    children: item.children?.length ? this.formatData(item.children) : null
                }))
            },
            // 楼栋选择相关方法（保持原有逻辑）
            async bdChange(value) {
                this.listQuery.buildingId = value?.length > 0 ? value[value.length - 1] : null
                this.handleSearch()
            },
            async listData() {
                this.loading = true
                const { data } = await list()
                console.log(this.formatData(data))
                this.bdOptions = this.formatData(data)
                this.buildPathMap(data)
                this.loading = false
            },
            buildPathMap(nodes, parentPath = '') {
                nodes.forEach(node => {
                    const currentPath = parentPath ? `${parentPath}/${node.name}` : node.name
                    this.buildingMap.set(node.id, currentPath)
                    if (node.children?.length) {
                        this.buildPathMap(node.children, currentPath)
                    }
                })
            }
        },
        async created() {
            await this.listData();
            await this.fetchData();
        }
    }
</script>

<style scoped>
    .dialog-content {
        display: flex;
        gap: 30px;
    }

    .score-section {
        flex: 1;
    }

    .image-section {
        flex: 1;
    }

    .room-image {
        width: 100%;
        max-height: 300px;
        object-fit: contain;
        border-radius: 4px;
    }

    .rate-item {
        margin: 15px 0;
        font-size: 16px;
    }
</style>