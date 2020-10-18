
<template>
  <div id="pie-chart" :style="{ height: height, width: width }"></div>
</template>

<script>
// https://gallery.echartsjs.com/editor.html?c=xuZ49_DgJ3
import echarts from 'echarts'

export default {
  name: 'ProgressGauge',
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
  this.myChart = echarts.init(document.getElementById('pie-chart'))

  this.updateChart()
}

function updateChart() {
  var seriesData = [
    {
      name: '项目一',
      value: '40000',
    },
    {
      name: '项目二',
      value: '40000',
    },
    {
      name: '项目三',
      value: '53000',
    },
    {
      name: '项目四',
      value: '40000',
    },
    {
      name: '项目五',
      value: '60000',
    },
    {
      name: '项目六',
      value: '10000',
    },
    {
      name: '项目七',
      value: '20000',
    },
  ]
  var legendData = ['项目一', '项目二', '项目三', '项目四', '项目五', '项目六', '项目七']
  var colorList = [
    '#73DDFF',
    '#73ACFF',
    '#FDD56A',
    '#FDB36A',
    '#FD866A',
    '#9E87FF',
    '#58D5FF',
  ]
  let option = {
    title: {
      text: '项目',
      x: 'center',
      y: 'center',
      textStyle: {
        color: '#fff',
      },
    },
    tooltip: {
      trigger: 'item',
      borderColor: 'rgba(255,255,255,.3)',
      backgroundColor: 'rgba(13,5,30,.6)',
      borderWidth: 1,
      padding: 5,
      formatter: function (parms) {
        var str =
          parms.marker +
          '' +
          parms.data.name +
          '</br>' +
          '投入：' +
          parms.data.value +
          '万元</br>' +
          '占比：' +
          parms.percent +
          '%'
        return str
      },
    },
    legend: {
      type: 'scroll',
      left: 'center',
      align: 'auto',
      itemWidth: 10,
      itemHeight: 10,
      textStyle: {
        color: '#fff',
        fontSize: 12
      },
      data: legendData,
    },
    series: [
      {
        type: 'pie',
        z: 3,
        center: ['50%', '50%'],
        radius: ['25%', '45%'],
        clockwise: true,
        avoidLabelOverlap: true,
        hoverOffset: 15,
        itemStyle: {
          normal: {
            color: function (params) {
              return colorList[params.dataIndex]
            },
          },
        },
        label: {
          show: true,
          position: 'outside',
          formatter: '{a|{d}%}\n{hr|}',
          rich: {
            hr: {
              backgroundColor: 't',
              borderRadius: 3,
              width: 3,
              height: 3,
              padding: [3, 3, 0, -12],
            },
            a: {
              padding: [-30, 15, -20, 15],
            },
          },
        },
        labelLine: {
          normal: {
            length: 20,
            length2: 30,
            lineStyle: {
              width: 1,
            },
          },
        },
        data: seriesData,
      },
      {
        name: '第一层环',
        type: 'pie',
        z: 2,
        tooltip: {
          show: false,
        },
        center: ['50%', '50%'],
        radius: ['45%', '58%'],
        hoverAnimation: false,
        clockWise: false,
        itemStyle: {
          normal: {
            // shadowBlur: 40,
            // shadowColor: 'rgba(0, 255, 255,.3)',
            color: 'rgba(1,15,80,.4)',
          },
          emphasis: {
            color: 'rgba(1,15,80,.4)',
          },
        },
        label: {
          show: false,
        },
        data: [100],
      },
      {
        name: '第二层环',
        type: 'pie',
        z: 1,
        tooltip: {
          show: false,
        },
        center: ['50%', '50%'],
        radius: ['58%', '76%'],
        hoverAnimation: false,
        clockWise: false,
        itemStyle: {
          normal: {
            // shadowBlur: 40,
            // shadowColor: 'rgba(0, 255, 255,.3)',
            color: 'rgba(0,15,69,.2)',
          },
          emphasis: {
            color: 'rgba(0,15,69,.2)',
          },
        },
        label: {
          show: false,
        },
        data: [100],
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
