<!--
 * @Description: 关系图谱
 * @Author: chenfengtao
 * @Date: 2020-09-15 16:49:09
 * @LastEditors: supercheney
 * @LastEditTime: 2020-12-09 23:29:37
-->
<template>
  <div id="relation-chart">
    <svg
      id="network"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      :width="width"
      :height="height"
    >
      <!-- 边 -->
      <g class="links zoom-g"></g><!-- :stroke-opacity="networkOptions.link ? (currentNodeId === link.source.id || currentNodeId === null ? 1 : 0.1 ) : 0" -->
      <g class="linklabels zoom-g"></g>
      <!-- 点 -->
      <g class="nodes zoom-g"></g>
      <defs>
        <marker id="end" viewBox="0 -5 10 10" refX="30" refY="0" markerWidth="10" markerHeight="10" orient="auto" fill="#aaa" fill-opacity="1">
          <path d="M0,-5L10,0L0,5"></path>
        </marker>
        <marker id="root" viewBox="0 -5 10 10" refX="35" refY="0" markerWidth="10" markerHeight="10" orient="auto" fill="#aaa" fill-opacity="1">
          <path d="M0,-5L10,0L0,5"></path>
        </marker>
        <!-- circle 背景图片 -->
        <!-- <pattern id="node-bg" x="22" y="22" patternUnits="userSpaceOnUse" height="44" width="44">
          <image x="2" y="2" width="40" height="40" xlink:href="~@/assets/images/relation/renwu.png"></image>
        </pattern>
        <pattern id="node-bg-root" x="30" y="30" patternUnits="userSpaceOnUse" height="60" width="60">
          <image x="3" y="3" width="54" height="54" xlink:href="~@/assets/images/relation/renwu.png"></image>
        </pattern> -->
      </defs>
    </svg>
  </div>
</template>

<script>
import * as d3 from 'd3'
import { cloneDeep } from 'lodash'

/* import people from '@/assets/images/relation/dark/people.png'
import book from '@/assets/images/relation/dark/book.png'
import dynasty from '@/assets/images/relation/dark/dynasty.png'
import event from '@/assets/images/relation/dark/event.png'
import guanzhi from '@/assets/images/relation/dark/guanzhi.png'
import place from '@/assets/images/relation/dark/place.png'
import org from '@/assets/images/relation/dark/org.png'
import diwei from '@/assets/images/relation/dark/diwei.png'

import peopleHl from '@/assets/images/relation/dark/people-hl.png'
import bookHl from '@/assets/images/relation/dark/book-hl.png'
import dynastyHl from '@/assets/images/relation/dark/dynasty-hl.png'
import eventHl from '@/assets/images/relation/dark/event-hl.png'
import guanzhiHl from '@/assets/images/relation/dark/guanzhi-hl.png'
import placeHl from '@/assets/images/relation/dark/place-hl.png'
import orgHl from '@/assets/images/relation/dark/org-hl.png'
import diweiHl from '@/assets/images/relation/dark/diwei-hl.png'

import peopleCenter from '@/assets/images/relation/light/light-people-center.png'
import bookCenter from '@/assets/images/relation/light/light-book-center.png'
import dynastyCenter from '@/assets/images/relation/light/light-dynasty-center.png'
import eventCenter from '@/assets/images/relation/light/light-event-center.png'
import guanzhiCenter from '@/assets/images/relation/light/light-guanzhi-center.png'
import placeCenter from '@/assets/images/relation/light/light-place-center.png'
import orgCenter from '@/assets/images/relation/light/light-org-center.png'
import diweiCenter from '@/assets/images/relation/light/light-diwei-center.png' */

import company from '@/assets/img/Human/company.png'
import department from '@/assets/img/Human/department.png'
import project from '@/assets/img/Human/project.png'
import user from '@/assets/img/Human/user.png'

// let height = 680

/* let color = {
  text: '#8fac4a',
  chart3: '#41433d'
} */
const icons = {
  company,
  department,
  project,
  user,
}

