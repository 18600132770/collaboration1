
<template>
  <div id="b-china-map1" :style="{ height: height, width: width }"></div>
</template>

<script>
// https://gallery.echartsjs.com/editor.html?c=xMQLI9VAFHa
import echarts from 'echarts'
import 'echarts/map/js/china.js'

export default {
  name: 'ChinaMap1',
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
  this.myChart = echarts.init(document.getElementById('b-china-map1'))

  this.updateChart()
}

function updateChart() {
  window.dataList = [
    {
      name: '南海诸岛',
      value: 0,
    },
    {
      name: '北京',
      value: 154,
    },
    {
      name: '天津',
      value: 13,
    },
    {
      name: '上海',
      value: 40,
    },
    {
      name: '重庆',
      value: 75,
    },
    {
      name: '河北',
      value: 13,
    },
    {
      name: '河南',
      value: 83,
    },
    {
      name: '云南',
      value: 11,
    },
    {
      name: '辽宁',
      value: 19,
    },
    {
      name: '黑龙江',
      value: 15,
    },
    {
      name: '湖南',
      value: 69,
    },
    {
      name: '安徽',
      value: 60,
    },
    {
      name: '山东',
      value: 39,
    },
    {
      name: '新疆',
      value: 4,
    },
    {
      name: '江苏',
      value: 31,
    },
    {
      name: '浙江',
      value: 104,
    },
    {
      name: '江西',
      value: 36,
    },
    {
      name: '湖北',
      value: 52,
    },
    {
      name: '广西',
      value: 133,
    },
    {
      name: '甘肃',
      value: 7,
    },
    {
      name: '山西',
      value: 19,
    },
    {
      name: '内蒙古',
      value: 7,
    },
    {
      name: '陕西',
      value: 22,
    },
    {
      name: '吉林',
      value: 4,
    },
    {
      name: '福建',
      value: 18,
    },
    {
      name: '贵州',
      value: 5,
    },
    {
      name: '广东',
      value: 98,
    },
    {
      name: '青海',
      value: 1,
    },
    {
      name: '西藏',
      value: 0,
    },
    {
      name: '四川',
      value: 44,
    },
    {
      name: '宁夏',
      value: 4,
    },
    {
      name: '海南',
      value: 22,
    },
    {
      name: '台湾',
      value: 3,
    },
    {
      name: '香港',
      value: 5,
    },
    {
      name: '澳门',
      value: 5,
    },
  ]
  let option = {
    /* tooltip: {
      triggerOn: 'click',
      formatter: function (e) {
        return 0.5 == e.value
          ? e.name + '：'
          : e.seriesName + '<br />' + e.name + '：' + e.value
      },
    }, */
    visualMap: {
      min: 0,
      max: 1000,
      left: 6,
      bottom: 0,
      showLabel: !0,
      // text: ['高', '低'],
      pieces: [
        {
          gt: 100,
          label: '> 100 亿',
          color: '#7f1100',
        },
        {
          gte: 10,
          lte: 100,
          label: '10 - 100 亿',
          color: '#ff5428',
        },
        {
          gte: 1,
          lt: 10,
          label: '1 - 9 亿',
          color: '#ff8c71',
        },
        {
          gte: 0,
          lt: 1,
          label: '< 1 亿',
          color: '#ffd768',
        }
      ],
      itemWidth: 10,
      itemHeight: 10,
      itemGap: 4,
      textStyle: {
        color: '#ddd'
      }
      // show: false,
    },
    geo: {
      map: 'china',
      roam: !1,
      scaleLimit: {
        min: 1,
        max: 2,
      },
      zoom: 1.23,
      top: 20,
      label: {
        normal: {
          show: !0,
          fontSize: '9',
          color: 'rgba(0,0,0,0.7)',
        },
      },
      itemStyle: {
        normal: {
          //shadowBlur: 50,
          //shadowColor: 'rgba(0, 0, 0, 0.2)',
          borderColor: 'rgba(0, 0, 0, 0.2)',
        },
        emphasis: {
          areaColor: '#f2d5ad',
          shadowOffsetX: 0,
          shadowOffsetY: 0,
          borderWidth: 0,
        },
      },
    },
    series: [
      {
        name: '合同额',
        type: 'map',
        geoIndex: 0,
        data: window.dataList,
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
