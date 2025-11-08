<!--
 * @Description: 
 * @Author: Rabbiter
 * @Date: 2023-03-05 20:17:11
-->
<template>
  <div class="icon-body">
<<<<<<< HEAD
    <!-- 输入框，用于搜索图标名称 -->
=======
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    <el-input v-model="name" style="position: relative;"
              clearable placeholder="请输入图标名称"
              @clear="filterIcons" @input.native="filterIcons">
      <i slot="suffix" class="el-icon-search el-input__icon"/>
    </el-input>
<<<<<<< HEAD
    <!-- 图标列表 -->
    <div class="icon-list">
      <!-- 遍历图标列表，显示图标并绑定点击事件 -->
=======
    <div class="icon-list">
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
      <div v-for="(item, index) in iconList" :key="index" @click="selectedIcon(item.font_class)" style="margin: 5px;">
        <i class="iconfont" :class="'icon-' + item.font_class" style="font-size: 30px;"></i>
        <span>{{ item.name }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import {glyphs} from '@/assets/font2/iconfont.json'

<<<<<<< HEAD
// 导出默认的对象
export default {
  // 组件的名称为IconSelect
  name: 'IconSelect',
  // 数据
  data() {
    return {
      // 图标名称
      name: '',
      // 图标列表
      iconList: glyphs
    }
  },
  // 方法
  methods: {
    // 过滤图标
=======
export default {
  name: 'IconSelect',
  data() {
    return {
      name: '',
      iconList: glyphs
    }
  },
  methods: {
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    filterIcons() {
      this.iconList = glyphs
      if (this.name) {
        this.iconList = this.iconList.filter(item => item.font_class.includes(this.name))
      }
    },
<<<<<<< HEAD
    // 选择图标
=======
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
    selectedIcon(name) {
      this.$emit('selected', 'icon-' + name)
      document.body.click()
    }
  },
<<<<<<< HEAD
  // 创建时
=======
>>>>>>> e6897d3eee7dd92889ec4638067e9f9148ca1f07
  created() {

  }
}
</script>

<style scoped>
.icon-body {
  padding: 10px;
}

.icon-list {
  height: 200px;
  overflow-y: scroll;
}

.icon-list div {
  height: 30px;
  line-height: 30px;
  margin-bottom: -5px;
  cursor: pointer;
  width: 33%;
  float: left;
}

.icon-list span {
  display: inline-block;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>