/* const icons = {
  '数字人文人物': people,
  '数字人文古籍': book,
  '数字人文朝代': dynasty,
  '数字人文事件': event,
  '数字人文官职': guanzhi,
  '数字人文地区': place,
  '数字人文机构': org,
  '数字人文社会地位': diwei
}

const iconsHl = {
  '数字人文人物': peopleHl,
  '数字人文古籍': bookHl,
  '数字人文朝代': dynastyHl,
  '数字人文事件': eventHl,
  '数字人文官职': guanzhiHl,
  '数字人文地区': placeHl,
  '数字人文机构': orgHl,
  '数字人文社会地位': diweiHl
}

const iconsCenter = {
  '数字人文人物': peopleCenter,
  '数字人文古籍': bookCenter,
  '数字人文朝代': dynastyCenter,
  '数字人文事件': eventCenter,
  '数字人文官职': guanzhiCenter,
  '数字人文地区': placeCenter,
  '数字人文机构': orgCenter,
  '数字人文社会地位': diweiCenter
} */

let simulation = null
// var svg = null
// let links = null
// let nodes = null
let linkUpdate = null
let nodeUpdate = null
let linkTextUpdate = null

let timer = null

export default {
  props: {
    data: {
      type: Object,
      required: true
    },
    options: {
      type: Object
    },
    distance: {
      type: [Number],
      default: 30
    }
  },
  data () {
    return {
      width: 0,
      height: 0,
      links: [],
      nodes: [],
      defaultOptions: {
        label: 'label',
        showLabel: true,
        linkText: 'linkText',
        showLinkText: true,
        node: true, // 默认显示节点
        link: true, // 默认显示边
        nodeColor: '#9999FF', // 节点及label颜色
        linkColor: '#CCCCCC', // 连线颜色及连线上的文字颜色
        nodeFontSize: 10, // 节点文字大小
        linkFontSize: 10, // 边文字大小
        linkField: 'id',
        mouseover: false // 是否启用node 鼠标mouseover事件
      },

      currentNodeId: null,
      linkedNode: [],
      selectedNodeId: null,

      clickedOnce: false
    }
  },
  computed: {
    networkOptions () {
      return Object.assign(this.defaultOptions, this.options)
    },
    nodeCount () {
      return this.groupData.nodes.map(item => item[this.networkOptions.nodeField])
    },
    linkWeights () {
      return this.groupData.links.map(item => +item[this.networkOptions.linkField])
    },
    groupData () {
      let links = this.setRelationGroup(cloneDeep(this.data.links))
      return {
        nodes: cloneDeep(this.data.nodes),
        links: cloneDeep(links)
      }
    }
  },
  watch: {
    width () {
      this.render(this.groupData)
    },
    data: {
      handler: function () {
        this.render(this.groupData)
      },
      deep: true
    }
  },
  mounted () {
    window.addEventListener('resize', this.onResize)
    this.onResize()
    // this.render(this.data)

    // 创建箭头
    // this.createArrow(d3.select('#network'))
  },
  methods: {
    forceSimulation: d3.forceSimulation,
    onResize () {
      this.width = this.$el.offsetWidth
      this.height = this.$el.offsetHeight
    },
    render (graph) {
      let _this = this

      // let links = this.setRelationGroup(graph.links)

      simulation = this.forceSimulation(this.groupData.nodes)
        .force(
          'link',
          d3.forceLink(this.groupData.links).id(function (d) {
            return d.id
          })
            .distance(function () {
              return 60
              // debugger
              // return _this.weightScale(d[_this.networkOptions.linkField])
            })
        )
        // 引力
        .force('charge', d3.forceManyBody().strength(-150).distanceMin(15)/* .strength(d => d.weight * -30).distanceMin(60) */)
        // 实例中心
        .force('center', d3.forceCenter(this.width / 2, this.height / 2))
        // 碰撞力 防止节点重叠
        .force('collide', d3.forceCollide().radius(30).iterations(2))
        // .force('X', d3.forceX(this.width / 2))
        // .force('Y', d3.forceY(this.height / 2))
        // .stop()

      let { linkEnter, nodeEnter, linkTextEnter } = this.draw(graph)

      // tick事件
      simulation/* .alphaTarget(0.3).alphaDecay(0.05).alpha(1) */.on('tick', function () {
        _this.ticked(linkEnter, nodeEnter, linkTextEnter)
      })

      /* simulation.stop()
        .alpha(0.5) */

      // simulation.force('link').links(graph.links)
      // simulation.nodes(graph.nodes)
      // this.links = simulation.force('link').links()
      this.nodes = simulation.nodes()

      // simulation.restart()

      return simulation
    },
    draw,
    drawLink,
    drawLinkText,
    drawNode,

    ticked,
    dragstarted,
    dragged,
    dragended,
    zoomed,

    linkPath,
    getLineTextDx, // path文字位置

    setRelationGroup,

    nodeTextScale,
    colorScale,
    weightScale,

    // node事件
    nodeHover,

    // 导出
    exportPng,

    // 创建箭头
    createArrow
  },
  beforeDestroy () {
    window.removeEventListener('resize', this.onResize)
  }
}

