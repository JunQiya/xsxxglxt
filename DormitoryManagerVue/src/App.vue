<template>
    <div id="app">
        <div class="pull-left" :class="isCollapse ? 'nav-collapse' : 'nav'">
            <!-- 侧边导航栏 -->
            <el-menu :default-active="activeMenu" class="nav" :collapse="isCollapse" unique-opened>
                <el-row>
                    <el-col :span="5">
                        <!-- 点击logo返回首页 -->
                        <img :class="isCollapse ? 'logo-collapse' : 'logo'" src="./assets/logo.png" alt=""
                            @click="to('/')" />
                    </el-col>
                    <el-col :span="19" v-if="!isCollapse"
                        style="margin-top: 15px;color: rgb(64,64,64);font-size: 24px;font-weight: 600;">
                        宿舍管理系统
                    </el-col>
                </el-row>
                <!-- 首页菜单项 -->
                <el-menu-item index="/index" @click="to('/index')">
                    <i class="iconfont icon-r-home"></i>
                    <span slot="title"> 首页</span>
                </el-menu-item>
                <!-- 学生宿舍管理和系统管理 -->
                <menu-item :menu="item" :key="item.id" v-for="item in menu" />
                <!-- 学生综合服务平台 -->
                <el-submenu index="#">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>综合服务平台</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="/index" @click="tob">后勤管理</el-menu-item>
                        <el-menu-item index="/index" @click="to_zlgl">资料管理</el-menu-item>
                        <el-menu-item index="/index" @click="tob">问卷调查</el-menu-item>
                        <el-menu-item index="/index" @click="tob">网络投票</el-menu-item>
                        <el-menu-item index="/index" @click="toa">学生宿舍查寝</el-menu-item>
                        <el-menu-item index="/index" @click="to_zdb">学生走读申请表</el-menu-item>
                        <el-menu-item index="/index" @click="tob">勤工助学申请</el-menu-item>
                        <el-menu-item index="1-2" @click="to_bxd">学生宿舍报修申请表</el-menu-item>

                    </el-menu-item-group>
                </el-submenu>
            </el-menu>
        </div>

        <!-- 主体部分 -->
        <div :class="isCollapse ? 'main-collapse' : 'main'">
            <!-- 顶部导航栏 -->
            <div :class="isCollapse ? 'top-bar-collapse' : 'top-bar'" style="border-bottom: 1px solid lightgrey;">
                <el-header>
                    <div class="left">
                        <!-- 控制侧边栏展开收起 -->
                        <span @click="isCollapse = !isCollapse">
                            <i class="el-icon-s-fold" v-if="!isCollapse"></i>
                            <i class="el-icon-s-unfold" v-else></i>
                        </span>
                    </div>
                    <div class="right">
                        <!-- 邮件提醒和用户信息 -->
                        <div class="mail">
                            <el-badge :value="count" :max="99" class="item">
                                <i class="el-icon-message" @click="showMailDialog"></i>
                            </el-badge>
                        </div>
                        <div class="icon">
                            <el-avatar :src="icon" v-if="icon" @error="errorHandler" :size="50">
                                <img src="http://localhost:9142/02.jpg" />
                            </el-avatar>
                            <el-avatar v-else> {{ name }}</el-avatar>
                        </div>
                        <!-- 用户下拉菜单 -->
                        <el-dropdown @command="handleCommand">
                            <span class="el-dropdown-link name">
                                {{ name
                                }}<i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item command="setting">
                                    <span style="display: block">个人中心</span>
                                </el-dropdown-item>
                                <el-dropdown-item command="change">
                                    <span style="display: block">修改密码</span>
                                </el-dropdown-item>
                                <el-dropdown-item command="logout">
                                    <span style="display: block">注销</span>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </el-header>
            </div>
            <!-- 路由展示区域 -->
            <div class="router">
                <div class="breadcrumb">
                    <breadcrumb />
                </div>
                <transition name="fade-transform" mode="out-in">
                    <router-view />
                </transition>
            </div>
        </div>

        <!-- 消息提示框 -->
        <div class="dialog">
            <el-dialog :visible.sync="dialogVisible" @closed="handleClose" title="消息" :fullscreen="true">
                <message :change="change"></message>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import store from "./store";
import Message from "./components/Message"; //消息页面
import MenuItem from "./components/MenuItems";
import { logout } from "./api/login";
import { removeToken } from "./utils/auth";
import Breadcrumb from "@/components/Breadcrumb";

