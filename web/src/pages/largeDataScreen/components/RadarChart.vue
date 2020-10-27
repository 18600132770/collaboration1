
<template>
  <div id="radar-chart" :style="{ height: height, width: width }"></div>
</template>

<script>
// https://gallery.echartsjs.com/editor.html?c=xOJOlagFK4
import echarts from 'echarts'

export default {
  name: 'RadarChart',
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
      timer: null,
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

    clearInterval(this.timer)
  },
}

function initBarChart() {
  this.myChart = echarts.init(document.getElementById('radar-chart'))

  this.updateChart()
}

function updateChart() {
  let data = [50,	42,	51,	6.3, 3]
  let data1 = [81.69934641,	68.23529412,	86.77419355,	123.5294118,	88.23529412]
  // let sum = [50,	42,	51,	6.3, 3].reduce((s, item) => {
  //   return s + item
  // }, 0)
  let indicator = [
    {
      name: '机械',
      max: 100,
    },
    {
      name: '人工',
      max: 100,
    },
    {
      name: '材料',
      max: 100,
    },
    {
      name: '其他成本',
      max: 100,
    },
    {
      name: '管理费',
      max: 100,
    }
  ]

  let option = {
    radar: {
      center: ['50%', '50%'],
      radius: '75%',
      name: {
        formatter: function (name) {
          let arr
          arr = ['{a|' + name + '}']

          return arr.join('\n')
        },
        textStyle: {
          rich: {
            //根据文字的组设置格式
            a: {
              color: '#BCDCFF',
              fontSize: 14,
              fontWeight: 600,
              fontFamily: 'Source Han Sans CN',
            }
          },
        },
      },
      nameGap: 0,
      indicator: indicator,
      splitLine: {
        show: false,
      },
      splitArea: {
        show: false,
      },
      axisLine: {
        show: false,
      },
    },
    series: [
      {
        type: 'radar',
        data: [data1],
        // value:14,
        label: {
          show: true,
          formatter: function (params) {
            return data[params.dimensionIndex] + '亿'
          },
          color: '#8E88FE',
          // position:[-20,-10,-10,-10],
          align: 'right',
          distance: 10
        },
        symbolSize: [6, 6],
        lineStyle: {
          //边缘颜色
          width: 0,
        },
        itemStyle: {
          borderWidth: 1,
          color: '#fff',
          borderColor: '#F2F063',
        },
        areaStyle: {
          color: '#ff7e5f',
          opacity: 0.3,
        },
        zlevel: 100
      },
      {
        type: 'radar',
        data: [[100, 100, 100, 100, 100, 100]],
        symbol: 'none',
        lineStyle: {
          width: 1,
          opacity: 0.3
        },
        itemStyle: {
          color: '#56ab2f',
        },

        areaStyle: {
          color: '#56ab2f',
          opacity: 0.16,
        },
      },
      {
        type: 'radar',
        data: [[90, 90, 90, 90, 90, 90]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#2C72C8',
        },

        areaStyle: {
          color: '#56ab2f',
          opacity: 0.12,
        },
      },
      {
        type: 'radar',
        data: [[80, 80, 80, 80, 80, 80]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#56ab2f',
        },

        areaStyle: {
          color: '#56ab2f',
          opacity: 0.18,
        },
      },
      {
        type: 'radar',
        data: [[70, 70, 70, 70, 70, 70]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#56ab2f',
        },

        areaStyle: {
          color: '#56ab2f',
          opacity: 0.19,
        },
      },
      {
        type: 'radar',
        data: [[60, 60, 60, 60, 60, 60]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#56ab2f',
        },

        areaStyle: {
          color: '#56ab2f',
          opacity: 0.17,
        },
      },
      {
        type: 'radar',
        data: [[50, 50, 50, 50, 50, 50]],
        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#56ab2f',
        },

        areaStyle: {
          color: '#56ab2f',
          opacity: 0.16,
        },
      },
      {
        type: 'radar',
        data: [[40, 40, 40, 40, 40, 40]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#56ab2f',
        },

        areaStyle: {
          color: '#56ab2f',
          opacity: 0.13,
        },
      },
    ],
  }
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
