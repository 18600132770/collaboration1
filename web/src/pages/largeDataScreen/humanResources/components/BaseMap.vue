/* eslint-disable no-undef */
<!--
 * @Description: 高德地图
 * @Author: chenfengtao
 * @Date: 2020-08-17 14:44:34
 * @LastEditors: supercheney
 * @LastEditTime: 2020-11-22 23:40:39
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
    initInfoWindow
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
        // mapStyle: 'amap://styles/blue', //设置地图的显示样式 https://lbs.amap.com/api/javascript-api/example/personalized-map/set-theme-style/?sug_index=0
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
