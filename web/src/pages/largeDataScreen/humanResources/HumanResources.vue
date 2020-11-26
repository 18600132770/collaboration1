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
            <!-- <header>每月总应收款</header> -->
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
            <header>合同额</header>
            <section>
              <PieChartSix></PieChartSix>
            </section>
          </div>
        </div>
        <div class="chart-container right-bottom">
          <div style="height: 100%">
            <!-- <header>储备项目</header> -->
            <section style="height: 100%">
              <BaseMap></BaseMap>
            </section>
          </div>
          <div style="height: 100%">
            <!-- <header>图谱</header> -->
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
/* import BarChart from './components/BarChart'
import ChinaMap from './components/ChinaMap'
import ProjectTable from './components/ProjectTable'
import CollectionsTable from './components/CollectionsTable' */

export default {
  name: 'BusinessAnalysis',
  components: {
    PieChartSix,
    BaseMap,
    LineChart,
    FunnelChart,
    Graph,
    /* TenderProject,
    BarChart,
    ChinaMap,
    ProjectTable,
    CollectionsTable */
  },
  data() {
    return {
      currentDate: format(new Date(), 'yyyy-MM-dd'),

      // 图谱数据
      graphData: {
        nodes: [
          {
            inOfficeYear: '38',
            isRoot: 1,
            entityType: '1',
            sex: '男',
            lastYearOffice: '前210',
            firstYearOffice: '前247',
            entryId: 'R2006063350035680',
            labels: ['数字人文人物', '数字人文'],
            dynasty: '嬴秦',
            deathYear: '',
            birthYear: 'null',
            entryName: '嬴政',
            name: '桥梁所',
            personId: 'null',
            deathTitle: ';始皇帝;',
            ethnicityCode: 'null',
            id: '102123786',
            householdRegister: 'null',
            choronymCode: 'null',
          },
          {
            entityType: '1',
            entryName: '秦朝',
            name: '马东铁路',
            startYear: '-221.0',
            dyType: 'null',
            sort: '6.2',
            id: '101987033',
            endYear: '-206.0',
            entryId: 'R2008040190000021',
            dyId: '61.0',
            labels: ['数字人文朝代', '数字人文'],
          },
          {
            entityType: '1',
            entryName: '秦朝',
            name: '李四',
            startYear: '-221.0',
            dyType: 'null',
            sort: '6.2',
            id: '1019870331',
            endYear: '-206.0',
            entryId: 'R2008040190000021',
            dyId: '61.0',
            labels: ['数字人文朝代', '数字人文'],
          },
          {
            entityType: '1',
            entryName: '秦朝',
            name: '张三',
            startYear: '-221.0',
            dyType: 'null',
            sort: '6.2',
            id: '1019870333',
            endYear: '-206.0',
            entryId: 'R2008040190000021',
            dyId: '61.0',
            labels: ['数字人文朝代', '数字人文'],
          },
        ],
        links: [
          {
            name: '',
            id: '244841248',
            source: '102123786',
            target: '101987033',
          },
          {
            name: '',
            id: '2448412481',
            source: '101987033',
            target: '1019870331',
          },
          {
            name: '',
            id: '2448412481',
            source: '101987033',
            target: '1019870333',
          },
        ],
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
        mouseover: false,
      },
    }
  },
  methods: {
    onNodeClick() {},
    onNodeDBClick() {},
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