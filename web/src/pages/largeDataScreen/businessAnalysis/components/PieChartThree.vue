
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
  var data = [
    {
      name: '部门',
      value: 54,
    },
    {
      name: '区域',
      value: 44,
    },
    {
      name: '领域',
      value: 35,
    }
  ]

  var titleArr = [],
    seriesArr = []
  let colors = [
    ['#389af4', '#dfeaff'],
    ['#ff8c37', '#ffdcc3'],
    // ['#ffc257', '#ffedcc'],
    // ['#fd6f97', '#fed4e0'],
    ['#a181fc', '#e3d9fe'],
  ]
  data.forEach(function (item, index) {
    titleArr.push({
      text: item.name,
      left: index * 35 + 15 + '%',
      top: '75%',
      textAlign: 'center',
      textStyle: {
        fontWeight: 'normal',
        fontSize: '16',
        color: colors[index][0],
        textAlign: 'center',
      },
    })
    seriesArr.push({
      name: item.name,
      type: 'pie',
      clockWise: false,
      radius: [40, 50],
      itemStyle: {
        normal: {
          color: colors[index][0],
          shadowColor: colors[index][0],
          shadowBlur: 0,
          label: {
            show: false,
          },
          labelLine: {
            show: false,
          },
        },
      },
      hoverAnimation: false,
      center: [index * 35 + 15 + '%', '50%'],
      data: [
        {
          value: item.value,
          label: {
            normal: {
              formatter: function (params) {
                return params.value
              },
              position: 'center',
              show: true,
              textStyle: {
                fontSize: '20',
                fontWeight: 'bold',
                color: colors[index][0],
              },
            },
          },
        },
        {
          value: 100 - item.value,
          name: 'invisible',
          itemStyle: {
            normal: {
              color: colors[index][1],
            },
            emphasis: {
              color: colors[index][1],
            },
          },
        },
      ],
    })
  })

  let option = {
    title: titleArr,
    series: seriesArr,
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
