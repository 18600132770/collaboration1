
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
  let data = [582, 421.2, 622.1, 625.3, 265, 224]
  let indicator = [
    {
      name: '机械',
      max: 1000,
    },
    {
      name: '人工',
      max: 1000,
    },
    {
      name: '材料',
      max: 1000,
    },
    {
      name: '其他成本',
      max: 1000,
    },
    {
      name: '管理费',
      max: 1000,
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
        // name: '家庭融合包',
        type: 'radar',
        data: [data],
        // value:14,
        label: {
          show: true,
          formatter: function (params) {
            return params.value + '万'
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
          color: '#7D77F1',
          opacity: 0.6,
        },
      },
      {
        type: 'radar',
        data: [[1000, 1000, 1000, 1000, 1000, 1000]],
        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#4175F5',
        },

        areaStyle: {
          color: '#4175F5',
          opacity: 0.06,
        },
      },
      {
        type: 'radar',
        data: [[900, 900, 900, 900, 900, 900]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#2C72C8',
        },

        areaStyle: {
          color: '#2C72C8',
          opacity: 0.12,
        },
      },
      {
        type: 'radar',
        data: [[800, 800, 800, 800, 800, 800]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#4175F5',
        },

        areaStyle: {
          color: '#4175F5',
          opacity: 0.18,
        },
      },
      {
        type: 'radar',
        data: [[700, 700, 700, 700, 700, 700]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#4175F5',
        },

        areaStyle: {
          color: '#4175F5',
          opacity: 0.19,
        },
      },
      {
        type: 'radar',
        data: [[600, 600, 600, 600, 600, 600]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#4175F5',
        },

        areaStyle: {
          color: '#4175F5',
          opacity: 0.17,
        },
      },
      {
        type: 'radar',
        data: [[500, 500, 500, 500, 500, 500]],
        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#4175F5',
        },

        areaStyle: {
          color: '#4175F5',
          opacity: 0.16,
        },
      },
      {
        type: 'radar',
        data: [[400, 400, 400, 400, 400, 400]],

        symbol: 'none',
        lineStyle: {
          width: 0,
        },
        itemStyle: {
          color: '#4175F5',
        },

        areaStyle: {
          color: '#4175F5',
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
