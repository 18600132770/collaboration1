/* eslint-disable no-undef */
<!--
 * @Description: 高德地图
 * @Author: chenfengtao
 * @Date: 2020-08-17 14:44:34
 * @LastEditors: supercheney
 * @LastEditTime: 2020-12-05 16:27:26
-->
<template>
  <div id="map-container"></div>
</template>

<script>
// https://lbs.amap.com/
import AMapLoader from '@amap/amap-jsapi-loader'

import markerIcon from '@/assets/img/bigscreen/marker.png'

export default {
  name: 'BaseMap',
  props: {
    markerList: {
      type: Array,
      required: false,
      default: () => []
    }
  },
  data () {
    return {
      map: null,
      infoWindow: null,
      loading: false
    }
  },

  mounted () {
    this.initMap()
  },
  methods: {
    // 初始化地图
    initMap,
    // 地图平移到指定位置
    panTo,
    // 添加marker
    addMarker,
    onMarkerClick,
    // 初始化信息框
    initInfoWindow,

    initCircle,

    addComMarker,
    initCircleMarker
  },
  beforeDestroy () {
    // 销毁地图
    this.map && this.map.destroy()
  }
}

function initMap () {
  const _this = this
  AMapLoader.load({
    key: '7c19ea3418bf3c54d21051995bec8ced', // 申请好的Web端开发者Key，首次调用 load 时必填
    version: '1.4.15', // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: [], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
    AMapUI: {
      // 是否加载 AMapUI，缺省不加载
      version: '1.1', // AMapUI 缺省 1.1
      plugins: ['control/BasicControl'] // 需要加载的 AMapUI ui插件
    },
    Loca: {
      // 是否加载 Loca， 缺省不加载
      version: '1.3.2' // Loca 版本，缺省 1.3.2
    }
  })
    .then((AMap) => {
      window.AMap = AMap
      _this.map = new AMap.Map('map-container', {
        zoom: 5,
        center: [108.93, 34.27],
        mapStyle: 'amap://styles/darkblue', //设置地图的显示样式 https://lbs.amap.com/api/javascript-api/example/personalized-map/set-theme-style/?sug_index=0
      })

      // 缩放组件
      // eslint-disable-next-line no-undef
      const zoomCtrl = new AMapUI.BasicControl.Zoom({
        position: 'br',
        showZoomNum: true
      })

      _this.map.addControl(zoomCtrl)

      // 初始化信息框
      _this.infoWindow = _this.initInfoWindow()

      _this.map.on('click', function () {
        // 清空所有的信息框
        _this.map.clearInfoWindow()
      })
      // 地图资源加载完成事件
      _this.map.on('complete', function () {
        _this.$emit('map-complete', true)
      })

      _this.initCircle(4000)
      _this.initCircle(3000)
      _this.initCircle(2000)

      _this.addComMarker([108.93, 34.27])
      _this.initCircleMarker()
    })
    .catch((e) => {
      console.log(e)
    })
}

function panTo (lnglat, duration) {
  this.map.panTo(lnglat, duration)
}

/**
 * @param： {Array} lnglat [116.481181, 39.989792]
 * @param：{String} content
 */
function addMarker (data) {
  const { lnglat, name } = data

  const _this = this
  var marker = new window.AMap.Marker({
    position: lnglat,
    icon: markerIcon, // type === '事件' ? event : place,
    // offset: new window.AMap.Pixel(-20, -35),
    label: {
      content: `<div class="marker-label">${name}</div>`,
      offset: new window.AMap.Pixel(16, 1)
    }
  })
  marker.data = data
  marker.content = name
  marker.on('click', _this.onMarkerClick) // 绑定click事件
  this.map.add(marker)

  return marker
}

function addComMarker (lnglat) {
  var marker = new window.AMap.Marker({
    position: lnglat,
    // icon: markerIcon, // type === '事件' ? event : place,
    // offset: new window.AMap.Pixel(-20, -35),
    /* label: {
      content: `<div class="marker-label">${name}</div>`,
      offset: new window.AMap.Pixel(16, 1)
    } */
  })
  this.map.add(marker)
}

function onMarkerClick (e) {
  this.infoWindow.open(this.map, e.target.getPosition()) // 打开信息窗体
  this.infoWindow.setContent(e.target.content)
  // e.target就是被点击的Marker
  // 当前标记的数据
  const data = e.target.data
  this.loading = true
  /* const res = this.getDetail({
    name: data.name,
    type: data.type,
    entryId: data.entryId,
    nodeId: data.nodeId,
    sonDynasty: data.sonDynasty
  }) */

  // 选中朝代
  this.$emit('select-marker', data.name)

  /* res.then(data => {
    _this.loading = false
    _this.infoWindow.setContent(_this.formatDetail(data))
  }) */
}

