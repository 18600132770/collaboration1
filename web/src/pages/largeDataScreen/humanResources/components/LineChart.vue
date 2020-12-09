
<template>
  <div id="line-chart" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'LineChart',
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
  let bgColor = 'transparent'
  let color = ['#0090FF', '#36CE9E', '#FFC005', '#FF515A', '#8B5CFF', '#00CA69']
  let echartData = [
    {
      name: '初级',
      value1: 100,
      value2: 233,
    },
    {
      name: '中级',
      value1: 138,
      value2: 233,
    },
    {
      name: '高级',
      value1: 350,
      value2: 200,
    }
  ]

  let xAxisData = echartData.map((v) => v.name)
  // ["1", "2", "3", "4", "5", "6", "7", "8"]
  let yAxisData1 = echartData.map((v) => v.value1)
  // [100, 138, 350, 173, 180, 150, 180, 230]
  let yAxisData2 = echartData.map((v) => v.value2)
  // [233, 233, 200, 180, 199, 233, 210, 180]
  const hexToRgba = (hex, opacity) => {
    let rgbaColor = ''
    let reg = /^#[\da-f]{6}$/i
    if (reg.test(hex)) {
      rgbaColor = `rgba(${parseInt('0x' + hex.slice(1, 3))},${parseInt(
        '0x' + hex.slice(3, 5)
      )},${parseInt('0x' + hex.slice(5, 7))},${opacity})`
    }
    return rgbaColor
  }

  let option = {
    backgroundColor: bgColor,
    color: color,
    legend: {
      right: 10,
      top: 10,
      textStyle: {
        color: '#ddd'
      }
    },
    tooltip: {
      trigger: 'none',
      formatter: function (params) {
        let html = ''
        params.forEach((v) => {
          console.log(v)
          html += `<div style="color: #666;font-size: 14px;line-height: 24px">
                <span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:${
                  color[v.componentIndex]
                };"></span>
                ${v.seriesName}.${v.name}
                <span style="color:${
                  color[v.componentIndex]
                };font-weight:700;font-size: 18px">${v.value}</span>
                万元`
        })

        return html
      },
      extraCssText:
        'background: #fff; border-radius: 0;box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);color: #333;',
      axisPointer: {
        type: 'shadow',
        shadowStyle: {
          color: '#ffffff',
          shadowColor: 'rgba(225,225,225,1)',
          shadowBlur: 5,
        },
      },
    },
    grid: {
      top: 45,
      bottom: 20,
      containLabel: false,
    },
    xAxis: [
      {
        type: 'category',
        boundaryGap: false,
        axisLabel: {
          formatter: '{value}',
          textStyle: {
            color: '#ddd',
          },
        },
        axisLine: {
          lineStyle: {
            color: '#333',
          },
        },
        data: xAxisData,
      },
    ],
    yAxis: [
      {
        type: 'value',
        name: '单位：万元',
        axisLabel: {
          textStyle: {
            color: '#ddd',
          },
        },
        nameTextStyle: {
          color: '#ddd',
          fontSize: 12,
          lineHeight: 40,
        },
        splitLine: {
          lineStyle: {
            type: 'dashed',
            color: '#333',
          },
        },
        axisLine: {
          show: false,
        },
        axisTick: {
          show: false,
        },
      },
    ],
    series: [
      {
        name: '薪酬',
        type: 'line',
        smooth: true,
        // showSymbol: false,/
        symbolSize: 8,
        zlevel: 3,
        lineStyle: {
          normal: {
            color: color[0],
            shadowBlur: 3,
            shadowColor: hexToRgba(color[0], 0.5),
            shadowOffsetY: 8,
          },
        },
        areaStyle: {
          normal: {
            color: new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: hexToRgba(color[0], 0.3),
                },
                {
                  offset: 1,
                  color: hexToRgba(color[0], 0.1),
                },
              ],
              false
            ),
            shadowColor: hexToRgba(color[0], 0.1),
            shadowBlur: 10,
          },
        },
        data: yAxisData1,
      },
      {
        name: '劳动力',
        type: 'line',
        smooth: true,
        // showSymbol: false,
        symbolSize: 8,
        zlevel: 3,
        lineStyle: {
          normal: {
            color: color[1],
            shadowBlur: 3,
            shadowColor: hexToRgba(color[1], 0.5),
            shadowOffsetY: 8,
          },
        },
        areaStyle: {
          normal: {
            color: new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: hexToRgba(color[1], 0.3),
                },
                {
                  offset: 1,
                  color: hexToRgba(color[1], 0.1),
                },
              ],
              false
            ),
            shadowColor: hexToRgba(color[1], 0.1),
            shadowBlur: 10,
          },
        },
        data: yAxisData2,
      },
    ],
  }
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
