<template>
  <div id="progress-gauge" :style="{ height: height, width: width }"></div>
</template>

<script>
// https://gallery.echartsjs.com/editor.html?c=xuZ49_DgJ3
import echarts from 'echarts'

export default {
  name: 'ProgressGauge',
  props: {
    percentData: {
      type: Number,
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
    percentData() {
      this.updateChart(this.percentData)
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
    // startTimer,
  },
  beforeDestroy() {
    this.myChart && this.myChart.clear()
    this.myChart = null

    clearInterval(this.timer)
  },
}

function initBarChart() {
  this.myChart = echarts.init(document.getElementById('progress-gauge'))

  // setTimeout(this.startTimer, 0)

  this.updateChart(this.percentData)
}

function updateChart(point) {
  /* const handred = 100

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
 */

  var data = {
    name: '贷款',
    value: [point],
    nAmount: 1566557.14,
  }

  let option = {
    // backgroundColor: '#03060F',
    title: {
      text: data.value[0] + '%',
      textStyle: {
        color: '#2A95F9',
        fontSize: 20,
      },
      // subtext: data.name + '占比',
      subtextStyle: {
        color: '#aaaaaa',
        fontSize: 13,
      },
      itemGap: 20,
      left: 'center',
      top: '45%',
    },
    graphic: [
      {
        type: 'text',
        z: 100,
        left: 'center',
        top: '95%',
        style: {
          fill: '#fff',
          // text: data.name + '总额' + data.nAmount + '万',
          // text: [
          //     '横轴表示温度，单位是°C',
          //     '纵轴表示高度，单位是km',
          //     '右上角有一个图片做的水印',
          //     '这个文本块可以放在图中各',
          //     '种位置'
          // ].join('\n'),
          font: '18px Microsoft YaHei',
        },
      },
    ],
    tooltip: {
      show: false,
      formatter: function(params) {
        return '<span style="color: #fff;">占比：' + params.value + '%</span>'
      },
    },
    angleAxis: {
      max: 100,
      clockwise: false, // 逆时针
      // 隐藏刻度线
      show: false,
    },
    radiusAxis: {
      type: 'category',
      show: true,
      axisLabel: {
        show: false,
      },
      axisLine: {
        show: false,
      },
      axisTick: {
        show: false,
      },
    },
    polar: [
      {
        center: ['50%', '50%'], //中心点位置
        radius: '100%', //图形大小
      },
    ],
    series: [
      {
        name: '大环',
        type: 'gauge',
        splitNumber: 12,
        radius: '82%',
        center: ['50%', '50%'],
        startAngle: 0,
        endAngle: 359.9999,
        axisLine: {
          show: false,
          lineStyle: {
            color: [[1, '#1f59a7']],
          },
        },
        axisTick: {
          show: false,
        },
        splitLine: {
          show: true,
          length: 12,
          lineStyle: {
            color: 'auto',
            width: 1.5,
          },
        },
        axisLabel: {
          show: false,
        },
        detail: {
          show: false,
        },
      },
      {
        name: '小环',
        type: 'gauge',
        splitNumber: 12,
        radius: '78%',
        center: ['50%', '50%'],
        startAngle: 0,
        endAngle: 359.9999,
        axisLine: {
          show: false,
        },
        axisTick: {
          show: true,
          lineStyle: {
            color: '#1f59a7',
            width: 2,
          },
          length: 10,
          splitNumber: 3,
        },
        splitLine: {
          show: false,
        },
        axisLabel: {
          show: false,
        },
        detail: {
          show: false,
        },
      },
      {
        type: 'bar',
        data: data.value,
        showBackground: true,
        polarIndex: 0,
        backgroundStyle: {
          color: '#022546',
          borderWidth: 10,
        },
        coordinateSystem: 'polar',
        roundCap: true,
        barWidth: 10,
        itemStyle: {
          normal: {
            opacity: 1,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: '#008BFF',
              },
              {
                offset: 1,
                color: '#0071FF',
              },
            ]),
            shadowBlur: 5,
            shadowColor: '#2A95F9',
          },
        },
      },
    ],
  }
  this.myChart.setOption(option, false)
}

/* function setdata() {
  let dataArr = []
  for (var i = 0; i < 100; i++) {
    if (i % 2 === 0) {
      dataArr.push({
        name: (i + 1).toString(),
        value: 10,
        itemStyle: {
          normal: {
            color: 'rgb(0,255,255,.3)',
          },
        },
      })
    } else {
      dataArr.push({
        name: (i + 1).toString(),
        value: 100,
        itemStyle: {
          normal: {
            color: 'rgb(0,0,0,0)',
            borderWidth: 0,
            borderColor: 'rgba(0,255,255,1)',
          },
        },
      })
    }
  }
  return dataArr
}
function startTimer() {
  const _this = this
  this.timer = setInterval(() => {
    let option = _this.myChart.getOption()
    option.series[1].startAngle = option.series[1].startAngle - 1
    _this.myChart.setOption(option)
  }, 100)
} */

function onResize() {
  this.width = this.$el.offsetWidth
  this.height = this.$el.offsetHeight

  this.myChart && this.myChart.resize()
}
</script>

<style lang="less" scoped>
#progress-gauge {
  // height: 180px !important;
  > div {
    height: 100%;
  }
}
</style>
