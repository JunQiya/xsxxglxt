<template>
    <div class="home">
        <el-row :gutter="70">
            <el-col :span="12">
                <!-- el-card组件 -->
                <el-card class="grid-content">
                    <div class="avatar">
                        <!-- el-avatar组件 -->
                        <el-avatar :size="100" shape="square" :src="icon" v-if="icon !== undefined">
                            <img src="http://123.60.165.61:9142/02.jpg" />
                        </el-avatar>
                        <el-avatar :size="70" v-else @error="errorHandler">{{ name }}</el-avatar>
                    </div>
                    <div class="welcome-content" style="font-size: 22px">
                        <div>欢迎您， {{ name }}</div>
                        <div class="little">
                            我管理的学校/宿舍楼：{{ statistics.building }}
                        </div>
                        <div class="little">
                            宿舍总数： {{ statistics.roomNum }}
                        </div>
                    </div>

                    <img :src="require('@/assets/home.png')" style="width: 100%" />
                </el-card>
            </el-col>
            <!-- 在Home.vue组件中，添加el-col列，占据12列宽度 -->
            <el-col :span="12">
                <!-- 使用element-ui的卡片组件el-card，并设置样式 -->
                <el-card class="grid-content" style="padding-top: 25px">
                    <!-- 居中显示标题 -->
                    <div style="text-align: center; margin-bottom: 15px">
                        <h1>欢迎访问学生宿舍管理系统</h1>
                    </div>
                    <hr />
                    <!-- 水平分割线 -->
                    <div style="margin-top: 15px;font-size: 1vw;">
                        第一条、按时归寝，按时就寝，严格执行宿舍的作息时间。<br /><br />
                        第二条、个人因事、因病要离开宿舍时，必须向寝室长汇报。<br /><br />
                        第三条、寝室成员都要讲究卫生，值日生按时清扫室内卫生，大家养成良好的卫生习惯。<br /><br />
                        第四条、不准私自调换房间、床位。个人贵重物品、现金财物及证件等要妥善保管，任何人未经主人同意不得擅自动用他人物品，宿舍成员离开宿舍必须关好门窗、锁好房门、切断电源。<br /><br />
                        第五条、被褥整洁，物品摆放整齐有序，屋内棚上、墙角无蜘蛛网灰尘，地面床下每天清扫一次，拖一次。床上、窗台、门、角提线无灰尘。<br /><br />
                        第六条、遵守纪律，服从领导，不会客、不留住、不吸烟、不饮酒、不打架、不骂人，团结互助礼貌待人。<br /><br />
                        第七条、宿舍成员间要文明交往、和睦相处、团结一致，互谅互让，互勉互助。<br /><br />
                        第八条、讲文明礼貌，不准随地吐谈、乱丢垃圾、大小便，室内设施不准随便移动拆卸。要保持室内整洁美观，一切日常用具要干净整齐有序，不准往窗外乱丢杂物，垃圾、剩饭(菜)要倒在指定地点。<br /><br />
                        第九条、宿舍内的走道、公共场所，禁止堆放杂物、禁止饲养宠。<br /><br />
                        第十条、注意安全严禁在寝室内用用火，寝室人员时刻要提高防火意识，平时经常检查寝室内，走廊，洗漱间的照明电路是否有破损。
                    </div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <!-- 使用element-ui的卡片组件el-card，并设置样式 -->
                <el-card class="grid-content" style="padding-top: 25px">
                    <!-- 居中显示标题 -->
                    <div style="text-align: center; margin-bottom: 15px">
                        <h2>关于组织开展学生宿舍安全卫生检查的通知</h2>
                    </div>
                    <hr />
                    <!-- 水平分割线 -->
                    <div style="margin-top: 10px;font-size: 1vw;">
                        <div style="font-size: 10px; text-align: center; color: gray; font-weight: lighter;">
                            发布时间：2024-04-06
                            阅读： 13252 次 发布：学生处
                        </div><br /><br />

                        各相关单位：<br>
                        &nbsp;&nbsp;为落实学校常态化疫情防控和校园安全工作要求，营造良好住宿环境，决定开展全校学生宿舍安全及卫生检查。现将有关事项通知如下：<br>
                        &nbsp;&nbsp;一、检查范围<br>
                        &nbsp;&nbsp;全校本科生、研究生宿舍。<br>
                        &nbsp;&nbsp;二、检查内容<br>
                        &nbsp;&nbsp;1.宿舍内是否使用或存放转换插排、电热毯、热得快、电热杯、电饭煲和电磁炉等违规电器及无3C认证的劣质电器;<br>
                        &nbsp;&nbsp;2.是否存在私拉乱接电线、在宿舍楼内停放电动车及充电等现象；<br>
                        &nbsp;&nbsp;3.宿舍内是否使用或存放明火器具（酒精炉、瓦斯炉、蜡烛等），是否存有易燃易爆品（酒精、汽油、瓦斯罐、煤气罐等）、管制刀具等危险物品；<br>
                        &nbsp;&nbsp;4.宿舍内是否有酗酒、吸烟，饲养宠物现象；<br>
                        &nbsp;&nbsp;5.是否存在私调床位、床位出租出借或占用空床位等现象；<br>
                        &nbsp;&nbsp;6.是否有占用、堵塞消防通道现象；<br>
                        &nbsp;&nbsp;7.是否有离开房间时不关闭电源、长明灯、插座放在床铺上或室内无人时长时间充电等不安全用电现象；<br>
                        &nbsp;&nbsp;8.宿舍内是否整洁、卫生、无异味。
                        &nbsp;&nbsp;三、组织实施<br>
                        &nbsp;&nbsp;（一）各学院（中心）自查（1月6-9日）<br>
                        &nbsp;&nbsp;学部、各学院（中心）要认真做好学生教育、引导工作，对照学校相关管理规定，对宿舍消防安全、住宿秩序及宿舍卫生等方面进行自查，及时整改检查中发现的问题。宿舍内的违章电器和明火器具等，学生可自行妥善处理，也可交由各学院（中心）暂时保管。<br>
                        &nbsp;&nbsp;（二）学校全面检查、整改阶段（1月10-18日）<br>
                        &nbsp;&nbsp;成立由学生工作处、保卫处、后勤保障处及学部、各学院（中心）组成的学生宿舍安全卫生检查工作组对学生宿舍进行集中检查。对于检查中出现的继续使用、存放违规物品，违规给电动车充电，出租出借床位及饲养宠物等扰乱正常住宿安全的违规违纪现象，学校将采取“零容忍、严处理”的态度，按照《XXX大学学生违纪处分规定(修订)》（XX大学字〔2023〕47号）进行处理。<br>
                        &nbsp;&nbsp;四、有关要求<br>
                        &nbsp;&nbsp;学部、各学院（中心）要高度重视，对检查情况全面梳理分析，把学生宿舍安全、内务卫生作为学生教育管理的重点工作，教育引导与严格管理相结合，进一步完善各项管理和检查制度，健全安全隐患及内务卫生检查及处理机制。相关部门要对此次检查中出现的问题建立学生宿舍安全隐患台账及责任清单，制定整改措施，明确整改责任人和整改时限，压实管理责任，切实推动平安校园建设。
                        <br><br>
                        附件：<a class="ssxz"
                            href="http://47.108.196.182:8080/edit?name=%E5%AD%A6%E7%94%9F%E5%AE%BF%E8%88%8D%E5%AE%89%E5%85%A8%E5%86%85%E5%8A%A1%E6%A3%80%E6%9F%A5%E6%A0%87%E5%87%86.doc&userName=admin&userId=1"
                            target="_blank">学生宿舍安全内务检查标准
                        </a><a href="https://wxb.lxxno.cn/office/xueshengsusnwujianch.doc" class="ssxz">[下载]</a>
                        <div style="text-align: right;">
                            保卫处<br>
                            学生工作处<br>
                            后勤保障处<br>
                            2022年1月6日<br>
                        </div>

                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
