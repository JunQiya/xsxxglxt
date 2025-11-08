<template>
<<<<<<< HEAD
    <!-- 使用 Element UI 的 el-breadcrumb 组件创建面包屑导航 -->
    <el-breadcrumb class="app-breadcrumb" separator-class="el-icon-arrow-right">
        <!-- 使用 Vue 的 transition-group 组件管理列表的过渡效果 -->
        <transition-group name="breadcrumb">
            <!-- 使用 v-for 指令遍历 levelList 数组，为每个元素创建一个 el-breadcrumb-item 组件 -->
            <el-breadcrumb-item v-for="(item, index) in levelList" :key="item.path" style="font-size: 22px;">
                <!-- 如果当前元素是第一个，显示一个图标 -->
                <i class="el-icon-a-011" v-if="index == 0"></i>
                <!-- 如果当前元素的 redirect 属性为 'noRedirect' 或者当前元素是最后一个，显示一个 span 元素 -->
                <span v-if="item.redirect === 'noRedirect' ||
                index === levelList.length - 1
                " class="no-redirect"> {{ item.meta.title }}</span>
                <!-- 否则，显示一个链接，点击链接会触发 handleLink 方法 -->
                <a v-else @click.prevent="handleLink(item)"> {{
                item.meta.title
            }}</a>
=======
    <el-breadcrumb class="app-breadcrumb" separator-class="el-icon-arrow-right">
        <transition-group name="breadcrumb">
            <el-breadcrumb-item
                v-for="(item, index) in levelList"
                :key="item.path"
                style="font-size: 22px;"
            >
                <i class="el-icon-a-011" v-if="index == 0"></i>
                <span
                    v-if="
                        item.redirect === 'noRedirect' ||
                        index === levelList.length - 1
                    "
                    class="no-redirect"
                    > {{ item.meta.title }}</span
                >
                <a v-else @click.prevent="handleLink(item)"> {{
                    item.meta.title
                }}</a>
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
            </el-breadcrumb-item>
        </transition-group>
    </el-breadcrumb>
</template>

<script>
export default {
    data() {
        return {
<<<<<<< HEAD
            levelList: null,  // 存储面包屑导航列表
        };
    },
    watch: {
        $route(route) {  // 监听路由变化
            if (route.path.startsWith("/redirect/")) {  // 如果路由路径以"/redirect/"开头则返回
                return;
            }
            this.getBreadcrumb();  // 调用获取面包屑导航方法
        },
    },
    created() {
        this.getBreadcrumb();  // 组件创建时调用获取面包屑导航方法
    },
    methods: {
        getBreadcrumb() {
            // 只显示具有meta.title的路由
=======
            levelList: null,
        };
    },
    watch: {
        $route(route) {
            if (route.path.startsWith("/redirect/")) {
                return;
            }
            this.getBreadcrumb();
        },
    },
    created() {
        this.getBreadcrumb();
    },
    methods: {
        getBreadcrumb() {
            // only show routes with meta.title
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
            let matched = this.$route.matched.filter(
                (item) => item.meta && item.meta.title
            );
            const first = matched[0];

<<<<<<< HEAD
            // 如果不是主页，则添加首页路由
            if (!this.isDashboard(first)) {
                matched = [{ path: "/index", meta: { title: "首页" } }].concat(
=======
            if (!this.isDashboard(first)) {
                matched = [{ path: "/index", meta: { title: "首 页" } }].concat(
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
                    matched
                );
            }

<<<<<<< HEAD
            // 过滤出具有meta.title且meta.breadcrumb不为false的路由，存入levelList
=======
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
            this.levelList = matched.filter(
                (item) =>
                    item.meta &&
                    item.meta.title &&
                    item.meta.breadcrumb !== false
            );
        },
        isDashboard(route) {
            const name = route && route.name;
            if (!name) {
                return false;
            }
            return (
                name.trim().toLocaleLowerCase() === "index".toLocaleLowerCase()
            );
        },
        pathCompile(path) {
            const { params } = this.$route;
<<<<<<< HEAD
            var pathToRegexp = require("path-to-regexp");  // 从外部库引入path-to-regexp
            var toPath = pathToRegexp.compile(path);  // 编译路由路径
            return toPath(params);  // 返回编译后的路径
=======
            var pathToRegexp = require("path-to-regexp");
            var toPath = pathToRegexp.compile(path);
            return toPath(params);
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
        },
        handleLink(item) {
            const { redirect, path } = item;
            if (redirect) {
<<<<<<< HEAD
                this.$router.push(redirect);  // 如果存在重定向路径，则跳转至重定向路径
                return;
            }
            this.$router.push(this.pathCompile(path));  // 否则根据路由路径跳转
=======
                this.$router.push(redirect);
                return;
            }
            this.$router.push(this.pathCompile(path));
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
        },
    },
};
</script>

<style scoped>
.app-breadcrumb {
    display: inline-block;
    font-size: 14px;
    line-height: 40px;
    margin-left: 8px;
}

.no-redirect {
    color: #97a8be;
    cursor: text;
}

.el-breadcrumb__item {
    height: 40px;
}

.breadcrumb-enter-active,
.breadcrumb-leave-active {
    transition: all 0.5s;
}

.breadcrumb-enter,
.breadcrumb-leave-active {
    opacity: 0;
    transform: translateX(20px);
}

.breadcrumb-move {
    transition: all 0.5s;
}

.breadcrumb-leave-active {
    position: absolute;
}
</style>