function initInfoWindow () {
  // eslint-disable-next-line no-undef
  var infoWindow = new AMap.InfoWindow({
    // 创建信息窗体
    anchor: 'bottom-center',
    // isCustom: true, // 使用自定义窗体
    // content: 'content', // 信息窗体的内容可以是任意html片段
    // eslint-disable-next-line no-undef
    offset: new AMap.Pixel(0, -45)
  })
  return infoWindow
}

function initCircle (radius) {
  var circle = new window.AMap.Circle({
    center: [108.93, 34.27],
    radius: radius, //半径
    borderWeight: 3,
    strokeColor: "#FF33FF", 
    strokeWeight: 6,
    strokeOpacity: 0.2,
    fillOpacity: 0.2,
    strokeStyle: 'dashed',
    strokeDasharray: [10, 10], 
    // 线样式还支持 'dashed'
    fillColor: '#1791fc',
    zIndex: 50,
  })

  circle.setMap(this.map)

  // 缩放地图到合适的视野级别
  radius === 4000 && this.map.setFitView([ circle ])
}

function initCircleMarker () {
  let data = [
    [108.936872,34.271727],
    [108.937559,34.283926],
    [108.979101,34.274564],
    [108.929319,34.254135],
    [108.953008,34.257824],
    [108.961591,34.294705],
    [109.01103,34.255554],
    [108.908033,34.291301],
    [108.983907,34.265769],
    [108.898763,34.300377],
    [108.971548,34.25981],
    [108.954725,34.230011],
    [108.99558,34.257256],
    [108.951292,34.300093],
    [108.952665,34.212694],
    [108.955755,34.243634],
    [108.906316,34.252432],
    [108.920049,34.239093],
    [108.902196,34.257256],
    [108.89842,34.224901],
    [108.882284,34.24108],
  ]
  data.forEach(item => {
    var circleMarker = new window.AMap.CircleMarker({
      center:item,
      radius:10,//3D视图下，CircleMarker半径不要超过64px
      strokeColor:'white',
      strokeWeight:2,
      strokeOpacity:0.5,
      fillColor:'rgba(0,0,255,1)',
      fillOpacity:0.5,
      zIndex:10,
      bubble:true,
      cursor:'pointer',
      clickable: true
    })
    circleMarker.setMap(this.map)
  })
}
</script>

<style lang="less" scoped>
#map-container {
  width: 100%;
  height: 100%;
}
</style>
<style lang="less">
.event-intro {
  width: 400px;
  height: 278px;
  background: #fff;
  bottom: 20px;
  border-radius: 4px;
  padding: 0px 20px 4px;
  box-sizing: border-box;
  overflow: auto;
  img {
    width: 100%;
  }
  .e-b {
    margin-bottom: 16px;
  }
  .title {
    font-weight: bold;
    color: #333;
    font-size: 18px;
    margin-bottom: 10px;
  }
  .title.link {
    cursor: pointer;
  }
  .intro {
    color: #555;
  }
}
.amap-marker-label {
  position: absolute;
  z-index: 0;
  border: 1px solid transparent;
  background-color: white;
  white-space: nowrap;
  cursor: default;
  margin-left: 8px;
  padding: 0 12px 0 12px;
  font-size: 12px;
  height: 20px;
  line-height: 20px;
  border-radius: 10px;
  vertical-align: middle;
  // font-weight: bold;
  border: 1px solid #DBB019;
}
// .amap-marker-label:hover {
//   background: #a62b1d;
//   color: #fff;
// }
.amap-info-content {
  padding: 20px 10px 10px 10px;
}
.amap-info-close {
  right: 8px !important;
  font-size: 20px;
}
.amap-logo, .amap-copyright {
  display: none !important;
}
.amap-ui-control-position-br, .amap-ui-control-position-rb {
  bottom: 10px !important;
  right: 10px !important;
}

.link {
  margin-right: 8px;
}
.link-person {
  color: #0077c6;
  border-bottom: 1px solid #0077c6;
  cursor: pointer;
}
.link-event {
  color: #008e25;
  border-bottom: 1px solid #008e25;
  cursor: pointer;
}
.link-place {
  color: #dd7800;
  border-bottom: 1px solid #dd7800;
  cursor: pointer;
}
.amap-icon img {
  width: 24px;
  height: 33px;
}
.amap-info-contentContainer ::-webkit-scrollbar {
  width: 2px;
}
</style>
