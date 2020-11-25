
<template>
  <div id="line-chart-2" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'LineChart',
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
  this.myChart = echarts.init(document.getElementById('line-chart-2'))

  this.updateChart()
}

function updateChart() {
  var data = {
    city: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11'],
    num: ['20', '30', '12', '45', '30', '20', '30', '12', '65', '40', '20'],
  }
  let option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        lineStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              {
                offset: 0,
                color: 'rgba(255,255,255,0)', // 0% 处的颜色
              },
              {
                offset: 0.5,
                color: 'rgba(255,255,255,1)', // 100% 处的颜色
              },
              {
                offset: 1,
                color: 'rgba(255,255,255,0)', // 100% 处的颜色
              },
            ],
            global: false, // 缺省为 false
          },
        },
      },
    },
    grid: {
      top: '20%',
      left: '10%',
      right: '5%',
      bottom: '25%',
      // containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        boundaryGap: true,
        axisLine: {
          //坐标轴轴线相关设置。数学上的x轴
          show: true,
          lineStyle: {
            color: 'color:"#092b5d"',
          },
        },
        axisLabel: {
          //坐标轴刻度标签的相关设置
          textStyle: {
            color: '#24c4ff',
            margin: 15,
          },
          formatter: function (data) {
            return data + '月'
          },
        },
        axisTick: {
          show: false,
        },
        data: data.city,
      },
    ],
    yAxis: [
      {
        name: '亿',
        nameTextStyle: {
          color: '#24c4ff'
        },
        splitLine: {
          show: false,
          lineStyle: {
            color: '#092b5d',
          },
        },
        axisLine: {
          show: true,
          lineStyle: {
            color: '#092b5d',
          },
        },
        axisLabel: {
          show: true,
          textStyle: {
            color: '#24c4ff',
          },
          formatter: function (value) {
            if (value === 0) {
              return value
            }
            return value
          },
        },
        axisTick: {
          show: false,
        },
      },
    ],
    series: [
      {
        name: '营业收入',
        type: 'line',
        symbol: 'circle', // 默认是空心圆（中间是白色的），改成实心圆
        showAllSymbol: true,
        symbolSize: 8,
        lineStyle: {
          normal: {
            color: '#7c80f4', // 线条颜色
          },
          borderColor: 'rgba(0,0,0,.4)',
        },
        itemStyle: {
          color: 'rgba(14,30,73,1)',
          borderColor: '#646ace',
          borderWidth: 2,
        },
        label: {
          normal: {
            show: true,
            position: 'top',
            formatter: [' {a|{c}}'].join(','),
            rich: {
              a: {
                color: '#fff',
                align: 'center',
              },
            },
          },
        },
        tooltip: {
          show: true,
        },
        areaStyle: {
          //区域填充样式
          normal: {
            //线性渐变，前4个参数分别是x0,y0,x2,y2(范围0~1);相当于图形包围盒中的百分比。如果最后一个参数是‘true’，则该四个值是绝对像素位置。
            color: new echarts.graphic.LinearGradient(
              0,
              0,
              0,
              1,
              [
                {
                  offset: 0,
                  color: 'rgba(124, 128, 244,.3)',
                },
                {
                  offset: 1,
                  color: 'rgba(124, 128, 244, 0)',
                },
              ],
              false
            ),
            shadowColor: 'rgba(53,142,215, 0.9)', //阴影颜色
            shadowBlur: 20, //shadowBlur设图形阴影的模糊大小。配合shadowColor,shadowOffsetX/Y, 设置图形的阴影效果。
          },
        },
        data: data.num,
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