function draw (graph) {
  let links = graph.links
  // 缩放
  const zoom = d3
    .zoom()
    .duration(100)
    .on('zoom', zoomed)
  d3.select('#network').call(zoom).on('dblclick.zoom', null)

  simulation.nodes(graph.nodes)
  simulation.force('link').links(links)

  // 画节点
  let nodeEnter = this.drawNode(graph.nodes)
  // 画边
  let linkEnter = this.drawLink(links)
  // 边上的文字
  let linkTextEnter = this.drawLinkText(links)
  // tick事件
  // simulation/* .alphaTarget(0.3).alphaDecay(0.05).alpha(1) */.on('tick', function () {
  //   _this.ticked(linkEnter, nodeEnter, linkTextEnter)
  // })
  return { linkEnter, nodeEnter, linkTextEnter }
}

function drawLink (links) {
  const _this = this
  linkUpdate = d3.select('.links').selectAll('.link').data(links, d => d.id)

  // remove exit
  linkUpdate.exit().remove()
  let linkEnter = linkUpdate.enter()
    .append('path')
    // .attr('marker-end', d => d.target.isRoot === 1 ? 'url(#root)' : 'url(#end)')

  linkUpdate = linkEnter.merge(linkUpdate)
    .attr('id', function (d) {
      return `link${d.id}`
    })
    .attr('class', function (d) {
      if (_this.currentNodeId !== d.source.id && _this.currentNodeId !== d.target.id && _this.currentNodeId !== null) {
        return 'link hide'
      } else {
        return 'link'
      }
    })
    .attr('stroke', () => {
      return _this.networkOptions.linkColor // _this.colorScale(d[_this.networkOptions.linkField])
    })
    .attr('stroke-opacity', (d) => {
      return _this.currentNodeId === d.source.id || _this.currentNodeId === d.target.id || _this.currentNodeId === null ? 1 : 0.1
    })

  return linkUpdate
}

function drawLinkText (links) {
  const _this = this

  linkTextUpdate = d3.select('.linklabels')
    .selectAll('text.link-text')
    .data(links, d => d.id)

  linkTextUpdate.exit().remove()
  let linkTextEnter = linkTextUpdate.enter()
    .append('text')
    .attr('class', 'link-text')
    .attr('font-size', () => _this.networkOptions.linkFontSize)
    .attr('fill', () => '#ddd' /* _this.networkOptions.linkColor */)
  // .attr('fill-opacity', (d) => _this.networkOptions.link ? (_this.currentNodeId === d.source.id || _this.currentNodeId === null ? 1 : 0.1) : 0)
    .attr('dy', -2)
    .attr('dx', d => getLineTextDx(d))

  linkTextEnter.append('textPath')
    .text(d => d[_this.networkOptions.linkText])
    .attr('href', d => `#link${d.id}`)

  linkTextUpdate = linkTextEnter.merge(linkTextUpdate)
    .attr('class', d => _this.networkOptions.link
      ? (_this.currentNodeId === d.source.id || _this.currentNodeId === d.target.id || _this.currentNodeId === null
        ? 'link-text'
        : 'link-text opacity')
      : 0)

  return linkTextUpdate
}

