
<template>
  <div id="degree-chart-1" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'DegreeChart',
  props: {
    percentData: {
      type: Object
      // required: true
    }
  },
  data() {
    return {
      barData: [],
      legendSelected: {},
      myChart: null,

      height: '100%',
      width: '100%'
    }
  },
  computed: {
    pieData() {
      let temp = []
      for (let key in this.percentData) {
        temp.push({
          name: key,
          value: this.percentData[key]
        })
      }
      return temp
    }
  },
  watch: {
    pieData() {
      this.pieData
        .map(item => item.name)
        .forEach(key => {
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
    }
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
    onResize
  },
  beforeDestroy() {
    this.myChart && this.myChart.clear()
    this.myChart = null
  }
}

function initBarChart() {
  this.myChart = echarts.init(document.getElementById('degree-chart-1'))

  this.updateChart()
}

function updateChart() {
  let data = [
    {
      name: '国内',
      value: 754
    },
    {
      name: '国外',
      value: 611
    }
  ]
  // let arrName = getArrayValue(data, 'name')
  // let arrValue = getArrayValue(data, 'value')
  let sumValue = 1000
  // let objData = array2obj(data, 'name')
  let optionData = getData(data)

  /* function getArrayValue(array, key) {
    var key1 = key || 'value'
    var res = []
    if (array) {
      array.forEach(function(t) {
        res.push(t[key1])
      })
    }
    return res
  }

  function array2obj(array, key) {
    var resObj = {}
    for (var i = 0; i < array.length; i++) {
      resObj[array[i][key]] = array[i]
    }
    return resObj
  } */

  function getData(data) {
    var res = {
      series: [
        {
          name: '大环',
          type: 'gauge',
          splitNumber: 15,
          radius: '75%',
          center: ['50%', '45%'],
          startAngle: 90,
          endAngle: -269.9999,
          axisLine: {
            show: false,
            lineStyle: {
              color: [[1, '#1f59a7']]
            }
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: true,
            length: 10,
            lineStyle: {
              color: 'auto',
              width: 1.5
            }
          },
          axisLabel: {
            show: false
          },
          detail: {
            show: false
          }
        },
        {
          name: '小环',
          type: 'gauge',
          splitNumber: 15,
          radius: '72%',
          center: ['50%', '45%'],
          startAngle: 90,
          endAngle: -269.9999,
          axisLine: {
            show: false
          },
          axisTick: {
            show: true,
            lineStyle: {
              color: '#1f59a7',
              width: 1
            },
            length: 8,
            splitNumber: 5
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            show: false
          },
          detail: {
            show: false
          }
        }
      ],
      yAxis: []
    }
    for (let i = 0; i < data.length; i++) {
      res.series.push({
        name: '学历',
        type: 'pie',
        clockWise: true,
        z: 2,
        hoverAnimation: false,
        radius: [68 - i * 15 + '%', 63 - i * 15 + '%'],
        center: ['50%', '45%'],
        label: {
          show: true,
          formatter: '{d}%',
          color: 'RGB(246,175,101)',
          fontSize: 13,
          position: 'inside'
        },
        labelLine: {
          show: false
        },
        data: [
          {
            value: data[i].value,
            name: data[i].name
          },
          {
            value: sumValue - data[i].value,
            name: '',
            itemStyle: {
              color: 'rgba(0,0,0,0)',
              borderWidth: 0
            },
            tooltip: {
              show: false
            },
            label: {
              show: false
            },
            hoverAnimation: false
          }
        ]
      })
      res.series.push({
        name: '背景线',
        type: 'pie',
        silent: true,
        z: 1,
        clockWise: true,
        hoverAnimation: false,
        radius: [66 - i * 15 + '%', 64 - i * 15 + '%'],
        center: ['50%', '45%'],
        label: {
          show: false
        },
        itemStyle: {
          label: {
            show: false
          },
          labelLine: {
            show: false
          },
          borderWidth: 3
        },
        data: [
          {
            value: 100,
            itemStyle: {
              color: 'RGB(12,64,128)',
              borderWidth: 0
            },
            tooltip: {
              show: false
            },
            hoverAnimation: false
          }
        ]
      })
      res.yAxis.push(data[i].name)
    }
    return res
  }

  let option = {
    color: [
      {
        type: 'linear',
        x: 0,
        y: 0,
        x2: 1,
        y2: 1,
        colorStops: [
          {
            offset: 0,
            color: 'rgba(10,31,95,1)'
          },
          {
            offset: 1,
            color: 'rgba(1,232,254,1)'
          }
        ],
        global: false
      }
    ],
    grid: {
      top: '5%',
      bottom: '74%',
      left: '50%',
      containLabel: false
    },
    yAxis: [
      {
        type: 'category',
        inverse: true,
        z: 3,
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          interval: 0,
          inside: false,
          textStyle: {
            color: 'RGB(78,184,252)',
            fontSize: 13
          },
          show: true
        },
        data: optionData.yAxis
      }
    ],
    xAxis: [
      {
        show: false
      }
    ],
    series: optionData.series
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
