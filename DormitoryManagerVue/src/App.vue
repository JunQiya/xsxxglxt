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
                        <svg t="1712739068920" class="icon-zhfwtp" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/2000/svg" p-id="3375" width="24" height="24">
                            <path
                                d="M927.552 540.16c1.408 6.634667 2.090667 13.205333 2.090667 20.010667 0 9.045333-1.728 18.048-5.034667 26.773333a76.010667 76.010667 0 0 1-14.954667 24.725333l-0.661333 0.64-0.533333 0.682667c-3.648 3.690667-6.933333 6.976-9.898667 9.749333l-6.464 6.464c-2.965333 3.114667-5.888 5.738667-8.32 8.170667l-0.384 0.384-29.568 30.421333-91.306667 94.293334-34.133333 35.498666-0.384 0.362667-0.170667 0.170667a1305.664 1305.664 0 0 0-30.08 30.954666l-22.784 23.658667a234.026667 234.026667 0 0 1-13.226666 12.885333c-3.264 3.114667-7.104 6.250667-11.136 9.578667-6.101333 4.693333-12.522667 8.874667-19.477334 12.010667-5.205333 2.602667-11.989333 5.546667-20.672 8.853333-8.192 3.114667-16.704 6.101333-25.728 9.216-9.621333 3.114667-18.112 5.76-26.090666 7.829333-6.805333 1.92-13.930667 3.669333-20.885334 4.885334l-0.896 0.192h-0.896a85.888 85.888 0 0 1-9.386666 0.469333 53.376 53.376 0 0 1-43.285334-19.818667 62.656 62.656 0 0 1-10.944-49.408 184.533333 184.533333 0 0 1 5.205334-23.616c2.24-7.829333 4.501333-16.021333 6.954666-24.533333 2.453333-9.045333 4.885333-17.194667 7.296-24.533333a126.933333 126.933333 0 0 1 7.658667-19.626667c2.261333-5.546667 5.034667-10.965333 8.362667-15.978667 4.181333-6.464 9.045333-12.565333 14.464-17.941333l1.365333-1.6 10.261333-10.752c6.442667-6.762667 14.442667-14.954667 23.808-24.896l31.530667-31.808 34.218667-35.669333 91.669333-94.272 2.090667-2.261334-1.045334-1.024 26.090667-24.853333 2.453333-2.453333 3.477334-3.306667 11.818666-11.306667c4.352-4.352 9.066667-8.512 13.76-12.693333l0.362667-0.362667 0.490667-0.341333a77.866667 77.866667 0 0 1 49.429333-17.770667h2.965333c7.616 0.362667 15.104 1.578667 22.421334 3.84 5.909333 1.92 11.648 4.16 17.045333 6.976a117.589333 117.589333 0 0 1 31.317333 25.898667c9.770667 10.773333 17.92 22.976 24.021334 36.330667l0.170666 0.170666 0.192 0.362667c2.410667 5.909333 4.16 11.84 5.397334 18.090667v0.277333h-0.042667z m-40.192 19.648c0-3.626667-0.405333-7.274667-1.066667-10.944a40.768 40.768 0 0 0-2.794666-9.237333 92.970667 92.970667 0 0 0-16.874667-25.194667 75.328 75.328 0 0 0-19.669333-16.874667 71.232 71.232 0 0 0-10.069334-4.010666 44.458667 44.458667 0 0 0-11.861333-1.898667h-1.216a37.098667 37.098667 0 0 0-23.616 8.533333c-3.136 2.602667-6.954667 6.272-11.648 10.773334a386.133333 386.133333 0 0 0-12.16 11.648l79.637333 82.133333c1.92-1.749333 4.16-4.010667 6.272-6.08l7.104-7.146667c2.624-2.602667 5.76-5.738667 9.066667-9.216 2.965333-3.136 5.44-6.954667 6.784-10.965333 1.429333-3.690667 2.112-7.488 2.112-11.52z m-63.338667 77.781333l-79.658666-82.410666-91.456 94.442666-34.432 35.477334-30.954667 32.021333c-9.386667 9.536-17.194667 17.749333-23.488 24.362667-6.272 6.613333-10.261333 10.752-12.010667 12.522666a70.464 70.464 0 0 0-9.045333 11.114667 71.957333 71.957333 0 0 0-5.738667 11.285333 80.64 80.64 0 0 0-5.568 14.442667c-2.282667 6.976-4.565333 14.613333-6.976 22.976-2.453333 8.341333-4.736 16.32-6.976 24.192-1.557333 5.888-2.944 11.818667-4.010666 17.728-1.386667 5.546667-0.512 11.306667 2.261333 16.192 2.773333 2.794667 6.784 4.16 10.602667 3.690667 1.557333 0 3.114667 0 4.693333-0.213334a197.546667 197.546667 0 0 0 16.704-3.989333c7.616-2.090667 15.637333-4.522667 24-7.274667 8.341333-2.837333 16.32-5.589333 24-8.533333a158.72 158.72 0 0 0 17.386667-7.296 56.533333 56.533333 0 0 0 11.456-7.146667c3.498667-2.624 6.784-5.397333 9.92-8.298666 3.648-3.328 7.125333-6.826667 10.56-10.474667 5.930667-6.08 13.610667-13.909333 22.656-23.509333 9.066667-9.557333 19.306667-20.010667 30.634666-31.466667l34.090667-35.136-0.213333-0.192 91.562666-94.506667z"
                                fill="#909399" p-id="3376"></path>
                            <path
                                d="M904.064 209.024v154.261333c0 15.296-11.669333 27.84-25.962667 27.84-14.4 0-26.048-12.522667-26.048-27.84V209.024c0.469333-30.954667-22.528-56.362667-51.328-56.896h-546.986666c-28.821333 0.512-51.648 25.898667-51.349334 56.896v605.952c-0.341333 30.954667 22.528 56.362667 51.328 56.874667h164.522667c14.250667 0.362667 25.6 12.693333 26.069333 27.989333 0.469333 15.850667-11.178667 29.034667-26.069333 29.589333h-164.501333c-57.962667-1.066667-104.106667-52.16-103.317334-114.432V209.024c-0.810667-62.272 45.333333-113.408 103.317334-114.453333h547.029333c57.962667 1.024 104.085333 52.16 103.296 114.453333z"
                                fill="#909399" p-id="3377"></path>
                            <path
                                d="M742.058667 282.602667a28.096 28.096 0 0 1-27.84 28.693333H316.437333a28.650667 28.650667 0 0 1-27.989333-27.84 28.864 28.864 0 0 1 27.989333-29.738667h397.824c15.658667 0.341333 28.138667 13.226667 27.797334 28.885334zM618.944 433.557333c0.490667 16-12.010667 29.226667-28.010667 29.738667H316.437333a28.8 28.8 0 0 1 0-57.578667h274.474667a28.8 28.8 0 0 1 28.032 27.84zM500.330667 595.989333a28.8 28.8 0 0 1-28.010667 29.589334h-155.861333a28.906667 28.906667 0 0 1-27.989334-27.818667 29.056 29.056 0 0 1 27.989334-29.781333h155.861333a29.013333 29.013333 0 0 1 28.010667 28.010666z"
                                fill="#909399" p-id="3378"></path>
                        </svg>
                        <span>综合服务平台</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item index="/index" @click="tob">
                            <i>

                            </i> 后勤管理</el-menu-item>
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
            // this.$router.push('/zlgl');
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

.icon-zhfwtp {
    margin-right: 10px;
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

/* 下载 */
.ssxz {
    text-decoration: none;
    color: DeepSkyBlue;
}

.ssxz:hover {
    color: rgb(23, 241, 212);
}
</style>