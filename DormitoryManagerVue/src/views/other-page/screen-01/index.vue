<template>
  <div class="fullscreen-container">
    <dv-border-box-11 title="滇西科技师范学院" class="dashboard-container">

      <!-- 主体内容 -->
      <div class="main-content">
        <!-- 左侧区域 -->
        <div class="left-section">
          <dv-border-box-8 style="height: 100%;">
            <dv-scroll-board :key="scrollBoardKey" :config="scrollBoardConfig"
              style="width:calc(100% - 20px);height:calc(80% - 20px);margin:10px">
              <div v-if="scrollBoardConfig.data.length === 0" class="empty-tip">
                {{ loading ? '数据加载中...' : '暂无请假记录' }}
              </div>
            </dv-scroll-board>
            <dv-percent-pond :config="precentPondConfig"
              style="width:calc(100% - 20px);height:calc(20% - 10px);margin:10px" />
          </dv-border-box-8>
          <dv-border-box-8 style="height: 75%;">
            <dv-capsule-chart ::key="capsuleRefreshKey" :config="cCapsuleChartConfig" style="width:100%;height:100%" />
          </dv-border-box-8>
        </div>

        <!-- 中间区域 -->
        <div class="center-section">
          <div class="slider">
            <img :src="images[currentIndex]" :style="{ transform: `translateZ(0)` }" @load="handleImageLoad" />
          </div>
          <dv-border-box-8 style="height: 75%;">
            <div id="stackedLine"></div>
          </dv-border-box-8>
        </div>

        <!-- 右侧区域 -->
        <div class="right-section">
          <dv-border-box-8 style="height: 100%;">
            <h2>本月卫生评分</h2>
            <dv-active-ring-chart ::key="ringRefreshKey" :config="ringChartConfig" style="width:100%;height:100%" />
          </dv-border-box-8>
          <dv-border-box-8 style="height: 75%;">
            <div class="digital-grid">
              <div v-for="(item, index) in totalStats" :key="index" class="digital-item">
                <div class="digital-label">{{ item.label }}</div>
                <div class="digital-value">
                  <span class="value-bg">{{ item.value }}</span>
                  <animated-number :value="item.value" :duration="1000" easing="easeOutCubic" class="value-front" />
                </div>
              </div>
            </div>
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
  import { list as leaveList } from "@/api/leave"
  import { list as stuList } from "@/api/student"
  import { getAllStudentNum } from "@/api/building"
  import { stats, getScoreTrend } from "@/api/score"
  import { totalStats } from "@/api/stats"

  Vue.component('animated-number', AnimatedNumber);
  Vue.use(dataV)


  export default {
    data() {
      return {
        ringRefreshKey: 0,
        capsuleRefreshKey: 0,
        scrollBoardKey: 0,
        loading: false,
        listQuery: {
          page: 1,
          rows: 99999,
          entity: {},
        },
        imgRatio: 0.5625,
        isHovering: false,
        images: [
          'https://xsxx.lxxno.cn/2025xszhwz/other/1.png',
          'https://xsxx.lxxno.cn/2025xszhwz/other/2.png',
          'https://xsxx.lxxno.cn/2025xszhwz/other/3.png'
        ],
        // 当前显示的图片索引
        currentIndex: 0,
        // 定时器 ID
        timer: null,
        totalStats: [],
        countConifg: {
          number: [100],
          content: '{nt}个'
        },
        ringChartConfig: {
          data: []
        },
        cCapsuleChartConfig: {
          data: [
          ]
        },
        scrollBoardConfig: {
          header: ['姓名', '请假时间', '返校时间'],
          data: [],
          index: false,
          columnWidth: [80],
          align: ['center']
        },
        precentPondConfig: {
          value: 0,
          borderWidth: 1,
          borderRadius: 5,
          borderGap: 5,
          localGradient: false
        }
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
          this.percentPondData()
          this.cCapsuleChartData()
          this.ringChartData()
          this.digitalData()
        } finally {
          this.loading = false;
        }
      },
      async fetchScoreTrend() {
        try {
          const { data } = await getScoreTrend();
          console.log("zhexian" + data)
          if (data && data.length > 0) {
            this.processChartData(data);
          } else {
            console.warn('无评分趋势数据');
            this.myChart.clear();
          }
        } catch (error) {
          console.error('获取评分趋势失败:', error);
          this.myChart.showLoading('default', { text: '数据加载失败' });
        }
      },
      processChartData(apiData) {
        // 按接口返回数据结构处理
        const categories = apiData.map(item => item.month);
        const series = [
          {
            name: '综合评分',
            data: apiData.map(item => item.globalAvg.toFixed(1)),
            type: 'line',
            smooth: true
          },
          {
            name: '建筑评分',
            data: apiData.map(item => item.buildingAvg.toFixed(1)),
            type: 'line',
            smooth: true
          },
          {
            name: '床位评分',
            data: apiData.map(item => item.bedAvg.toFixed(1)),
            type: 'line',
            smooth: true
          },
          {
            name: '室内评分',
            data: apiData.map(item => item.indoorAvg.toFixed(1)),
            type: 'line',
            smooth: true
          }
        ];

        this.updateChartConfig(categories, series);
      },

      updateChartConfig(categories, series) {
        const option = {
          title: {
            text: '宿舍卫生评分趋势',
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
            ${p.seriesName}: ${p.value}分
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
            max: 10,
            axisLabel: {
              color: '#fff',
              formatter: '{value} 分'
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
              width: 2,
              shadowColor: 'rgba(0,0,0,0.3)',
              shadowBlur: 8
            },
            itemStyle: {
              color: ['#4FFA6E', '#FFC233', '#FF6D4A', '#6A8BFF'][index]
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: this.getAreaColor(index) },
                { offset: 1, color: 'rgba(255,255,255,0)' }
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
      async digitalData() {
        const { data } = await totalStats()
        const formattedData = [
          { label: '维修上报数', value: data[0].repairCount, color: { gradient: ['#00F2FE', '#4FACFE'] } },
          { label: '请假申请数', value: data[0].leaveCount, color: { gradient: ['#FAD961', '#F76B1C'] } },
          { label: '病因上报数', value: data[0].absenceCount, color: { gradient: ['#6F86FF', '#48C6EF'] } },
          { label: '学生总人数', value: data[0].studentNum, color: { gradient: ['#42E695', '#3BB2B8'] } }
        ]

        this.totalStats = formattedData

      },
      async ringChartData() {
        const { data } = await stats()

        console.log(data[0].acount)

        const formattedData = [
          {
            name: "A级",
            value: data[0].acount
          },
          {
            name: "B级",
            value: data[0].bcount
          },
          {
            name: "C级",
            value: data[0].ccount
          },
          {
            name: "D级",
            value: data[0].dcount
          }
        ]

        this.ringChartConfig = {
          ...this.ringChartConfig, // 保留其他配置项
          data: formattedData,
          lineWidth: 50,
        }
        console.log(this.ringChartConfig)
        this.ringRefreshKey = Date.now();
      },
      async cCapsuleChartData() {
        const { data } = await getAllStudentNum();

        const formattedData = data.map(item => ({
          name: item.name,
          value: item.studentNum
        }))

        this.cCapsuleChartConfig = {
          ...this.cCapsuleChartConfig,
          data: formattedData,

          colors: ['#4FACFE', '#F76B1C'], // 示例颜色
          unit: '人',
          showValue: true
        }
        this.capsuleRefreshKey = Date.now()
      },
      async percentPondData() {
        const { data } = await stuList(this.listQuery);
        // 计算离校人数和总人数
        const total = data.list.length;
        const leave = data.list.reduce((acc, cur) => cur.isLeave ? acc + 1 : acc, 0);

        // 计算在校率百分比（保留两位小数）
        const inSchoolRatio = total > 0 ? ((total - leave) / total * 100).toFixed(2) : 0;

        // 更新百分比水池配置（数值范围0-100）
        this.precentPondConfig = {
          ...this.precentPondConfig,
          value: parseFloat(inSchoolRatio) // 转换为浮点数
        };

      },
      async scrollData() {
        const { data } = await leaveList(this.listQuery);

        // 格式化数据为二维数组
        const formattedData = data.list
          .filter(item => !item.isBack) // 筛选isBack为false的条目
          .map(item => [
            item.student?.name || '未知',
            this.formatTime(item.time),
            this.formatTime(item.backDate)
          ]);
        // 响应式更新数据
        this.$set(this.scrollBoardConfig, 'data', formattedData);
        this.scrollBoardKey = Date.now();
      },
      formatTime(timestamp) {
        return dayjs(timestamp).format('YYYY-MM-DD HH:mm');
      },
      handleImageLoad(e) {
        const img = e.target
        this.imgRatio = img.naturalHeight / img.naturalWidth
        this.$nextTick(() => {
          this.$forceUpdate() // 强制触发视图更新[2,6](@ref)
        })
      },
      // 切换到上一张图片
      prevSlide() {
        this.currentIndex =
          this.currentIndex === 0
            ? this.images.length - 1
            : this.currentIndex - 1;
      },
      // 切换到下一张图片
      nextSlide() {
        this.currentIndex = (this.currentIndex + 1) % this.images.length
      }
    },
    // 生命周期
    mounted() {
      this.images.forEach(img => {
        new Image().src = img // 预加载所有图片
      })
      this.timer = setInterval(() => {
        this.nextSlide()
      }, 2000)
      this.myChart = echarts.init(document.getElementById('stackedLine'))
      window.addEventListener('resize', () => this.myChart.resize())
      this.fetchScoreTrend() // 确保调用数据获取
    },
    beforeDestroy() {
      clearInterval(this.timer)
      if (this.myChart) {
        this.myChart.dispose();
        window.removeEventListener('resize', this.myChart.resize);
      }
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
    grid-template-columns: 1fr 2fr 1fr;
    gap: 2vmin;
    height: calc(100vh - 100px);
    margin-top: 50px;
    padding: 0 20px;
  }

  .left-section,
  .right-section {
    display: grid;
    grid-template-rows: 1fr 1fr;
    flex-direction: column;
    gap: 2vmin;
  }

  .center-section {
    display: grid;
    grid-template-rows: 1fr 1fr;
    gap: 2vmin;
    padding: 0 2vmin;
  }

  .dv-border-box-8 {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .slider {
    position: relative;
    width: 100%;
    height: 0;
    padding-bottom: 56.25%;
    overflow: hidden;
    z-index: 1;

  }

  .slider img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    transform: translate(-50%, -50%);
    object-fit: cover;
    will-change: transform;
  }

  #stackedLine {
    width: 100%;
    height: 100%;
  }

  .digital-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 2vh;
    /* 改用视口单位 */
    height: 100%;
    /* 新增 */
    padding: 1.5%;
    /* 相对单位替代固定像素 */
    box-sizing: border-box;
    /* 确保padding不溢出 */
  }

  .digital-item {
    background: rgba(16, 35, 78, 0.6);
    border-radius: 8px;
    padding: 50px;
    position: relative;
    overflow: hidden;
    min-height: 120px;
    /* 设置最小高度 */
    padding: 5% 8%;
    /* 百分比替代固定像素 */
    display: flex;
    /* 启用弹性布局 */
    flex-direction: column;
    justify-content: center;
    /* 改进点3：动态尺寸约束 */
    height: calc((100% - 2vh)/2);
    /* 计算高度 */
  }

  .digital-label {
    font-size: clamp(12px, 1.2vw, 16px);
    /* 添加字体响应式 */
    color: #89f7fe;
    margin-bottom: 8px;
  }

  .digital-value {
    position: relative;
    font-size: clamp(24px, 3vw, 36px);
    font-family: 'DS-Digital', monospace;
  }

  .value-front {
    color: #4af7ff;
    text-shadow: 0 0 10px rgba(74, 247, 255, 0.5);
  }

  .value-bg {
    position: absolute;
    color: rgb(255, 255, 255);
    z-index: 0;
  }

  /* 数据流动效果 */
  .digital-item::after {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 50%;
    height: 100%;
    background: linear-gradient(to right,
        rgba(255, 255, 255, 0) 0%,
        rgba(255, 255, 255, 0.1) 50%,
        rgba(255, 255, 255, 0) 100%);
    transform: skewX(-45deg);
    animation: flow 3s infinite;
  }

  @keyframes flow {
    0% {
      left: -100%;
    }

    100% {
      left: 200%;
    }
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
</style>