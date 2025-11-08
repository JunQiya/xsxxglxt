<<<<<<< HEAD
<template>
  <!-- 渲染菜单项组件 -->
  <el-submenu v-if="menu.menuType === 'M'" :index="menu.id.toString()">
    <template slot="title">
      <!-- 显示一个图标。如果 menu.icon 没有设置，使用 'iconfeedback' 作为默认图标。 -->
      <i class="iconfont" :class="menu.icon ? menu.icon : 'iconfeedback'" style="font-size: 24px;"></i>
      <!-- 显示菜单的名称 -->
      <span> {{ menu.name }}</span>
    </template>
    <!-- 循环渲染子菜单组件 -->
    <menu-item v-for="item in menu.children" :key="item.id" :menu="item"></menu-item>
  </el-submenu>
  <!-- 渲染菜单项组件 -->
  <el-menu-item :index="menu.path[0] === '/' ? menu.path : '/' + menu.path" v-else @click="to(menu.path)">
    <!-- 显示一个图标。如果 menu.icon 没有设置，使用 'iconfeedback' 作为默认图标。 -->
    <i class="iconfont" :class="menu.icon ? menu.icon : 'iconfeedback'" style="font-size: 24px;"></i>
    <!-- 显示菜单的名称 -->
=======
<!--
 * @Description: 
 * @Author: Rabbiter
 * @Date: 2023-03-05 20:17:11
-->
<template>
  <el-submenu v-if="menu.menuType === 'M'" :index="menu.id.toString()" >
    <template slot="title">
      <i class="iconfont" :class="menu.icon?menu.icon : 'iconfeedback'" style="font-size: 24px;"></i>
      <span> {{ menu.name }}</span>
    </template>
    <menu-item v-for="item in menu.children" :key="item.id" :menu="item"></menu-item>
  </el-submenu>
  <el-menu-item :index="menu.path[0] === '/'?menu.path:'/'+menu.path" v-else @click="to(menu.path)">
    <i class="iconfont" :class="menu.icon?menu.icon : 'iconfeedback'" style="font-size: 24px;"></i>
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    <span slot="title"> {{ menu.name }}</span>
  </el-menu-item>
</template>

<script>
<<<<<<< HEAD
// MenuItem组件，用于渲染侧边栏菜单项
export default {
  name: "MenuItem", // 组件名称为MenuItem
  props: ['menu'], // 接收menu作为prop

  // 定义方法
  methods: {
    // 跳转到指定的URL
    to(url) {
      // 如果URL不是以'/'开头，则添加'/'在前面
      if (url[0] !== '/') url = '/' + url
      // 使用Vue Router的push方法进行路由跳转，如果出错则捕获错误
=======
export default {
  name: "MenuItem",
  props: ['menu'],
  methods: {
    to(url) {
      if (url[0] !== '/') url = '/' + url
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
      this.$router.push(url).catch(err => {
        err
      })
    }
  }
}
</script>

<<<<<<< HEAD
<style scoped></style>
=======
<style scoped>

</style>
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