function drawNode (nodes) {
  const _this = this

  nodeUpdate = d3.select('.nodes')
    .selectAll('.node-container')
    .data(nodes, d => d.id)

  nodeUpdate.exit().remove()
  let nodeEnter = nodeUpdate.enter()
    .append('g')
    .attr('class', 'node-container')
  // nodeEnter.append('circle')
  //   .attr('class', 'node')
  //   // .attr('stroke', '#fff')
  //   .attr('r', d => +d.isRoot === 1 ? 30 : 22)
  //   .merge(nodeUpdate.select('circle'))
  //   // .attr('fill', d => _this.networkOptions.nodeColor)
  //   // .attr('fill', d => +d.isRoot === 1 ? 'url(#node-bg-root)' : 'url(#node-bg)')
  //   // .attr('fill-opacity', d => (_this.currentNodeId === d.id || _this.currentNodeId === null || _this.linkedNode.indexOf(d.id) > -1) ? 1 : 0.2)
  //   .append('title', d => d[_this.networkOptions.label])

  nodeEnter.append('text')
    .attr('class', 'text')
    .merge(nodeUpdate.select('text'))
    .attr('dx', d => -d[_this.networkOptions.label].length * _this.networkOptions.nodeFontSize / 2)
    /* .attr('dx', d =>
      _this.networkOptions.node
        ? (+d.isRoot === 1 ? 28 : 20)
        : -(d[_this.networkOptions.label].length * nodeTextScale(d[_this.networkOptions.nodeField]) / 2)) */
    .attr('dy', d => +d.isRoot === 1 ? 48 : 20 + _this.networkOptions.nodeFontSize * 1.5)
    // .attr('dy', '0.35em')
    // .attr('font-size', d => _this.nodeTextScale(d[_this.networkOptions.nodeField]))
    .attr('font-size', () => _this.networkOptions.nodeFontSize)
    .attr('fill', '#fff')
    .text(d => d[_this.networkOptions.label])
    .call(getBB)

  // node 文字背景
  // nodeEnter.insert('rect', 'text')
  //   .attr('x', function (d) { return d.bbox.x })
  //   .attr('y', function (d) { return d.bbox.y })
  //   .attr('width', function (d) { return d.bbox.width })
  //   .attr('height', function (d) { return d.bbox.height })
  // .style('fill', 'red')
  // nodeEnter.append('circle')
  //   .attr('class', 'node')
  //   .attr('stroke', '#fff')
  //   .attr('r', d => +d.isRoot === 1 ? 15 : 10.5)
  //   .merge(nodeUpdate.select('circle'))
  //   .attr('fill', () => _this.networkOptions.nodeColor)
  // // .attr('fill-opacity', d => (_this.currentNodeId === d.id || _this.currentNodeId === null || _this.linkedNode.indexOf(d.id) > -1) ? 1 : 0.2)
  //   .append('title', d => d[_this.networkOptions.label])

  nodeEnter.append('image')
    .merge(nodeUpdate.select('image'))
    .attr('class', d => +d.isRoot === 1
      ? (_this.selectedNodeId === d.id ? 'node-icon-big-hl' : 'node-icon-big')
      : (_this.selectedNodeId === d.id ? 'node-icon-hl' : 'node-icon')
    )
    .attr('xlink:href', function (d) {
      if (+d.isRoot === 1) {
        return (icons[d.type])
      }
      return _this.selectedNodeId === d.id
        ? (icons[d.type])
        : (icons[d.type])
    })
    .attr('x', d => +d.isRoot === 1 ? -27 : -15)
    .attr('y', d => +d.isRoot === 1 ? -27 : -15)
    // .attr('width', d => +d.isRoot === 1 ? (_this.selectedNodeId === d.id ? 94 : 60) : (_this.selectedNodeId === d.id ? 69 : 44))
    // .attr('height', d => +d.isRoot === 1 ? (_this.selectedNodeId === d.id ? 94 : 60) : (_this.selectedNodeId === d.id ? 69 : 44))

  nodeUpdate = nodeEnter.merge(nodeUpdate)
    .attr('opacity', d => {
      return (_this.currentNodeId === d.id || _this.currentNodeId === null || _this.linkedNode.indexOf(d.id) !== -1)
        ? 1
        : 0.2
    })
    .call(
      d3
        .drag()
        .on('start', this.dragstarted)
        .on('drag', this.dragged)
        .on('end', this.dragended)
    )
    .on('mouseenter', function (e, d) {
      if (!_this.networkOptions.mouseover) return
      _this.nodeHover(d)
    })
    .on('mouseleave', function () {
      if (!_this.networkOptions.mouseover) return
      _this.currentNodeId = null
      _this.draw(_this.groupData)
      // simulation.stop()
      // d3.event.stopPropagation()
    })
    .on('click', function (d) {
      _this.selectedNodeId = d.id
      _this.draw(_this.groupData)
      /* _this.$nextTick(function () {
        _this.$emit('node-click', d)
      }) */
      if (_this.clickedOnce) {
        _this.clickedOnce = false
        window.clearTimeout(timer)
        timer = null
        _this.$emit('node-dbclick', d)
      } else {
        timer = window.setTimeout(function () {
          _this.$nextTick(function () {
            _this.$emit('node-click', d)
            _this.clickedOnce = false
          })
        }, 300)
        _this.clickedOnce = true
      }
      d3.event.stopPropagation()
    })
    /* .on('dblclick', function (d) {
      _this.$emit('node-dbclick', d)
    }) */

  return nodeUpdate
}

