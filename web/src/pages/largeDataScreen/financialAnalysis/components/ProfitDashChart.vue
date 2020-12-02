
<template>
  <div id="profit-dash-chart" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'ProfitDashChart',
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
  this.myChart = echarts.init(document.getElementById('profit-dash-chart'))

  this.updateChart()
}

function updateChart() {
  var dataArr = 140
  var colorSet = {
    color: '#468EFD',
  }
  let option = {
    tooltip: {
      formatter: '{a} <br/>{b} : {c}%',
    },

    series: [
      {
        name: '内部进度条',
        type: 'gauge',
        // center: ['20%', '50%'],
        radius: '40%',

        splitNumber: 10,
        axisLine: {
          lineStyle: {
            color: [
              [dataArr / 200, colorSet.color],
              [1, '#111F42'],
            ],
            width: 8,
          },
        },
        axisLabel: {
          show: false,
        },
        axisTick: {
          show: false,
        },
        splitLine: {
          show: false,
        },
        itemStyle: {
          show: false,
        },
        detail: {
          formatter: function (value) {
            if (value !== 0) {
              var num = Math.round(value)
              return parseInt(num).toFixed(0) + '万元'
            } else {
              return 0
            }
          },
          offsetCenter: [0, 82],
          textStyle: {
            padding: [0, 0, 0, 0],
            fontSize: 15,
            fontWeight: '700',
            color: colorSet.color,
          },
        },
        title: {
          //标题
          show: true,
          offsetCenter: [0, 56], // x, y，单位px
          textStyle: {
            color: '#fff',
            fontSize: 13, //表盘上的标题文字大小
            fontWeight: 400,
            fontFamily: 'PingFangSC',
          },
        },
        data: [
          {
            name: '已完成利润',
            value: dataArr,
          },
        ],
        pointer: {
          show: true,
          length: '75%',
          radius: '20%',
          width: 5, //指针粗细
        },
        animationDuration: 4000,
      },
      {
        name: '外部刻度',
        type: 'gauge',
        //  center: ['20%', '50%'],
        radius: '65%',
        min: 0, //最小刻度
        max:200, //最大刻度
        splitNumber: 10, //刻度数量
        startAngle: 225,
        endAngle: -45,
        axisLine: {
          show: true,
          lineStyle: {
            width: 1,
            color: [[1, 'rgba(0,0,0,0)']],
          },
        }, //仪表盘轴线
        axisLabel: {
          show: true,
          color: '#4d5bd1',
          distance: 25,
          formatter: function (v) {
            switch (v + '') {
              case '0':
                return '0'
              case '20':
                return '20'
              case '40':
                return '40'
              case '60':
                return '60'
              case '80':
                return '80'
              case '100':
                return '100'
              case '120':
                return '120'
              case '140':
                return '140'
              case '160':
                return '160'
              case '180':
                return '180'
              case '200':
                return '200'
            }
          },
        }, //刻度标签。
        axisTick: {
          show: true,
          splitNumber: 7,
          lineStyle: {
            color: colorSet.color, //用颜色渐变函数不起作用
            width: 1,
          },
          length: -8,
        }, //刻度样式
        splitLine: {
          show: true,
          length: -16,
          lineStyle: {
            color: colorSet.color, //用颜色渐变函数不起作用
          },
        }, //分隔线样式
        detail: {
          show: false,
        },
        pointer: {
          show: false,
        },
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