// 导入名为 store 的 Vuex store
import store from "../store";
// 导入名为 get 的来自 "@/api/system/home" 的函数
import { get } from "@/api/system/home";

export default {
    name: "Home",
    // 数据
    data() {
        return {
            statistics: {
                building: undefined,
                roomNum: undefined,
                totalStudent: undefined,
                livingStudent: undefined,
                leaveStudent: undefined,
                unprocessedRepair: undefined,
            },
        };
    },
    // 计算属性
    computed: {
        // 活动菜单
        activeMenu() {
            const route = this.$route;
            const { path } = route;
            return path;
        },
        // 名字
        name() {
            return store.state.userName;
        },
        // 图标
        icon() {
            return store.state.userIcon;
        },
    },
    // 方法
    methods: {
        // 异步获取数据
        async get() {
            const { data } = await get();
            this.statistics = data;
        },
    },
    // 创建时执行
    created() {
        this.get();
    },
};
</script>

<style scoped>
/* 在这个部分设置了一些样式，使用了 scoped 属性确保样式只作用于当前组件 */

/* 定义了.avatar类的样式 */
.avatar {
    margin: 20px 20px;
    float: left;
}

/* 定义了.home类的样式 */
.home {
    padding: 20px;
}

/* 定义了.welcome-content类的样式 */
.welcome-content {
    /* 注释掉了float属性 */
    margin: 30px 0;
}

/* 定义了.grid-content类的样式 */
.grid-content {
    margin: 10px 0;
    position: relative;
}

/* 定义了.fixed类的样式并使用了:after伪元素 */
.fixed:after {
    content: "";
    clear: both;
    display: block;
    visibility: hidden;
    height: 0;
    width: 0;
}

/* 定义了.little类的样式 */
.little {
    font-size: 16px;
    color: #909399;
    margin-top: 8px;
}

/* 定义了.mid类的样式 */
.mid {
    text-align: center;
}
</style>