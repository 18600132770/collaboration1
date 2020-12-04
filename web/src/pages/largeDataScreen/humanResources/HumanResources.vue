<template>
  <div class="container">
    <header class="header">
      <div class="head-title">
        <!-- <div class="logo"></div> -->
        <span>公司人力资源分析系统</span>
        <span class="date"
          ><a-icon type="clock-circle" /> {{ currentDate }}</span
        >
      </div>
    </header>
    <section class="main">
      <div class="left">
        <div class="chart-container" style="height: 150px; flex: unset">
          <!-- <header>项目收款总览</header> -->
          <section>
            <div class="main-count">
              <a-statistic title="员工数" :value="2893" suffix="人" />
            </div>
            <div class="sub-count">
              <div>院内在岗：<span>2783</span>人</div>
              <div>出差：<span>100</span>人</div>
              <div>请假：<span>10</span>人</div>
            </div>
          </section>
        </div>
        <div class="chart-container">
          <div class="small-col">
            <header>人员结构分析</header>
            <section>
              <FunnelChart></FunnelChart>
            </section>
          </div>
        </div>
        <div class="chart-container left-bottom">
          <div class="small-col">
            <header>劳动价值分析</header>
            <section>
              <LineChart></LineChart>
            </section>
          </div>
        </div>
      </div>
      <div class="right">
        <div class="chart-container right-top">
          <div class="small-col">
            <header>人员结构比例</header>
            <section>
              <PieChartSix></PieChartSix>
              <!-- <SexChart style="width: calc(100% / 6); display: inline-block;"></SexChart> -->
              <!-- <DegreeChart style="width: calc(100% / 6); display: inline-block;"></DegreeChart> -->
            </section>
          </div>
        </div>
        <div class="chart-container right-bottom">
          <div style="height: 100%">
            <header>员工居住地点分析</header>
            <section style="height: 100%">
              <BaseMap></BaseMap>
            </section>
          </div>
          <div style="height: 100%">
            <header>人力资源图谱</header>
            <section>
              <Graph
                v-if="graphData.nodes.length > 0"
                :data="graphData"
                :options="options"
                @node-click="onNodeClick"
                @node-dbclick="onNodeDBClick"
              ></Graph>
            </section>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { format } from 'date-fns'

import PieChartSix from './components/PieChartSix'
import BaseMap from './components/BaseMap'
import LineChart from './components/LineChart'
import FunnelChart from './components/FunnelChart'
import Graph from './components/Graph'
// import SexChart from './components/SexChart'
// import DegreeChart from './components/DegreeChart'

export default {
  name: 'BusinessAnalysis',
  components: {
    PieChartSix,
    BaseMap,
    LineChart,
    FunnelChart,
    Graph,
    // SexChart,
    // DegreeChart
  },
  data() {
    return {
      currentDate: format(new Date(), 'yyyy-MM-dd'),

      // 图谱数据
      graphData: {
        nodes: [],
        links: [],
      },
      options: {
        label: 'name',
        linkText: 'name',
        node: true,
        link: true,
        showLabel: true,
        nodeColor: '#9999FF',
        linkColor: '#727071',
        nodeFontSize: 13,
        linkField: 'id',
        nodeField: 'value',
        mouseover: true,
      },
    }
  },
  mounted () {
    this.mockData()
  },
  methods: {
    onNodeClick() {},
    onNodeDBClick() {},
    mockData () {
      let com = [{
        isRoot: 1,
        name: '公司',
        id: '102123787',
        type: 'company'
      }]
      let department = ['桥梁所', '路基所', '隧道所', '地质所']
      let project = ['京广高速', '渤海大桥', '央视二号楼', '雄安城轨']
      let name1 = ['杨朝来','蒋平','唐灿','华马达','赵小雪','薛文泉','丁建']
      let name2 = ['彭万里','高大山','谢大海','马宏宇','林莽','黄强辉']
      let name3 = ['甘铁生','张伍绍祖','马继祖','程孝先','宗敬先','年广嗣','汤绍箕']
      let name4 = ['王德茂','李书诚','杨勇','高尚德','刁富贵']

      let departmentData = department.map((item, index) => {
        return {
          id: 'd-' + index,
          name: item,
          type: 'department'
        }
      })
      let projectData = project.map((item, index) => {
        return {
          id: 'p-' + index,
          name: item,
          type: 'project'
        }
      })
      let name1Data = this.formatUser(name1, 'u1')
      let name2Data = this.formatUser(name2, 'u2')
      let name3Data = this.formatUser(name3, 'u3')
      let name4Data = this.formatUser(name4, 'u4')

      let nodes = [...com, ...departmentData, ...projectData, ...name1Data, ...name2Data, ...name3Data, ...name4Data]
      console.log(nodes)
      let links = []
      departmentData.forEach((item) => {
        links.push({
          source: com[0].id,
          target: item.id,
        })
      })
      departmentData.forEach((item, index) => {
        links.push({
          source: item.id,
          target: projectData[index].id,
        })
      })
      
      let ul1 = this.userLink(name1Data, projectData[0])
      let ul2 = this.userLink(name2Data, projectData[1])
      let ul3 = this.userLink(name3Data, projectData[2])
      let ul4 = this.userLink(name4Data, projectData[3])
      let ul5= this.userLink(name3Data, projectData[3])
      links = [...links, ...ul1, ...ul2, ...ul3, ...ul4, ...ul5]
      console.log(links)
      links = links.map((item, index) => {
        item.id = 'link' + index
        return item
      })

      this.graphData.nodes = nodes
      this.graphData.links = links
    },
    formatUser (data, prefix) {
      return data.map((item, index) => {
        return {
          id: prefix +'-' + index,
          name: item,
          type: 'user'
        }
      })
    },
    userLink (data, projectData) {
      return data.map(item => {
        return {
          source: projectData.id,
          target: item.id
        }
      })
    }
  },
}
</script>

<style lang="less" scoped>
@import 'index';

.sub-count {
  display: flex;
  justify-content: space-around;
  align-items: center;
  div {
    font-size: 12px;
    font-weight: bold;
  }
  span {
    font-size: 16px;
  }
}
.main-count {
  height: 90px;
}

/deep/ .ant-statistic {
  margin-left: 50px;
  margin-top: 20px;
  .ant-statistic-title {
    font-size: 13px;
    font-weight: bold;
    color: #333;
  }
  .ant-statistic-content-value {
    font-weight: bold;
    font-size: 30px;
  }
}
</style>