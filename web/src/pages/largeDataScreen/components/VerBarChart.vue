
<template>
  <div id="ver-bar-chart" :style="{height: height, width: width}"></div>
</template>

<script>
// https://gallery.echartsjs.com/editor.html?c=xMQLI9VAFHa
import echarts from 'echarts'

export default {
  name: 'BtnStatistics',
  props: {
    percentData: {
      type: Object,
      // required: true
    }
  },
  data () {
    return {
      barData: [],
      legendSelected: {},
      myChart: null,

      height: '100%',
      width: '100%'
    }
  },
  computed: {
    pieData () {
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
    pieData () {
      this.pieData.map(item => item.name).forEach(key => {
        this.legendSelected[key] = true
      })
    },
    // lengend变化时请求接口
    legendSelected () {
      let type = []
      for (let key in this.legendSelected) {
        if (this.legendSelected[key]) {
          type.push(key)
        }
      }
      this.getWords(type.join(','), this.updateChart)
    }
  },
  mounted () {
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
  beforeDestroy () {
    this.myChart && this.myChart.clear()
    this.myChart = null
  }
}

function initBarChart () {
  this.myChart = echarts.init(
    document.getElementById('ver-bar-chart')
  )

  this.updateChart()
}

function updateChart () {
  var salvProName =["安徽项目","河南项目","浙江项目","湖北项目","贵州项目","江西项目","江苏项目","四川项目"];
  var salvProValue =[100,81,54,44,35,17,74,72];
  var salvProMax =[];//背景按最大值
  for (let i = 0; i < salvProValue.length; i++) {
      salvProMax.push(salvProValue[0])
  }
  let option = {
    grid: {
        left: '2%',
        right: '2%',
        bottom: '2%',
        top: '2%',
        containLabel: true
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'none'
        },
        formatter: function(params) {
            return params[0].name  + ' : ' + params[0].value
        }
    },
    xAxis: {
        show: false,
        type: 'value'
    },
    yAxis: [{
        type: 'category',
        inverse: true,
        axisLabel: {
            show: true,
            textStyle: {
                color: '#fff'
            },
        },
        splitLine: {
            show: false
        },
        axisTick: {
            show: false
        },
        axisLine: {
            show: false
        },
        data: salvProName
    }, {
        type: 'category',
        inverse: true,
        axisTick: 'none',
        axisLine: 'none',
        show: true,
        axisLabel: {
            textStyle: {
                color: '#ffffff',
                fontSize: '12'
            },
        },
        data:salvProValue
    }],
    series: [{
            name: '值',
            type: 'bar',
            zlevel: 1,
            itemStyle: {
                normal: {
                    barBorderRadius: 30,
                    color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
                        offset: 0,
                        color: 'rgb(57,89,255,1)'
                    }, {
                        offset: 1,
                        color: 'rgb(46,200,207,1)'
                    }]),
                },
            },
            barWidth: 10,
            data: salvProValue
        },
        {
            name: '背景',
            type: 'bar',
            barWidth: 10,
            barGap: '-100%',
            data: salvProMax,
            itemStyle: {
                normal: {
                    color: 'rgba(24,31,68,1)',
                    barBorderRadius: 30,
                }
            },
        },
    ]
};// this.myChart && this.myChart.clear()
  this.myChart.setOption(option, false)
}

function onResize () {
  this.width = this.$el.offsetWidth
  this.height = this.$el.offsetHeight

  this.myChart && this.myChart.resize()
}
</script>

<style>

</style>
