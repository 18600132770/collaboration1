
<template>
  <div id="funnelbarchart2" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'FunnelBarChart2',
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
  this.myChart = echarts.init(this.$el)

  this.updateChart()
}

function updateChart() {
  var echartData = [
    { data: [1184], name: '10k以下' },
    { data: [789], name: '10k-15k' },
    { data: [689], name: '15-20k'},
    { data: [231], name: '20k以上' },
  ]

  var option = {
    title:[
    {
      subtext: '薪酬',
      left: '10%',
      top: '15',
      textAlign: 'center',
      subtextStyle:{
        color: '#eee'
      }
    }],
    grid: {
      containLabel: true,
      left: 80,
      right: 0,
      top: 10,
      bottom: 30
    },
    tooltip: {
      show: false
    },
    legend: {
      show: false
    },
    xAxis: {
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      splitLine: {
        show: false
      },
      axisLabel: {
        show: false
      }
    },
    yAxis: {
      data: ['sss'],
      axisLabel: {
        show: false
      },
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      splitLine: {
        show: false
      }
    },
    color: [
      '#FDB36A',
      '#73DDFF',
      '#73ACFF',
      '#FDD56A',
      '#9E87FF',
      '#FD866A',
      '#58D5FF',
      '#389af4', 
      '#dfeaff',],
    series: []
  }

  echartData.forEach((item) => {
    option.series.push({
      type: 'bar',
      name: item.name,
      stack: '1',
      label: {
        normal: {
          show: true,
          position: [5, 5],
          formatter: `{value|${item.data}}\n{name|${item.name}}`,
          align: 'left',
          textStyle: {
            color: '#fff',
            rich: {
              name: {
                fontSize: '12',
                fontWeight: 500,
                color: '#eee'
              },
              value: {
                fontSize: '12',
                fontWeight: 500,
                color: '#fff'
              }
            }
          }
        }
      },
      barWidth: 15,
      data: item.data,
      itemStyle: {
        normal: {
          barBorderRadius: [0]
        }
      }
    })
    /* if (index === 0) {
      option.series[index].itemStyle.normal.barBorderRadius = [5, 0, 0, 5]
    } else if (index === echartData.length - 1) {
      option.series[index].itemStyle.normal.barBorderRadius = [0, 5, 5, 0]
    } else {
      return
    } */
  })

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
