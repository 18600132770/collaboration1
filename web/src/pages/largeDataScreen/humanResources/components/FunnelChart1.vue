
<template>
  <div id="funnel-chart-1" :style="{ height: height, width: width }"></div>
</template>

<script>
import echarts from 'echarts'

export default {
  name: 'FunnelChart',
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
  this.myChart = echarts.init(document.getElementById('funnel-chart-1'))

  this.updateChart()
}

function updateChart() {
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
      subtext: '职级人数',
      left: '20%',
      top: '77%',
      textAlign: 'center',
      subtextStyle:{
        color: '#ddd'
      }
    },
    {
      subtext: '薪酬',
      left: '65%',
      top: '77%',
      textAlign: 'center',
      subtextStyle:{
        color: '#ddd'
      }
    }],
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c}',
    },
    color: colorList,
    series: [
      {
        name: '职级人数',
        type: 'funnel',
        width: '20%',
        height: '67%',
        left: '12.5%',
        top: '15%',
        sort: 'ascending', // none
        minSize: '0%',
        maxSize: '100%',
        label: {
          show: true,
          formatter: "{b|{b}}\n {a|{c}}人",
          rich: {
            a: {
              color: 'rgba(222,240,255,1)',
              fontSize: 12
            },
            b: {
              color: 'rgba(101,166,196,1)',
              fontSize: 12
            }
          }
        },

        data: [
          { value: 332, name: '高级' },
          { value: 905, name: '中级' },
          { value: 1656, name: '初级' },
        ],
      },
      {
        name: '薪酬',
        type: 'funnel',
        width: '20%',
        height: '67%',
        left: '55%',
        top: '15%',
        sort: 'ascending',
        label: {
          show: true,
          formatter: "{b|{b}}\n {a|{c}}人",
          rich: {
            a: {
              color: 'rgba(222,240,255,1)',
              fontSize: 12
            },
            b: {
              color: 'rgba(101,166,196,1)',
              fontSize: 12
            }
          }
        },
        data: [
          { value: 231, name: '20k以上' },
          { value: 689, name: '15-20k'},
          { value: 789, name: '10k-15k' },
          { value: 1184, name: '10k以下' },
        ],
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
