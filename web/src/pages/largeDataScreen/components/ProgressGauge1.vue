
<template>
  <div id="progress-gauge1" :style="{ height: height, width: width }"></div>
</template>

<script>
// https://gallery.echartsjs.com/editor.html?c=xuZ49_DgJ3
import echarts from 'echarts'

export default {
  name: 'ProgressGauge1',
  props: {
    id: {
      type: String
    },
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
    onResize
  },
  beforeDestroy() {
    this.myChart && this.myChart.clear()
    this.myChart = null

    clearInterval(this.timer)
  },
}

function initBarChart() {
  this.myChart = echarts.init(document.getElementById('progress-gauge1'))

  this.updateChart()
}

function updateChart() {
  const handred = 100
  let point = 80.8

  let option = {
    title: {
      text: '已完成\n' + point + '%',
      x: 'center',
      y: 'center',
      textStyle: {
        fontWeight: 'normal',
        color: '#15A3FF',
        fontSize: '18',
      },
    },
    tooltip: {
      show: false,
      formatter: function (params) {
        return params.name + '：' + params.percent + ' %'
      },
    },
    legend: {
      show: false,
      itemGap: 12,
      data: ['占比', '剩余'],
    },

    series: [
      {
        name: 'circle',
        type: 'pie',
        clockWise: true,
        radius: ['50%', '66%'],
        itemStyle: {
          normal: {
            label: {
              show: false,
            },
            labelLine: {
              show: false,
            },
          },
        },
        hoverAnimation: false,
        data: [
          {
            value: point,
            name: '占比',
            itemStyle: {
              normal: {
                color: {
                  // 颜色渐变
                  colorStops: [
                    {
                      offset: 0,
                      color: '#4FADFD', // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: '#15A3FF', // 100% 处的颜色1
                    },
                  ],
                },
                label: {
                  show: false,
                },
                labelLine: {
                  show: false,
                },
              },
            },
          },
          {
            name: '剩余',
            value: handred - point,
            itemStyle: {
              normal: {
                color: 'rgba(255, 255, 255, .3)',
              },
            },
          },
        ],
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

<style lang="less" scoped>
  #progress-gauge1 {
    height: 180px !important;
    > div {
      height: 100%;
    }
  }
</style>
