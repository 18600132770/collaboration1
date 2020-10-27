
<template>
  <div style="height: 100%">
    <div id="progress" :style="{ height: '20px', width: width }"></div>
    <div id="p-chart" :style="{ height: height, width: width }"></div>
  </div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'ProjectProgress',
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
      progress: null,

      height: 'calc(100% - 10px)',
      width: '100%',
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

    updateProgressChart,
  },
  beforeDestroy() {
    this.myChart && this.myChart.clear()
    this.myChart = null

    this.progress && this.progress.clear()
    this.progress = null
  },
}

function initBarChart() {
  this.myChart = echarts.init(document.getElementById('p-chart'))
  this.progress = echarts.init(document.getElementById('progress'))

  this.updateChart()
  this.updateProgressChart()
}

function updateChart() {
  let data = {
    xData: ['预付款', '科研', '初设', '施设', '配施', '验收', '质保金'],
    yData: [10, 10, 25, 35, 10, 5, 5],
  }
  let realdata = {
    xData: ['预付款', '科研', '初设', '施设'],
    yData: [10, 10, 25, 35],
  }
  let colors = [
    '#4150d8',
    '#28bf7e',
    '#ed7c2f',
    '#f2a93b',
    '#f9cf36',
    '#4a5bdc',
    '#4cd698',
    '#f4914e',
    '#fcb75b',
    '#ffe180',
    '#b6c2ff',
    '#96edc1',
  ]
  /**
      双X轴标签对应，伪实现思路：
      底部的标签也是柱状图，对应包含的区域为上方X轴条数占总数的比例，设为宽度即可
  */
  let option = {
    tooltip: {
      show: false,
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      },
    },
    grid: [
      {
        left: 60,
        top: 10,
        right: '10%',
        bottom: 25,
      },
      {
        left: 60,
        right: '10%',
        height: 15,
        bottom: 10,
      },
    ],
    xAxis: [
      {
        type: 'value',
        data: data.xData,
        gridIndex: 0,
        axisLabel: {
          color: '#333',
        },
        axisLine: {
          show: false,
        },
        max: 100,
        axisTick: {
          show: false,
        },
        splitLine: {
          show: false,
        },
        position: 'top',
        zlevel: 2,
      },
      {
        name: '项目节点',
        nameLocation: 'start',
        //
        nameTextStyle: {
          color: '#EFF4F9',
          verticalAlign: 'bottom',
          align: 'right',
          padding: [0, -8, 0, 0],
        },
        type: 'category',
        gridIndex: 1,
        axisLine: {
          show: false,
        },
        zlevel: 1,
      },
    ],
    yAxis: [
      {
        type: 'category',
        gridIndex: 0,
        axisLabel: {
          color: '#eff4f9',
        },
        splitLine: {
          show: false,
          lineStyle: {
            type: 'dashed',
          },
        },
        axisLine: {
          show: false,
          lineStyle: {
            color: '#ccc',
          },
        },
        axisTick: {
          show: false,
          lineStyle: {
            color: '#ccc',
          },
        },
        data: ['累计收款'],
      },
      {
        type: 'value',
        gridIndex: 1,
        axisLabel: {
          show: false,
        },
        axisLine: {
          show: false,
        },
        splitLine: {
          show: false,
        },
        axisTick: {
          show: false,
        },
      },
    ],
    series: [],
  }

  realdata.xData.forEach((item, index) => {
    option.series.push({
      name: '累计收款',
      data: [realdata.yData[index]],
      type: 'bar',
      stack: 'progress',
      barWidth: '15',
      label: {
        show: true,
        position: 'inner',
        align: 'left',
        verticalAlign: 'bottom',
        padding: [0, 0, 0, 10],
        textStyle: {
          color: '#eff4f9',
        },
        formatter: '{c}%',
      },
      itemStyle: {
        normal: {
          color: () => {
            return colors[index]
          },
        },
      },
      xAxisIndex: 0,
      yAxisIndex: 0,
    })
  })

  data.xData.forEach((item, index) => {
    option.series.push({
      data: [
        {
          name: item,
          value: 1,
        },
      ],
      align: 'center',
      label: {
        show: true,
        position: 'inside',
        formatter: '{b}',
        offset: [0, 0],
        textStyle: {
          color: '#eff4f9',
          fontSize: 10,
        },
      },
      type: 'bar',
      barGap: 0,
      barWidth: `${data.yData[index]}%`,
      itemStyle: {
        normal: {
          color: colors[index],
        },
      },
      xAxisIndex: 1,
      yAxisIndex: 1,
    })
  })

  // option.series

  // this.myChart && this.myChart.clear()
  this.myChart.setOption(option, false)
}

function updateProgressChart() {
  var baifenbi = [0.8]
  var grayBar = [1]
  var city = [
    '收款进度'
  ]
  let option = {
    color: ['#DBB019'],
    grid: {
      left: '60',
      right: '10%',
      bottom: '0',
      top: '30%',
      // containLabel: true,
    },
    xAxis: [
      {
        show: false,
      },
      {
        show: false,
      },
    ],
    yAxis: {
      type: 'category',
      axisLabel: {
        show: true, //让Y轴数据不显示
        interval: 0,
        color: '#eff4f9',
        fontSize: 12,
        padding: [5, 0, 5, 5],
      },
      itemStyle: {},
      axisTick: {
        show: false, //隐藏Y轴刻度
      },
      axisLine: {
        show: false, //隐藏Y轴线段
      },

      data: city,
    },
    series: [
      //背景色--------------------我是分割线君------------------------------//
      {
        show: true,
        type: 'bar',
        barGap: '-100%',
        barWidth: '20', //统计条宽度
        itemStyle: {
          normal: {
            barBorderRadius: 5,
            color: '#24293D',
          },
        },
        z: 1,
        label: {
          normal: {
            show: true,
            textStyle: {
              color: '#EFF4F9', //百分比颜色
            },
            position: 'right',
            //百分比格式
            formatter: function (data) {
              return (baifenbi[data.dataIndex] * 100).toFixed(1) + '%'
            },
          },
        },
        data: grayBar,
      },
      //--------------------我是分割线君------------------------------//
      {
        show: true,
        type: 'bar',
        barGap: '-100%',
        barWidth: '15px', //统计条宽度
        max: 1,
        labelLine: {
          show: false,
        },
        z: 2,
        data: baifenbi,
      },
    ],
  }

  this.progress.setOption(option, false)
}

function onResize() {
  this.width = this.$el.offsetWidth - 20
  this.height = this.$el.offsetHeight

  console.log(this.width)

  this.myChart && this.myChart.resize()
  this.progress && this.progress.resize()
}
</script>

<style>
</style>
