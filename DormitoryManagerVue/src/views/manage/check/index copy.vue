<template>
    <div class="router-container">
        <div class="container">
            <div class="inner_container" v-loading="loading">
                <div class="table">
                    <div class="room_selector">
                        <div class="select_item">
                            <span>选择楼栋：</span>
                            <el-cascader :options="bdOptions" v-model="selectedtNode" @change="bdChange"></el-cascader>
                        </div>
                        <div class="select_item">
                            <span>选择宿舍：</span>
                            <el-select v-model="roomValue" clearable @change="handleChange">
                                <el-option v-for="item in roomOptions" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </div>
                    </div>

                </div>
                <div class="table_rate">
                    <!-- 上方两行 -->
                    <div class="rate-item">
                        <span>整体环境评分：</span>
                        <el-rate v-model="score.global_score" :max="10" disabled show-score text-color="#ff9900"
                            :allow-half="true" score-template="{value}分" />
                    </div>
                    <div class="rate-item">
                        <span>建筑设施评分：</span>
                        <el-rate v-model="score.building_score" :max="10" disabled show-score text-color="#ff9900"
                            :allow-half="true" score-template="{value}分" />
                    </div>
                    <!-- 下方两行 -->
                    <div class="rate-item">
                        <span>床铺卧具评分：</span>
                        <el-rate v-model="score.bed_score" :max="10" disabled show-score text-color="#ff9900"
                            :allow-half="true" score-template="{value}分" />
                    </div>
                    <div class="rate-item">
                        <span>室内用品评分：</span>
                        <el-rate v-model="score.indoor_score" :max="10" disabled show-score text-color="#ff9900"
                            :allow-half="true" score-template="{value}分" />
                    </div>
                    <div class="rate-item">
                        <span>综合宿舍评分：</span>
                        <el-rate v-model="score.ce_score" :max="10" disabled show-score text-color="#ff9900"
                            :allow-half="true" score-template="{value}分" />
                    </div>
                </div>
                <div class="img_box">
                    <h1>宿舍照片</h1>
                    <img :src="imgName" alt="暂未照片">
                </div>
            </div>

        </div>
    </div>
</template>

<script>
    import { list } from "@/api/building";
    import { listByBId, saveOrUpdate, scoreByRid } from "@/api/score"

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
                score: { global_score: 0, building_score: 0, bed_score: 0, indoor_score: 0, ce_score: 0 },
                imgName: ''
            }
        },
        methods: {
            async handleChange(value) {
                this.loading = true; // 开启加载状态
                try {
                    const response = await scoreByRid(value)// 异步获取数据

                    if (response.statusCode !== 200) {
                        throw new Error('请求失败,code:${response.statusCode}')
                    }

                    const { data } = response
                    // 解构赋值
                    const {
                        id,
                        room_id,
                        global_score,
                        building_score,
                        bed_score,
                        indoor_score,
                        ce_score,
                        img_name
                    } = data[0];

                    // 其他字段赋值
                    this.score.global_score = global_score;
                    this.score.building_score = building_score;
                    this.score.bed_score = bed_score;
                    this.score.indoor_score = indoor_score;
                    this.score.ce_score = ce_score;
                    this.imgName = process.env.VUE_APP_IMG_PREFIX + img_name;
                } catch (error) {
                    console.error('分数获取失败:', error); // 错误处理
                    this.score = {
                        global_score: 0,
                        building_score: 0,
                        bed_score: 0,
                        indoor_score: 0,
                    }
                    this.imgName = ''
                } finally {
                    this.loading = false; // 无论成功与否都关闭加载状态
                }
            }
            ,
            async bdChange(value) {
                this.loading = true
                const { data } = await listByBId(value[value.length - 1])
                this.roomOptions = this.formatData(data)
                console.log(this.roomOptions)
                this.loading = false
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
            },
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
        flex-direction: column;
        flex: 1.5;
    }

    .router-container {
        height: 100%;
    }

    .inner_container {
        display: flex;
    }

    .room_selector {
        display: flex;
        margin-bottom: 5vh;
    }

    .select_item {
        margin-right: 2.5vw;
        margin-bottom: 2.5vh;
    }

    .rate-item {
        display: flex;
        margin-bottom: 5vh;
        margin-left: 1vw;
        font-size: 20px;
        color: rgb(148, 148, 148);
    }

    .img_box {
        flex: 2;
        height: 70vh;
        min-width: 300px;
    }

    .img_box h1 {
        margin-bottom: 2vh;
    }

    .img_box img {
        width: 100%;
        height: 80%;
        object-fit: contain;
    }

    ::v-deep .el-rate__icon {
        font-size: 2rem !important;
        /* 调整星星大小 */
        margin-right: 5px;
        /* 调整星星间距 */
    }
</style>