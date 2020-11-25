<template>
  <div class="container">
    <header class="header">
      <div class="head-title">
        <!-- <div class="logo"></div> -->
        <span>公司人力资源分析系统</span>
        <span class="date"><a-icon type="clock-circle" /> {{currentDate}}</span>
      </div>
    </header>
    <section class="main">
      <div class="left">
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
      <div class="main"></div>
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
          <div style="height: 100%;">
            <!-- <header>储备项目</header> -->
            <section style="height: 100%;">
              <BaseMap></BaseMap>
            </section>
          </div>
          <div style="height: 100%;">
            <!-- <header>图谱</header> -->
            <section>
              <ProjectTable></ProjectTable>
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
    FunnelChart
    /* TenderProject,
    BarChart,
    ChinaMap,
    ProjectTable,
    CollectionsTable */
  },
  data () {
    return {
      currentDate: format(new Date(), 'yyyy-MM-dd'),
    }
  }
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
  height: 90px
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