function getBB (selection) {
  selection.each(function (d) {
    d.bbox = this.getBBox()
  })
}

function ticked (link, node, linkText) {
  let _this = this
  link.attr('d', function (d) {
    return linkPath(d)
    // return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y
  })

  linkText.attr('dx', function (d) {
    return _this.getLineTextDx(d)
  })

  node.attr('transform', function (d) {
    return 'translate(' + d.x + ',' + d.y + ')'
  })
}

function dragstarted (e, d) {
  console.log(e, d)
  if (!e || !e.active) simulation.alphaTarget(0.3).restart()
  d.fx = d.x
  d.fy = d.y
}

function dragged (e, d) {
  d.fx = e.x
  d.fy = e.y
}

function dragended (e, d) {
  if (!e.active) simulation.alphaTarget(0)
  d.fx = null
  d.fy = null
}

function zoomed (event) {
  console.log(event)
  d3.selectAll('svg > g.zoom-g').attr('transform', event.transform)
}

function linkPath (d) {
  // return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y
  // 如果连接线连接的是同一个实体，则对path属性进行调整，绘制的圆弧属于长圆弧，同时对终点坐标进行微调，避免因坐标一致导致弧无法绘制
  if (d.target === d.source) {
    let dr = 30 / d.linknum
    return (
      'M' +
      d.source.x +
      ',' +
      d.source.y +
      'A' +
      dr +
      ',' +
      dr +
      ' 0 1,1 ' +
      d.target.x +
      ',' +
      (d.target.y + 1)
    )
  } else if (d.size % 2 !== 0 && d.linknum === 1) {
    // 如果两个节点之间的连接线数量为奇数条，则设置编号为1的连接线为直线，其他连接线会均分在两边
    return (
      'M ' +
      d.source.x +
      ' ' +
      d.source.y +
      ' L ' +
      d.target.x +
      ' ' +
      d.target.y
    )
  }

  // 根据连接线编号值来动态确定该条椭圆弧线的长半轴和短半轴，当两者一致时绘制的是圆弧
  // 注意A属性后面的参数，前两个为长半轴和短半轴，第三个默认为0，第四个表示弧度大于180度则为1，小于则为0，这在绘制连接到相同节点的连接线时用到；第五个参数，0表示正角，1表示负角，即用来控制弧形凹凸的方向。本文正是结合编号的正负情况来控制该条连接线的凹凸方向，从而达到连接线对称的效果
  var curve = 1.5
  var homogeneous = 1.2
  var dx = d.target.x - d.source.x
  var dy = d.target.y - d.source.y
  var dr =
    (Math.sqrt(dx * dx + dy * dy) * (d.linknum + homogeneous)) /
    (curve * homogeneous)
  // 当节点编号为负数时，对弧形进行反向凹凸，达到对称效果
  if (d.linknum < 0) {
    dr =
      (Math.sqrt(dx * dx + dy * dy) * (-1 * d.linknum + homogeneous)) /
      (curve * homogeneous)
    return (
      'M' +
      d.source.x +
      ',' +
      d.source.y +
      'A' +
      dr +
      ',' +
      dr +
      ' 0 0,0 ' +
      d.target.x +
      ',' +
      d.target.y
    )
  }
  return (
    'M' +
    d.source.x +
    ',' +
    d.source.y +
    'A' +
    dr +
    ',' +
    dr +
    ' 0 0,1 ' +
    d.target.x +
    ',' +
    d.target.y
  )
}

