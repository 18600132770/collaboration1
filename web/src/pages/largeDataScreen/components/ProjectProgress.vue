
<template>
  <div id="line-chart" :style="{ height: height, width: width }"></div>
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

      height: '100%',
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
  },
  beforeDestroy() {
    this.myChart && this.myChart.clear()
    this.myChart = null
  },
}

function initBarChart() {
  this.myChart = echarts.init(document.getElementById('line-chart'))

  this.updateChart()
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
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      },
    },
    grid: [
      {
        left: 60,
        top: 10,
        right: 10,
        bottom: 30,
      },
      {
        left: 60,
        right: 10,
        height: 10,
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
        splitLine:  {
          show: false,
        },
        position: 'top',
        zlevel: 2,
      },
      {
        name: '项目节点',
        nameLocation: 'start',
        nameTextStyle: {
          color: '#EFF4F9'
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
          show:false,
          lineStyle: {
            color: '#ccc',
          },
        },
        axisTick: {
          show:false,
          lineStyle: {
            color: '#ccc',
          },
        },
        data: ['项目进度', '累计收款']
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

  option.series.push({
    name: '项目进度',
    data: [45],
    type: 'bar',
    barWidth: '25%',
    label: {
      show: true,
      position: 'inner',
      textStyle: {
        color: '#eff4f9',
      },
    },
    itemStyle: {
      normal: {
        color: () => {
          return colors[1]
        },
      },
    },
    xAxisIndex: 0,
    yAxisIndex: 0,
  })


  realdata.xData.forEach((item, index) => {
    option.series.push({
        name: '累计收款',
        data: [0, realdata.yData[index]],
        type: 'bar',
        stack: 'progress',
        barWidth: '25%',
        label: {
          show: true,
          position: 'inner',
          textStyle: {
            color: '#eff4f9',
          },
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
          fontSize: 10
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

function onResize() {
  this.width = this.$el.offsetWidth
  this.height = this.$el.offsetHeight

  this.myChart && this.myChart.resize()
}
</script>

<style>
</style>
