
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
  var NO2_data = [4020, 4050, 3320, 2810, 3980, 4140, 3440, 3050, 3410, 2890, 3560, 3280 ]
  var O3_data = [2810, 3980, 2140, 1790, 2890, 3560, 4220, 3560, 2381, 3220 ]
  var date_list = [
    '01',
    '02',
    '03',
    '04',
    '05',
    '06',
    '07',
    '08',
    '09',
    '10',
    '11',
    '12',
  ]

  let option = {
    legend: {
      data: ['2019年', '2020年'],
      textStyle: {
        color: '#fff',
        align: 'right',
      },
      x: 'right',
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        lineStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: 'rgba(0, 255, 233,0)',
              },
              {
                offset: 0.5,
                color: 'rgba(255, 255, 255,1)',
              },
              {
                offset: 1,
                color: 'rgba(0, 255, 233,0)',
              },
            ],
            global: false,
          },
        },
      },
    },
    grid: {
      top: '15%',
      left: '8%',
      right: '8%',
      bottom: '12%',
      // containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        axisLine: {
          show: true,
        },
        splitArea: {
          // show: true,
          color: '#f00',
          lineStyle: {
            color: '#f00',
          },
        },
        axisLabel: {
          color: '#fff',
        },
        splitLine: {
          show: false,
        },
        boundaryGap: false,
        data: date_list,
        // date:getVirtulData()['data'],
      },
    ],

    yAxis: [
      {
        type: 'value',
        min: 0,
        // max: 140,
        splitNumber: 4,
        splitLine: {
          show: true,
          lineStyle: {
            color: 'rgba(255,255,255,0.1)',
          },
        },
        axisLine: {
          show: false,
        },
        axisLabel: {
          show: false,
          margin: 20,
          textStyle: {
            color: '#d1e6eb',
          },
        },
        axisTick: {
          show: false,
        },
      },
    ],
    series: [
      {
        name: '2019年',
        type: 'line',
        // smooth: true, //是否平滑
        showAllSymbol: true,
        // symbol: 'image://./static/images/guang-circle.png',
        symbol: 'circle',
        symbolSize: 5,
        lineStyle: {
          normal: {
            color: '#6c50f3',
            shadowColor: 'rgba(0, 0, 0, .3)',
            shadowBlur: 0,
            shadowOffsetY: 0,
            shadowOffsetX: 0,
          },
        },
        label: {
          show: true,
          position: 'top',
          textStyle: {
            color: '#6c50f3',
          },
        },
        itemStyle: {
          color: '#6c50f3',
          borderColor: '#fff',
          borderWidth: 1,
          shadowColor: 'rgba(0, 0, 0, .3)',
          shadowBlur: 0,
          shadowOffsetY: 0,
          shadowOffsetX: 0,
        },
        tooltip: {
          show: false,
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
                  color: 'rgba(108,80,243,0.3)',
                },
                {
                  offset: 1,
                  color: 'rgba(108,80,243,0)',
                },
              ],
              false
            ),
            shadowColor: 'rgba(108,80,243, 0.9)',
            shadowBlur: 20,
          },
        },
        data: NO2_data,
      },
      {
        name: '2020年',
        type: 'line',
        // smooth: true, //是否平滑
        showAllSymbol: true,
        // symbol: 'image://./static/images/guang-circle.png',
        symbol: 'circle',
        symbolSize: 5,
        lineStyle: {
          normal: {
            color: '#00ca95',
            shadowColor: 'rgba(0, 0, 0, .3)',
            shadowBlur: 0,
            shadowOffsetY: 0,
            shadowOffsetX:0,
          },
        },
        label: {
          show: true,
          position: 'top',
          textStyle: {
            color: '#00ca95',
          },
        },

        itemStyle: {
          color: '#00ca95',
          borderColor: '#fff',
          borderWidth: 1,
          shadowColor: 'rgba(0, 0, 0, .3)',
          shadowBlur: 0,
          shadowOffsetY: 0,
          shadowOffsetX: 0,
        },
        tooltip: {
          show: false,
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
                  color: 'rgba(0,202,149,0.3)',
                },
                {
                  offset: 1,
                  color: 'rgba(0,202,149,0)',
                },
              ],
              false
            ),
            shadowColor: 'rgba(0,202,149, 0.9)',
            shadowBlur: 20,
          },
        },
        data: O3_data,
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