function getLineTextDx (d) {
  let lineTextFontSize = 10
  const sr = 5.5
  const sx = d.source.x
  const sy = d.source.y
  const tx = d.target.x
  const ty = d.target.y

  const distance = Math.sqrt(Math.pow(tx - sx, 2) + Math.pow(ty - sy, 2))

  // const textLength = d.label.length;
  const textLength = 3// d.labels.length
  const deviation = 8 // 调整误差
  const dx = (distance - sr - textLength * lineTextFontSize) / 2 + deviation

  return dx / 1.2
}

// 关系分组
function setRelationGroup (links) {
  // 对连接线进行统计和分组，不区分连接线的方向，只要属于同两个实体，即认为是同一组
  let linkMap = {}
  let linkGroup = {}

  links.map(item => {
    let key =
      item.source < item.target
        ? item.source + ':' + item.target
        : item.target + ':' + item.source

    // eslint-disable-next-line no-prototype-builtins
    if (!linkMap.hasOwnProperty(key)) {
      linkMap[key] = 0
    }

    linkMap[key] += 1

    // eslint-disable-next-line no-prototype-builtins
    if (!linkGroup.hasOwnProperty(key)) {
      linkGroup[key] = []
    }
    linkGroup[key].push(item)

    // 设置link type
    item.linkType = item.source === item.target ? 'self' : 'notself'

    return item
  })

  // console.log('linkMap', linkMap)
  // console.log('linkGroup', linkGroup)
  // console.log('links', links)

  links = setLinkNumber(links, linkMap, linkGroup)

  return links
}

// 设置link编号
const setLinkNumber = (links, linkMap, linkGroup) => {
  links.map(item => {
    let key =
      item.source < item.target
        ? item.source + ':' + item.target
        : item.target + ':' + item.source

    item.size = linkMap[key]

    // 同一组进行编号
    let group = linkGroup[key]

    dispatchNumber(group, item.linkType)

    return item
  })

  return links
}

const dispatchNumber = (group, type) => {
  if (group.length === 0) return
  // 对该分组内的关系按照方向进行分类，此处根据连接的实体ASCII值大小分成两部分
  let linksA = []
  let linksB = []
  group.forEach(item => {
    if (item.source < item.target) {
      linksA.push(item)
    } else {
      linksB.push(item)
    }
  })

  // 确定关系最大编号。为了使得连接两个实体的关系曲线呈现对称，根据关系数量奇偶性进行平分。
  // 特殊情况：当关系都是连接到同一个实体时，不平分
  var maxLinkNumber = 0
  if (type === 'self') {
    maxLinkNumber = group.length
  } else {
    maxLinkNumber =
      group.length % 2 === 0 ? group.length / 2 : (group.length + 1) / 2
  }
  // 如果两个方向的关系数量一样多，直接分别设置编号即可
  if (linksA.length === linksB.length) {
    var startLinkNumber = 1
    for (var i = 0; i < linksA.length; i++) {
      linksA[i].linknum = startLinkNumber++
    }
    startLinkNumber = 1
    for (let i = 0; i < linksB.length; i++) {
      linksB[i].linknum = startLinkNumber++
    }
  } else {
    // 当两个方向的关系数量不对等时，先对数量少的那组关系从最大编号值进行逆序编号，然后在对另一组数量多的关系从编号1一直编号到最大编号，再对剩余关系进行负编号
    // 如果抛开负号，可以发现，最终所有关系的编号序列一定是对称的（对称是为了保证后续绘图时曲线的弯曲程度也是对称的）
    var biggerLinks, smallerLinks
    if (linksA.length > linksB.length) {
      biggerLinks = linksA
      smallerLinks = linksB
    } else {
      biggerLinks = linksB
      smallerLinks = linksA
    }

    let startLinkNumber = maxLinkNumber
    for (let i = 0; i < smallerLinks.length; i++) {
      smallerLinks[i].linknum = startLinkNumber--
    }
    var tmpNumber = startLinkNumber

    startLinkNumber = 1
    var p = 0
    while (startLinkNumber <= maxLinkNumber) {
      biggerLinks[p++].linknum = startLinkNumber++
    }
    // 开始负编号
    startLinkNumber = 0 - tmpNumber
    for (let i = p; i < biggerLinks.length; i++) {
      biggerLinks[i].linknum = startLinkNumber++
    }
  }
}

