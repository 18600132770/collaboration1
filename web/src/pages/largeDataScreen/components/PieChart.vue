
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
      name: '铁路',
      value: '40000',
    },
    {
      name: '轨道交通',
      value: '40000',
    },
    {
      name: '市政',
      value: '53000',
    },
    {
      name: '路桥',
      value: '40000',
    },
    {
      name: '房建',
      value: '60000',
    },
    {
      name: '环保',
      value: '10000',
    },
    {
      name: '水运',
      value: '20000',
    },
  ]
  var seriesData1 = [
    {
      name: '房建',
      value: '30000',
    },
    {
      name: '铁路',
      value: '20000',
    },
    {
      name: '轨道交通',
      value: '20000',
    },
    {
      name: '市政',
      value: '33000',
    },
    {
      name: '路桥',
      value: '20000',
    },
    {
      name: '水运',
      value: '10000',
    },
    {
      name: '环保',
      value: '5000',
    }
  ]
  seriesData = seriesData.sort(function (a, b) {
    return a.value - b.value
  })
  var legendData = ['铁路', '轨道交通', '市政', '路桥', '房建', '环保', '水运']
  var colorList = [
    '#FDB36A',
    '#73DDFF',
    '#73ACFF',
    '#FDD56A',
    '#9E87FF',
    '#FD866A',
    '#58D5FF',
  ]
  let option = {
    title:[{
      subtext: '合同额',
      left: '25%',
      top: '78%',
      textAlign: 'center',
      subtextStyle:{
        color: '#fff'
      }
    }, {
      subtext: '净利润',
      left: '75%',
      top: '78%',
      textAlign: 'center',
      subtextStyle:{
        color: '#fff'
      }
    }],
    tooltip: {
      show: true,
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
          '合同额：' +
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
        center: ['25%', '50%'],
        radius: ['28%', '45%'],
        clockwise: true,
        avoidLabelOverlap: false,
        hoverOffset: 10,
        itemStyle: {
          normal: {
            color: function (params) {
              return colorList[params.dataIndex]
            },
          },
        },
        label: {
          show: false,
          position: 'center',
          formatter: function (parms) {
            var str =
              parms.data.name +
              '\n' +
              // '合同额：' +
              parms.data.value +
              '万元\n' +
              // '占比：' +
              parms.percent +
              '%'
            return str
          },
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '10',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false,
        },
        data: seriesData,
      },
      {
        type: 'pie',
        z: 3,
        center: ['75%', '50%'],
        radius: ['28%', '45%'],
        clockwise: true,
        avoidLabelOverlap: false,
        hoverOffset: 10,
        itemStyle: {
          normal: {
            color: function (params) {
              return colorList[params.dataIndex]
            },
          },
        },
        label: {
          show: false,
          position: 'center',
          formatter: function (parms) {
            var str =
              parms.data.name +
              '\n' +
              // '合同额：' +
              parms.data.value +
              '万元\n' +
              // '占比：' +
              parms.percent +
              '%'
            return str
          },
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '10',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false,
        },
        data: seriesData1.sort(function (a, b) {
          return a.value - b.value
        }),
      }
    ],
  }
  this.myChart.setOption(option, false)

  /* const _this = this
  let dataIndex = 0
  setInterval(function () {
    if (dataIndex > legendData.length - 1) {
      dataIndex = 0
    }
    _this.myChart.dispatchAction({
      type: 'showTip', // 触发的行为
      dataIndex: dataIndex++
    })
    console.log('00', dataIndex)
  }, 3000) */
}

function onResize() {
  this.width = this.$el.offsetWidth
  this.height = this.$el.offsetHeight

  this.myChart && this.myChart.resize()
}
</script>

<style>
</style>
