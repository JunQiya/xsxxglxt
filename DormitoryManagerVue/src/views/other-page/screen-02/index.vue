<template>
  <div class="fullscreen-container">
    <dv-border-box-11 title="滇西科技师范学院" class="dashboard-container">

      <!-- 主体内容 -->
      <div class="main-content">
        <!-- 左侧区域 -->
        <div class="left-section">
          <dv-border-box-11 class="notice-container" backgroundColor="rgba(28, 43, 75,0.5)" title="最新公告"
            style="height: 100%;">
            <div class="notice-content">
              <div v-for="(item, index) in noticeConfig.data" :key="index">
                <div class="notice-row">
                  <span class="notice-label notice-title">发布人员：</span>
                  <span class="notice-value notice-title">{{ item.user.realName }}</span>
                </div>
                <div class="notice-row">
                  <span class="notice-label notice-title">发布时间：</span>
                  <span class="notice-value notice-title">{{ formatTime(item.time) }}</span>
                </div>
                <dv-border-box-5>
                  <div class="notice-textarea">
                    {{ item.msg }}
                  </div>
                </dv-border-box-5>
              </div>
              <!-- 空状态提示 -->
              <div v-if="!noticeConfig.data.length" class="empty-tip">
                {{ loading ? '数据加载中...' : '暂无最新公告' }}
              </div>
            </div>
          </dv-border-box-11>
          <dv-border-box-8 backgroundColor="rgba(28, 43, 75,0.5)" style="height: 100%;">
            <div class="pie-chart-container" ref="studentPie"></div>
          </dv-border-box-8>
        </div>

        <!-- 中间区域 -->
        <div class="center-section">
          <dv-border-box-8 style="height: 100%;" :color="['transparent', 'transparent']">
            <!-- <img src="../../../assets/building.png" alt="error">
            <div v-for="(annotation, index) in annotations" :key="index" class="annotation" :style="{
          top: annotation.y + 'px',
          left: annotation.x + 'px',
          flexDirection: annotation.align === 'left' ? 'row-reverse' : 'row', // 动态调整布局方向
        }">
              
              <div class="dot" :class="{ 'blinking': annotation.blinking }"></div>
              
              <div class="text-box">{{ annotation.text }}</div>
            </div> -->
          </dv-border-box-8>
          <dv-border-box-8 backgroundColor="rgba(28, 43, 75,0.5)" style="height: 100%;">
            <div id="stackedLine"></div>
          </dv-border-box-8>
        </div>

        <!-- 右侧区域 -->
        <div class="right-section">
          <dv-border-box-8 backgroundColor="rgba(28, 43, 75,0.5)" style="height: 100%;">
            <dv-scroll-board :key="scrollBoardKey" :config="scrollBoardConfig"
              style="width:calc(100% - 20px);height:calc(100% - 20px);margin:10px">
              <div v-if="scrollBoardConfig.data.length === 0" class="empty-tip">
                {{ loading ? '数据加载中...' : '暂无请假记录' }}
              </div>
            </dv-scroll-board>
          </dv-border-box-8>
          <dv-border-box-8 backgroundColor="rgba(28, 43, 75,0.5)" style="height: 100%;">
            <div class="pie-chart-container" ref="regMonthPie"></div>
          </dv-border-box-8>
        </div>
      </div>

    </dv-border-box-11>
    <div class="float-button" @click="handleBack">
      <div class="button-content">
        <i class="el-icon-back">
          返回
        </i>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import dataV from '@jiaminghi/data-view'
  import * as echarts from 'echarts'
  import dayjs from 'dayjs'
  import AnimatedNumber from 'vue-animated-number';
  import { getNew } from "@/api/notice"
  import { stats, getScoreTrend } from "@/api/score"
  import { all, countByFaculty, countByRegistrationMonth } from "@/api/student"
  import { selectByTime } from "@/api/leave"

  Vue.component('animated-number', AnimatedNumber);
  Vue.use(dataV)


  export default {
    data() {
      return {
        scrollBoardKey: 0,
        loading: false,
        listQuery: {
          page: 1,
          rows: 99999,
          entity: {},
        },
        noticeConfig: {
          data: []
        },
        scrollBoardConfig: {
          header: ['姓名', '班级', '房间号'],
          data: [],
          index: false,
          columnWidth: [150],
          align: ['center']
        },
        facultyData: [],
        regMonthData: [],
        colors: ['#4FFA6E', '#FFC233', '#6E8BFF', '#FF7A45', '#A36EFF'],
        // annotations: [
        //   {
        //     x: 55,
        //     y: 80,
        //     text: '这一楼有多少人住',
        //     blinking: true,
        //     align: 'left', // 文字框在红点左侧
        //   },
        //   {
        //     x: 405,
        //     y: 150,
        //     text: '这一楼有多少人住',
        //     blinking: true,
        //     align: 'right', // 文字框在红点右侧
        //   },
        //   {
        //     x: 50,
        //     y: 280,
        //     text: '这一楼有多少人住',
        //     blinking: true,
        //     align: 'left', // 文字框在红点左侧
        //   },
        //   {
        //     x: 400,
        //     y: 300,
        //     text: '这一楼有多少人住',
        //     blinking: true,
        //     align: 'right', // 文字框在红点右侧
        //   },
        //   {
        //     x: 50,
        //     y: 400,
        //     text: '这一楼有多少人住',
        //     blinking: true,
        //     align: 'left', // 文字框在红点左侧
        //   },
        //   {
        //     x: 400,
        //     y: 380,
        //     text: '这一楼有多少人住',
        //     blinking: true,
        //     align: 'right', // 文字框在红点右侧
        //   }
        // ]
      }
    },
    methods: {
      handleBack() {
        this.$router.push('/');
        // 或使用命名路由跳转（需与路由配置一致）
        // this.$router.push({ name: 'Home' });
      },
      async listData() {
        try {
          this.loading = true;
          this.scrollData()
          this.noticeData()
          this.fetchFacultyData()

        } finally {
          this.loading = false;
        }
      },
      async fetchFacultyData() {
        const { data } = await countByFaculty()
        this.facultyData = data.map((item, index) => ({
          value: item.count,
          name: item.faculty?.name,
          itemStyle: {
            color: this.colors[index % this.colors.length] // 循环使用色系
          }
        }))
      },
      async fetchRegMonth() {
        const { data } = await countByRegistrationMonth()
        this.regMonthData = data.map((item, index) => ({
          value: item.count,
          name: item.registrationMonth,
          itemStyle: {
            color: this.colors[index % this.colors.length] // 循环使用色系
          }
        }))
      },
      async noticeData() {
        const { data } = await getNew()
        this.noticeConfig.data = data
      },
      processChartData(apiData) {
        // 按月份排序（确保时间顺序正确）
        const sortedData = apiData.sort((a, b) =>
          dayjs(a.month).valueOf() - dayjs(b.month).valueOf()
        );

        const categories = sortedData.map(item => item.month);
        const series = [{
          name: '请假数量',
          data: sortedData.map(item => item.count), // 改为count字段
          type: 'line',
          smooth: true
        }]
        this.updateChartConfig(categories, series);
      },
      updateChartConfig(categories, series) {
        const option = {
          title: {
            text: '每月请假数量趋势',
            textStyle: {
              color: '#fff',
              fontSize: 16
            },
            left: 'center'
          },
          tooltip: {
            trigger: 'axis',
            formatter: params => {
              const date = params[0].axisValue;
              return params.map(p => `
          <div style="display: flex; align-items: center">
            <span style="display:inline-block;width:8px;height:8px;border-radius:50%;background:${p.color};margin-right:5px"></span>
            ${p.seriesName}: ${p.value}次
          </div>
        `).join('') + `<div>${date}</div>`;
            }
          },
          legend: {
            data: series.map(s => s.name),
            textStyle: { color: '#fff' },
            bottom: 10
          },
          grid: {
            top: 60,
            bottom: 80,
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: categories,
            axisLabel: {
              color: '#fff',
              formatter: value => dayjs(value).format('YYYY-MM')
            },
            axisLine: {
              lineStyle: { color: '#fff' }
            }
          },
          yAxis: {
            type: 'value',
            min: 0,
            minInterval: 1,
            axisLabel: {
              color: '#fff',
              formatter: '{value} 次'
            },
            splitLine: {
              lineStyle: { color: 'rgba(255,255,255,0.1)' }
            },
            axisLine: {
              lineStyle: { color: '#fff' }
            }
          },

          series: series.map((s, index) => ({
            ...s,
            lineStyle: {
              width: 3, // 加粗线条
              shadowColor: 'rgba(79,250,110,0.5)', // 调整阴影颜色
              shadowBlur: 12
            },
            itemStyle: {
              color: '#4FFA6E' // 固定为统一颜色
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(79,250,110,0.4)' },
                { offset: 1, color: 'rgba(79,250,110,0.05)' }
              ])
            }
          }))
        };

        this.myChart.setOption(option, true);
        this.myChart.hideLoading();
      },
      getAreaColor(index) {
        const colors = [
          'rgba(79,250,110,0.3)',
          'rgba(255,194,51,0.3)',
          'rgba(255,109,74,0.3)',
          'rgba(106,139,255,0.3)'
        ];
        return colors[index];
      },
      async scrollData() {
        const { data } = await all();

        // 格式化数据为二维数组
        const formattedData = data.map(item => [
          item.name,
          item.faculty?.name,
          item.room?.number
        ]);
        // 响应式更新数据
        this.$set(this.scrollBoardConfig, 'data', formattedData);
        this.scrollBoardKey = Date.now();
      },
      async initPieCharts() {
        // 性别比例饼图
        const studentChart = echarts.init(this.$refs.studentPie);
        console.log(this.facultyData)
        studentChart.setOption({
          title: {
            text: '学院人数占比',
            textStyle: {
              color: '#fff',
              fontSize: 14
            },
            left: 'left'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c}人 ({d}%)'
          },
          series: [{
            type: 'pie',
            radius: ['0%', '70%'],
            center: ['50%', '50%'],
            data: this.facultyData,
            label: {
              formatter: ({ name, percent }) =>
                `${name}\n${percent.toFixed(1)}%`, // 显示名称和百分比
              fontSize: 12,
              color: '#fff',
              rich: {
                name: { fontSize: 12 },
                percent: {
                  fontSize: 14,
                  color: '#4FFA6E' // 百分比特殊颜色
                }
              }
            },
            labelLine: {
              show: true,
              length: 20,
              smooth: 0.2
            }
          }]
        });

        const RegMonthChart = echarts.init(this.$refs.regMonthPie);
        RegMonthChart.setOption({
          title: {
            text: '入学日期占比',
            textStyle: {
              color: '#fff',
              fontSize: 14
            },
            left: 'right'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c}次 ({d})'
          },
          series: [{
            type: 'pie',
            radius: ['0%', '70%'],
            center: ['50%', '50%'],
            data: this.regMonthData,
            label: {
              formatter: ({ name, percent }) =>
                `${name}\n${percent.toFixed(0)}%`, // 显示名称和百分比
              fontSize: 12,
              color: '#fff',
              rich: {
                name: { fontSize: 12 },
                percent: {
                  fontSize: 14,
                  color: '#4FFA6E' // 百分比特殊颜色
                }
              }
            },
            labelLine: {
              show: true,
              length: 20,
              smooth: 0.2
            }
          }]
        });
      },
      async fetchScoreTrend() {
        try {
          const { data } = await selectByTime();
          if (data && data.length > 0) {
            this.processChartData(data);
          } else {
            this.myChart.clear();
          }
        } catch (error) {
          this.myChart.showLoading('default', { text: '数据加载失败' });
        }
      },
      formatTime(time) {
        return dayjs(time).format('YYYY-MM-DD HH:mm')
      }
    },
    // 生命周期
    async mounted() {
      await this.fetchFacultyData()
      await this.fetchRegMonth()
      this.$nextTick(() => {
        this.initPieCharts();
      });

      this.myChart = echarts.init(document.getElementById('stackedLine'))
      window.addEventListener('resize', () => this.myChart.resize())
      this.fetchScoreTrend() // 确保调用数据获取
    },
    created() {
      this.listData()
    }
  }
