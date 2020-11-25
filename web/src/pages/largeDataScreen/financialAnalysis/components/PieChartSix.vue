
<template>
  <div id="pie-chart-three" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'PieChartThree',
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
  this.myChart = echarts.init(document.getElementById('pie-chart-three'))

  this.updateChart()
}

function updateChart() {
  var seriesData = [
    {
      name: '博士',
      value: '400',
    },
    {
      name: '研究生',
      value: '400',
    },
    {
      name: '本科',
      value: '530',
    },
    {
      name: '其他',
      value: '400',
    }]
  var seriesData1 = [
    {
      name: '国内',
      value: '300',
    },
    {
      name: '海外',
      value: '200',
    }]
   var seriesData2 = [
    {
      name: '男',
      value: '3000',
    },
    {
      name: '女',
      value: '2000',
    }]
  var seriesData3 = [
    {
      name: '汉族',
      value: '4000',
    },
    {
      name: '其他',
      value: '200',
    }
  ]

  var seriesData4 = [
    {
      name: '高级',
      value: '500',
    },
    {
      name: '中级',
      value: '1000',
    },
    {
      name: '初级',
      value: '2000',
    }
  ]

  var seriesData5 = [
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
  seriesData = seriesData.sort(function (a, b) {
    return a.value - b.value
  })
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
    title:[{
      subtext: '学历',
      left: '16%',
      top: '70%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '',
      left: '24%',
      top: '70%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '性别',
      left: '40%',
      top: '70%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '民族',
      left: '56%',
      top: '70%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '职称',
      left: '72%',
      top: '70%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '政治身份',
      left: '88%',
      top: '70%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
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
        color: '#fff',
        fontSize: 12
      },
      data: legendData,
    }, */
    series: [
      {
        type: 'pie',
        z: 3,
        center: ['8%', '40%'],
        radius: ['38%', '50%'],
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
        data: seriesData,
      },
      {
        type: 'pie',
        z: 3,
        center: ['24%', '40%'],
        radius: ['38%', '50%'],
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
        data: seriesData1.sort(function (a, b) {
          return a.value - b.value
        }),
      },
      {
        type: 'pie',
        z: 3,
        center: ['40%', '40%'],
        radius: ['38%', '50%'],
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
        data: seriesData2.sort(function (a, b) {
          return a.value - b.value
        }),
      },

      {
        type: 'pie',
        z: 3,
        center: ['56%', '40%'],
        radius: ['38%', '50%'],
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
        data: seriesData3,
      },
      {
        type: 'pie',
        z: 3,
        center: ['72%', '40%'],
        radius: ['38%', '50%'],
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
        data: seriesData4.sort(function (a, b) {
          return a.value - b.value
        }),
      },
      {
        type: 'pie',
        z: 3,
        center: ['88%', '40%'],
        radius: ['38%', '50%'],
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
        data: seriesData5.sort(function (a, b) {
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