function nodeTextScale (count) {
  let x = d3.scaleLinear()
    .domain([d3.min(this.nodeCount), d3.max(this.nodeCount)])
    .range([8, 40])
  return x(count)
}

function colorScale (data) {
  // let color = d3.scaleQuantize()
  let color = d3.scaleLinear()
    .domain([d3.min(this.linkWeights), d3.max(this.linkWeights)])
    .range(['#CCCCCC', '#90997B', '#E89EEE', '#6EA9F7'])

  return color(data)
}

function weightScale (weight) {
  let x = d3.scaleLinear()
    .domain([d3.min(this.linkWeights), d3.max(this.linkWeights)])
    .range([80, 160])
  return x(weight)
}

function nodeHover (node) {
  this.currentNodeId = node.id
  this.linkedNode = []
  // 获取与当前节点连接的节点id
  this.groupData.links.forEach(item => {
    if (item.source.id === this.currentNodeId) {
      this.linkedNode.push(item.target.id)
    }
    if (item.target.id === this.currentNodeId) {
      this.linkedNode.push(item.source.id)
    }
  })

  this.draw(this.groupData)
  // simulation.stop()
  // d3.event.stopPropagation()
}

function exportPng () {
  var serializer = new XMLSerializer()
  var source = '<?xml version="1.0" standalone="no"?>\r\n' + serializer.serializeToString(d3.select('svg#network').node())

  var image = new Image()
  image.src = 'data:image/svg+xml;charset=utf-8,' + encodeURIComponent(source)
  var canvas = document.createElement('canvas')
  canvas.width = this.width
  canvas.height = this.height
  var context = canvas.getContext('2d')
  context.fillStyle = '#fff'
  context.fillRect(0, 0, 10000, 10000)
  context.drawImage(image, 0, 0)
  image.onload = function () {
    context.drawImage(image, 0, 0)
    var a = document.createElement('a')
    a.download = 'export_png.png'
    a.href = canvas.toDataURL('image/png')
    a.click()
  }
}

// 创建箭头
function createArrow (svg, type, size = 10) {
  svg
    .append('svg:defs')
    .selectAll('marker')
    .data([type || 'end']) // Different link/path types can be defined here
    .enter()
    .append('svg:marker') // This section adds in the arrows
    .attr('id', String)
    .attr('viewBox', '0 -5 10 10')
    .attr('refX', 18)
    .attr('refY', 0)
    .attr('markerWidth', size)
    .attr('markerHeight', size)
    .attr('orient', 'auto')
    .attr('fill', '#aaa')
    .attr('fill-opacity', 1)
    .append('svg:path')
    .attr('d', 'M0,-5L10,0L0,5')
}
</script>

<style lang="less">
#relation-chart {
  height: 100%;
}

.node-container {
  cursor: pointer;
}

.link {
  fill: none;
}
.link.show {
  opacity: 1;
}
.link.hide {
  opacity: 0.1;
}
.text {
  cursor: default;
  fill: #ddd;
}
.opacity textPath {
  fill-opacity: 0.1;
}
image.node-icon {
  width: 30px;
  height: 30px;
}
image.node-icon-hl {
  width: 70px;
  height: 70px;
}
image.node-icon-big {
  width: 54px;
  height: 54px;
}
image.node-icon-big-hl {
  width: 94px;
  height: 94px;
}

</style>