</script>

<style scoped>
  .fullscreen-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    color: #fff;
    z-index: 100;
    display: flex;
    flex-direction: column;
  }

  .dashboard-container {
    width: 100vw;
    height: 100vh;
    background: #001529;
    color: #fff;
    overflow: hidden;
    background-image: url('../../../assets/bg.jpg');
    background-position: center;
    background-size: cover;
    background-repeat: no-repeat;
  }

  .main-content {
    display: grid;
    grid-template-columns: 1fr 1.4fr 1fr;
    gap: 2vmin;
    height: calc(100vh - 100px);
    margin-top: 50px;
    padding: 0 20px;
  }

  .left-section,
  .right-section {
    display: grid;
    grid-template-rows: 2.2fr 1fr;
    flex-direction: column;
    gap: 2vmin;
  }

  .center-section {
    display: grid;
    grid-template-rows: 2.2fr 1fr;
    gap: 2vmin;
    padding: 0 2vmin;
  }

  .dv-border-box-8 {
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    /* 新增定位基准 */
    overflow: hidden;
    /* 确保遮罩效果不溢出容器 */
  }

  .dv-border-box-8 img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    transform: translate(0%, 0%);
    object-fit: cover;
    will-change: transform;
    /* 新增羽化遮罩 */
    -webkit-mask:
      radial-gradient(circle at 50% 50%,
        rgba(0, 0, 0, 1) 60%,
        rgba(0, 0, 0, 0) 90%);
    mask:
      radial-gradient(circle at 50% 50%,
        rgba(0, 0, 0, 1) 60%,
        rgba(0, 0, 0, 0) 90%);
  }

  #stackedLine {
    width: 100%;
    height: 100%;
  }

  .image-container {
    position: relative;
    top: -15px;
    width: 685px;
    height: 548px;
    /* 新增羽化效果 */
    mask-image: linear-gradient(to right,
        transparent 0%,
        white 10%,
        white 90%,
        transparent 100%),
      linear-gradient(to bottom,
        transparent 0%,
        white 10%,
        white 90%,
        transparent 100%);
    mask-mode: alpha;
    mask-size: 100% 100%;
    mask-composite: intersect;
  }

  .annotated-image {
    width: 100%;
    height: 100%;

    box-shadow: 0 0 20px 10px rgba(255, 255, 255, 0.6);
    border-radius: 8px;
    /* 圆角需与阴影配合 */
  }

  .annotation {
    position: absolute;
    display: flex;
    align-items: center;
    /* 垂直居中 */
  }

  /* 红点样式 */
  .dot {
    width: 10px;
    height: 10px;
    background-color: red;
    border-radius: 50%;
    margin: 0 10px;
    /* 红点和文字框之间的间距 */
  }

  /* 文字框样式 */
  .text-box {
    /* padding: 5px;
  background-color: #fff;
  border: 1px solid #000;
  border-radius: 5px;*/
    font-size: 19px;
    color: white;
  }

  /* 闪烁动画 */
  .blinking {
    animation: blink 4s infinite;
  }

  @keyframes blink {
    0% {
      opacity: 1;
    }

    50% {
      opacity: 0;
    }

    100% {
      opacity: 1;
    }
  }

  .notice-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* 垂直居中 */
    align-items: center;
    /* 水平居中 */
    width: 100%;
    /* 确保宽度继承 */
    height: 100%;
    /* 继承父容器高度 */
    /* padding: 20px; */
    /* 内边距留白 */
  }

  .notice-content {
    position: relative;
    margin-top: 10%;
    z-index: 1;
    /* 提升层级避免被边框遮挡 */
    width: 90%;
    /* 留出边框间距 */
    max-width: 500px;
    /* 防止内容过宽 */

    height: 100%;
    padding: 15px;
    overflow-y: auto;
  }


  .notice-title {
    font-size: 1.5em;
    font-weight: bold;
    margin-top: 15%;
    margin-bottom: 1%;
    color: #8da6ea;
  }

  .notice-content .dv-border-box-5 {
    min-height: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .notice-textarea {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 90%;
    height: 100%;
    font-size: 1.5em;
    text-align: center;
    padding: 15px;
    /* 增加内边距提升可读性 */
    word-break: break-all;
    /* 长文本自动换行 */
    min-height: 80px;
    padding: 10px;
    font-size: 16px;
    color: #a6d1ff;
    word-break: break-all;
  }

  .notice-row {
    margin-top: 5%;
    margin-bottom: 5%;
  }

  /* 清除边框组件默认padding */
  .dv-border-box-11 .border-box-content {
    padding: 0 !important;
  }

  /* 调整边框组件的定位基准 */
  .dv-border-box-11 {
    position: relative;
  }

  .float-button {
    position: fixed;
    z-index: 99999;
    bottom: 30px;
    right: 30px;
    background: rgba(0, 123, 255, 0.9);
    border-radius: 24px;
    padding: 12px 24px;
    cursor: pointer;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    transition: all 0.3s ease;
    display: flex;
    align-items: center;
  }

  .float-button:hover {
    background: #007bff;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0, 123, 255, 0.3);
  }

  .button-content {
    display: flex;
    align-items: center;
    color: white;
    font-size: 16px;
  }

  .el-icon-back {
    font-size: 20px;
    margin-right: 8px;
  }

  .border-box-content h2 {
    position: absolute;
  }

  .pie-chart-container {
    width: 100%;
    height: 100%;
  }

  #stackedLine {
    width: 100%;
    height: 100%;
  }
</style>