
<template>
  <div id="bar-chart" :style="{height: height, width: width}"></div>
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
    document.getElementById('bar-chart')
  )

  this.updateChart()
}

function updateChart () {
  let data = [9, 19, 18, 16, 15, 15, 13, 12].reverse()
  // let data1 = [161, 186, 181, 175, 160, 152, 140, 123].reverse()

  let option = {
    // backgroundColor: '#031245',
    "textStyle": {
        "color": "#c0c3cd",
        "fontSize": 14
    },
    "toolbox": {
        "show": false,
        "feature": {
            "saveAsImage": {
                "backgroundColor": "#031245"
            },
            "restore": {}
        },
        "iconStyle": {
            "borderColor": "#c0c3cd"
        }
    },
    "legend": {
        "top": 10,
        "itemWidth": 8,
        "itemHeight": 8,
        "icon": "circle",
        "left": "center",
        "padding": 0,
        "textStyle": {
            "color": "#c0c3cd",
            "fontSize": 14,
            "padding": [2, 0, 0, 0]
        }
    },
    "color": ["#63caff", "#49beff", "#03387a", "#03387a", "#03387a", "#6c93ee", "#a9abff", "#f7a23f", "#27bae7", "#ff6d9d", "#cb79ff", "#f95b5a", "#ccaf27", "#38b99c", "#93d0ff", "#bd74e0", "#fd77da", "#dea700"],
    "grid": {
        "containLabel": true,
        "left": 20,
        "right": 20,
        "bottom": 10,
        "top": 30
    },
    "xAxis": {
        "nameTextStyle": {
            "color": "#c0c3cd",
            "padding": [0, 0, -10, 0],
            "fontSize": 12
        },
        "axisLabel": {
            "color": "#c0c3cd",
            "fontSize": 12,
            "interval": 0
        },
        "axisTick": {
            "lineStyle": {
                "color": "#384267",
                "width": 1
            },
            "show": true
        },
        "splitLine": {
            "show": false
        },
        "axisLine": {
            "lineStyle": {
                "color": "#384267",
                "width": 1,
                "type": "dashed"
            },
            "show": true
        },
        "data": ["1", "2", "3", "4", "5", "6", "7", "8"].map(item => `${item}月`),
        "type": "category"
    },
    "yAxis": {
        "name": "亿元",
        "nameTextStyle": {
            "color": "#c0c3cd",
            "padding": [0, 0, -10, -15],
            "fontSize": 10
        },
        "axisLabel": {
            "color": "#c0c3cd",
            "fontSize": 12
        },
        "axisTick": {
            "lineStyle": {
                "color": "#384267",
                "width": 1
            },
            "show": true
        },
        "splitLine": {
            "show": true,
            "lineStyle": {
                "color": "#384267",
                "type": "dashed"
            }
        },
        "axisLine": {
            "lineStyle": {
                "color": "#384267",
                "width": 1,
                "type": "dashed"
            },
            "show": true
        }
    },
    "series": [{
        "data": data,
        "type": "bar",
        "barMaxWidth": "auto",
        "barWidth": 20,
        "itemStyle": {
            "color": {
                "x": 0,
                "y": 0,
                "x2": 0,
                "y2": 1,
                "type": "linear",
                "global": false,
                "colorStops": [{
                    "offset": 0,
                    "color": "#0b9eff"
                }, {
                    "offset": 1,
                    "color": "#63caff"
                }]
            }
        },
        "label": {
            "show": true,
            "position": "top",
            "distance": 10,
            "color": "#fff",
            fontSize: 12
        }
    }, {
        "data": [1, 1, 1, 1, 1, 1, 1, 1],
        "type": "pictorialBar",
        "barMaxWidth": "20",
        "symbol": "diamond",
        "symbolOffset": [0, "50%"],
        "symbolSize": [20, 12]
    }, {
        "data": data,
        "type": "pictorialBar",
        "barMaxWidth": "20",
        "symbolPosition": "end",
        "symbol": "diamond",
        "symbolOffset": [0, "-50%"],
        "symbolSize": [20, 10],
        "zlevel": 2
    }],
    "tooltip": {
      "trigger": "axis",
      "show": false
    }
  }
  // this.myChart && this.myChart.clear()
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
