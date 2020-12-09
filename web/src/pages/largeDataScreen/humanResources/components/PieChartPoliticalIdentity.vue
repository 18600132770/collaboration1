
<template>
  <div id="pie-chart-political-identity" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'PieChartPoliticalIdentity',
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
  this.myChart = echarts.init(document.getElementById('pie-chart-political-identity'))

  this.updateChart()
}

function updateChart() {

  var seriesData = [
    {
      name: '党员',
      value: '500',
    },
    {
      name: '群众',
      value: '1000',
    },
    {
      name: '民主党派',
      value: '2000',
    }
  ]

  // var legendData = ['铁路', '轨道交通', '市政', '路桥', '房建', '环保', '水运']
  var colorList = [
    '#FDB36A',
    '#73DDFF',
    '#73ACFF',
    '#FDD56A',
    '#9E87FF',
    '#FD866A',
    '#58D5FF',
    '#389af4', '#dfeaff',
    '#ff8c37', '#ffdcc3',
    // ['#ffc257', '#ffedcc'],
    // ['#fd6f97', '#fed4e0'],
    '#a181fc', '#e3d9fe'
  ]
  let option = {
    title:[
    {
      subtext: '政治身份',
      left: '50%',
      top: '70%',
      textAlign: 'center',
      subtextStyle:{
        color: '#ddd'
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
          '人数：' +
          parms.data.value +
          '个</br>' +
          '占比：' +
          parms.percent +
          '%'
        return str
      },
    },
    /* legend: {
      type: 'scroll',
      left: 'center',
      align: 'auto',
      itemWidth: 10,
      itemHeight: 10,
      textStyle: {
        color: '#ddd',
        fontSize: 12
      },
      data: legendData,
    }, */
    series: [
      {
        type: 'pie',
        z: 3,
        center: ['50%', '40%'],
        radius: ['54%', '62%'],
        clockwise: true,
        avoidLabelOverlap: false,
        hoverOffset: 5,
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
              '人\n' +
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
        data: seriesData.sort(function (a, b) {
          return a.value - b.value
        }),
      }
    ],
  }
  // this.myChart && this.myChart.clear()
  this.myChart.setOption(option, false)

  const _this = this
  let dataIndex = 0
  let timer = setInterval(function () {
    /* if (dataIndex > legendData.length - 1) {
      dataIndex = 0
    } */
    _this.myChart.dispatchAction({
      type: 'downplay', // 触发的行为
      dataIndex: dataIndex
    })

    dataIndex = (dataIndex + 1) % seriesData.length;

    _this.myChart.dispatchAction({
      type: 'highlight', // 触发的行为
      dataIndex: dataIndex
    })
  }, 3000)

  this.$once('hook:beforeDestroy', () => {            
    clearInterval(timer);                                    
  })
}

function onResize() {
  this.width = this.$el.offsetWidth
  this.height = this.$el.offsetHeight

  this.myChart && this.myChart.resize()
}
</script>

<style>
</style>
