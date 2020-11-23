
<template>
  <div id="funnel-chart" :style="{ height: height, width: width }"></div>
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
  this.myChart = echarts.init(document.getElementById('funnel-chart'))

  this.updateChart()
}

function updateChart() {
  let option = {
    title:[{
      subtext: '职级人数',
      left: '13%',
      top: '85%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '薪酬',
      left: '37%',
      top: '85%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '年龄',
      left: '61%',
      top: '85%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    },
    {
      subtext: '工龄',
      left: '85%',
      top: '85%',
      textAlign: 'center',
      subtextStyle:{
        color: '#333'
      }
    }],
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c}',
    },

    series: [
      {
        name: '职级人数',
        type: 'funnel',
        width: '20%',
        height: '77%',
        left: '4%',
        top: '15%',
        sort: 'none', // ascending
        label: {
          show: true,
          position: 'inside',
        },

        data: [
          { value: 100, name: '高级' },
          { value: 600, name: '中级' },
          { value: 900, name: '初级' },
        ],
      },
      {
        name: '薪酬',
        type: 'funnel',
        width: '20%',
        height: '77%',
        left: '28%',
        top: '15%',
        sort: 'none',
        label: {
          show: true,
          position: 'inside',
        },
        data: [
          { value: 30, name: '20k以上' },
          {
            value: 60,
            name: '15-20k',
          },
          { value: 90, name: '10k-15k' },
          { value: 90, name: '10k以下' },
        ],
      },
      {
        name: '年龄',
        type: 'funnel',
        width: '20%',
        height: '77%',
        left: '52%',
        top: '15%',
        sort: 'none',
        label: {
          show: true,
          position: 'inside',
          // fontSize:21,
        },
        data: [
          { value: 130, name: '56及以上' },
          { value: 260, name: "46~55" },
          { value: 690, name: '36~45' },
          { value: 2190, name: '26~35' },
          { value: 1010, name: '25及以下' },
        ],
      },
      {
        name: '工龄',
        type: 'funnel',
        width: '20%',
        height: '77%',
        left: '76%',
        top: '15%',
        sort: 'none',
        label: {
          show: true,
          position: 'inside',
          // fontSize:21,
        },
        data: [
          { value: 130, name: '31及以上' },
          { value: 260, name: "21~30" },
          { value: 690, name: '16~20' },
          { value: 1190, name: '11~15' },
          { value: 1010, name: '5~10' },
          { value: 1010, name: '5年以下' },
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