// Vue组件导出
export default {
    data() {
        // 数据
        return {
            change: 0,
            isCollapse: false,
            dialogVisible: false,
        };
    },
    components: {
        // 组件
        MenuItem,
        Breadcrumb,
        Message,
    },
    methods: {
        // 错误处理函数
        errorHandler() {
            this.icon = "http://localhost:9142/02.jpg";
            console.log(111);
        },
        // 显示邮件对话框
        showMailDialog() {
            this.change = 1;
            this.dialogVisible = true;
        },
        // 关闭对话框
        handleClose() {
            this.change = 0;
            this.dialogVisible = false;
        },
        // 跳转页面
        to(url) {
            this.$router.push(url).catch((err) => {
                err;
            });

        },

        // 打开新窗口
        toa() {
            window.open("http://47.108.196.182:8080/edit?name=%E5%AD%A6%E7%94%9F%E5%AE%BF%E8%88%8D%E6%9F%A5%E5%AF%9D%E8%A1%A8.xlsx&userName=admin&userId=1");
        },
        // 跳转资料管理页面
        to_zlgl() {
            window.open("http://47.108.196.182:8099/dist/");    //资料管理
        },
        // 打开保修单页面
        to_bxd() {
            window.open("http://47.108.196.182:8080/edit?name=%E5%AE%BF%E8%88%8D%E6%8A%A5%E4%BF%AE%E7%99%BB%E8%AE%B0(%E7%94%B3%E8%AF%B7)%E8%A1%A8.xlsx&userName=admin&userId=1");
        },
        // 打开走读表页面
        to_zdb() {
            window.open("http://47.108.196.182:8080/edit?name=%E5%AD%A6%E7%94%9F%E8%B5%B0%E8%AF%BB%E7%94%B3%E8%AF%B7%E8%A1%A8.docx&userName=admin&userId=1");
        },
        // 处理命令
        handleCommand(command) {
            if (command === "logout") {
                this.logout();
            } else if (command === "setting") {
                this.to("/personal/info");
            } else if (command === "change") {
                this.to("/personal/change_password");
            }
        },
        // 异步注销
        async logout() {
            await logout();
            await removeToken();
            window.location.href = process.env.VUE_APP_SSO;
        },
    },
    computed: {
        // 计算属性 - 活跃菜单
        activeMenu() {
            const route = this.$route;
            const { path } = route;
            return path;
        },
        // 计算属性 - 未读消息数
        count() {
            return store.state.countUnread;
        },
        // 计算属性 - 菜单
        menu() {
            return store.state.menu;
        },
        // 计算属性 - 用户名
        name() {
            return store.state.userName;
        },
        // 计算属性 - 用户图标
        icon() {
            return store.state.userIcon;
        },
    },
};
</script>
<style>
/* 定义页面元素的过渡动画样式 */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.3s;
}

/* 定义页面元素进入时的样式 */
.fade-enter,
.fade-leave-active {
    opacity: 0;
}

/* 定义页面元素离开时的样式 */
/* 使用 fade-transform 类名的过渡动画样式 */
.fade-transform-leave-active,
.fade-transform-enter-active {
    transition: all 0.5s;
}

/* 定义页面元素进入时的样式，包括透明度和位置变换 */
.fade-transform-enter {
    opacity: 0;
    transform: translateX(-30px);
}

/* 定义页面元素离开时的样式，包括透明度和位置变换 */
.fade-transform-leave-to {
    opacity: 0;
    transform: translateX(30px);
}

/* HTML 标签样式 */
html {
    height: 100%;
    overflow-x: hidden;
}

/* body 标签样式 */
body {
    height: 100%;
    background-color: #f0f7f9;
}

/* 主要内容区域样式 */
.main {
    height: 100%;
    margin-left: 200px;
}

/* 主要内容区域折叠后的样式 */
.main-collapse {
    height: 100%;
    margin-left: 64px;
}

/* 应用根元素样式 */
#app {
    height: 100%;
}

/* 通用样式重置 */
* {
    margin: 0;
}

/* 导航栏样式 */
.nav {
    width: 200px;
    height: 100%;
}

/* 导航栏折叠后的样式 */
.nav-collapse {
    width: 64px;
    height: 100%;
}

/* Logo 样式 */
.logo {
    width: 30px;
    height: 30px;
    margin: 15px 0 0 5px;
    cursor: pointer;
}

/* Logo 折叠后的样式 */
.logo-collapse {
    width: 24px;
    height: 24px;
    margin: 20px 15px;
    cursor: pointer;
}

/* 顶部栏样式 */
.top-bar {
    background-color: white;
    position: fixed;
    left: 201px;
    right: 0;
    z-index: 100;
    height: 70px;
}

/* 折叠后的顶部栏样式 */
.top-bar-collapse {
    background-color: white;
    position: fixed;
    left: 65px;
    right: 0;
    z-index: 100;
}

/* 左侧区域样式 */
.left {
    float: left;
    line-height: 85px;
}

/* 左侧区域中的图标样式 */
.left span i {
    font-size: 25px;
    color: #656565;
    cursor: pointer;
}

/* 右侧区域样式 */
.right {
    float: right;
    position: relative;
}

/* 右侧区域中的图标和名称样式 */
.icon,
.name {
    float: left;
}

/* 右侧区域中的姓名样式 */
.name {
    line-height: 75px;
    color: #2399f1;
    cursor: pointer;
    font-size: 13px;
}

/* 右侧区域中的图标样式 */
.icon {
    margin: 10px;
}

/* 面包屑样式 */
.breadcrumb {
    background-color: rgb(255, 255, 255);
    padding-left: 20px;
    margin-top: 13px;
}

/* 内容容器样式 */
.container {
    padding: 20px;
}

/* 内部容器样式 */
.inner_container {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 左侧悬浮区域样式 */
.pull-left {
    position: fixed;
    left: 0;
    top: 0;
}

/* 路由区域样式 */
.router {
    padding-top: 60px;
}

/* 邮件图标样式 */
.mail {
    float: left;
    height: 60px;
    font-size: 22px;
    cursor: pointer;
    position: absolute;
    left: -30px;
    top: 24px;
}

/* 项目悬停时的样式 */
.item:hover {
    color: #409eff;
}

/* 项目点击时的样式 */
.item:active {
    color: #000000;
}
</style>