
<template>
  <div id="progress-gauge" :style="{ height: height, width: width }"></div>
</template>

<script>
// https://gallery.echartsjs.com/editor.html?c=xuZ49_DgJ3
import echarts from 'echarts'

export default {
  name: 'ProgressGauge',
  props: {
    percentData: {
      type: Object,
      // required: true
    },
  },
  data() {
    return {
      barData: [],
      legendSelected: {},
      myChart: null,

      height: '100%',
      width: '100%',
      timer: null
    }
  },
  computed: {
    pieData() {
      let temp = []
      for (let key in this.percentData) {
        temp.push({
          name: key,
          value: this.percentData[key],
        })
      }
      return temp
    },
  },
  watch: {
    pieData() {
      this.pieData
        .map((item) => item.name)
        .forEach((key) => {
          this.legendSelected[key] = true
        })
    },
    // lengend变化时请求接口
    legendSelected() {
      let type = []
      for (let key in this.legendSelected) {
        if (this.legendSelected[key]) {
          type.push(key)
        }
      }
      this.getWords(type.join(','), this.updateChart)
    },
  },
  mounted() {
    this.initBarChart()

    window.addEventListener('resize', this.onResize)
    this.onResize()
  },
  methods: {
    // 初始化柱状图
    initBarChart,
    updateChart,
    onResize,
    startTimer
  },
  beforeDestroy() {
    this.myChart && this.myChart.clear()
    this.myChart = null

    clearInterval(this.timer)
  },
}

function initBarChart() {
  this.myChart = echarts.init(document.getElementById('progress-gauge'))

  setTimeout(this.startTimer, 0)

  this.updateChart()
}

function updateChart() {
  let value = 81
  let title = '已完成'

  let option = {
    title: {
      text: `${title}\n${value}%`,
      x: 'center',
      y: 'center',
      textStyle: {
        fontSize: 15,
        color: '#ffffff',
      },
    },
    series: [
      {
        type: 'gauge',
        radius: '40%',
        clockwise: false,
        startAngle: '-80',
        endAngle: '-439.9999',
        splitNumber: 25,
        detail: {
          offsetCenter: [0, -20],
          formatter: ' ',
        },
        pointer: {
          show: false,
        },
        axisLine: {
          show: true,
          lineStyle: {
            color: [
              [0, '#2CFAFC'],
              [50 / 100, '#00FFFF'],
              [1, '#0E192D'],
            ],
            width: 6,
          },
        },
        axisTick: {
          show: false,
        },
        splitLine: {
          show: true,
          length: 32,
          lineStyle: {
            color: '#0E192D',
            width: 6,
          },
        },
        axisLabel: {
          show: false,
        },
      },
      {
        name: '最内层虚线圈',
        type: 'pie',
        radius: ['24%', '25%'],
        hoverAnimation: false,
        clockWise: false,
        itemStyle: {
          normal: {
            color: 'red',
          },
        },
        label: {
          show: false,
        },
        data: setdata(),
      },
      {
        type: 'pie',
        name: '内层细圆环',
        radius: ['31%', '31.5%'],
        hoverAnimation: false,
        clockWise: false,
        itemStyle: {
          normal: {
            color: '#00FFFF',
          },
        },
        label: {
          show: false,
        },
        data: [100],
      },
      {
        type: 'pie',
        name: '外层细圆环',
        radius: ['41%', '42%'],
        hoverAnimation: false,
        clockWise: false,
        itemStyle: {
          normal: {
            color: '#00FFFF',
          },
        },
        label: {
          show: false,
        },
        data: [100],
      },
      {
        name: '大环',
        type: 'gauge',
        splitNumber: 35,
        radius: '52%',
        center: ['50%', '50%'],
        startAngle: 90,
        endAngle: -269.9999,
        axisLine: {
          show: false,
          lineStyle: {
            color: [[1, '#00FFFF']],
          },
        },
        axisTick: {
          show: false,
        },
        splitLine: {
          show: true,
          length: 6,
          lineStyle: {
            color: 'auto',
            width: 1.5,
          },
        },
        axisLabel: {
          show: false,
        },
        detail: {
          show: false,
        },
      },
    ],
  }
  this.myChart.setOption(option, false)
}

function setdata() {
  let dataArr = []
  for (var i = 0; i < 100; i++) {
    if (i % 2 === 0) {
      dataArr.push({
        name: (i + 1).toString(),
        value: 10,
        itemStyle: {
          normal: {
            color: 'rgb(0,255,255,.3)',
          },
        },
      })
    } else {
      dataArr.push({
        name: (i + 1).toString(),
        value: 100,
        itemStyle: {
          normal: {
            color: 'rgb(0,0,0,0)',
            borderWidth: 0,
            borderColor: 'rgba(0,255,255,1)',
          },
        },
      })
    }
  }
  return dataArr
}
function startTimer() {
  const _this = this
  this.timer = setInterval(() => {
    let option = _this.myChart.getOption()
    option.series[1].startAngle = option.series[1].startAngle - 1
    _this.myChart.setOption(option)
  }, 100)
}

function onResize() {
  this.width = this.$el.offsetWidth
  this.height = this.$el.offsetHeight

  this.myChart && this.myChart.resize()
}
</script>

<style>
</style>
