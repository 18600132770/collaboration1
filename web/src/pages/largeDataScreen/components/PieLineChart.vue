
<template>
  <div id="pie-line-chart" :style="{ height: height, width: width }"></div>
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
  this.myChart = echarts.init(document.getElementById('pie-line-chart'))

  this.updateChart()
}

function updateChart() {
  var NO2_data = [10, 19, 33, 48, 55, 60, 70, 80, 95 ]
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
  ]

   var datas = {
     value: 95,
     company: "%",
     ringColor: [{
         offset: 0,
         color: '#EB9B46' // 0% 处的颜色
     }, {
         offset: 1,
         color: '#EB9B46' // 100% 处的颜色
     }]
  }

  let option = {
    title: {
      text: datas.value + datas.company,
      textStyle: {
          fontWeight: 'normal',
          color: '#fff',
          fontSize: '14'
      },
      left: '11%',
      top: '20%'
    },
    legend: {
      data: [],
      textStyle: {
        color: '#fff',
        align: 'right',
      },
      x: 'right',
    },
    tooltip: {
      trigger: 'nonde',
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
      left: '10',
      right: '10',
      bottom: '12%',
      // containLabel: true
    },
    color: 'transparent',
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
          align: 'center'
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
          show: false,
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
        // showSymbol: false,
        // symbol: 'image://./static/images/guang-circle.png',
        symbol: 'none',
        symbolSize: 1,
        lineStyle: {
          normal: {
            color: '#eee',
            shadowColor: 'rgba(46,51,71, .3)',
            shadowBlur: 0,
            shadowOffsetY: 0,
            shadowOffsetX: 0,
            width: 1
          },
        },
        label: {
          show: true,
          position: 'top',
          textStyle: {
            color: '#fff',
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
                  color: 'rgba(60,71,88,1)',
                },
                {
                  offset: 1,
                  color: 'rgba(46,51,71,0)',
                },
              ],
              false
            ),
            shadowColor: 'rgba(46,51,71, 0.9)',
            shadowBlur: 20,
          },
        },
        data: NO2_data,
      },
      {
         name: 'Line 1',
         type: 'pie',
         clockWise: true,
         radius: ['30%', '40%'],
         center: ['15%', '25%'],
         itemStyle: {
             normal: {
                 label: {
                     show: false
                 },
                 labelLine: {
                     show: false
                 }
             }
         },
         label: {
           show: true,
           position: 'center'
         },
         hoverAnimation: false,
         data: [{
             value: datas.value,
             name: '',
             itemStyle: {
                 normal: {
                     color: { // 完成的圆环的颜色
                         colorStops: datas.ringColor
                     },
                     label: {
                         show: false
                     },
                     labelLine: {
                         show: false
                     }
                 }
             }
         }, {
             name: '',
             value: 100 - datas.value
         }]
     }
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
