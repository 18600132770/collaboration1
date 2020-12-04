<template>
  <div class="container">
    <header class="header">
      <div class="head-title">
        <!-- <div class="logo"></div> -->
        <span>公司经营分析系统</span>
        <span class="date"><a-icon type="clock-circle" /> {{currentDate}}</span>
      </div>
    </header>
    <section class="main">
      <div class="left">
        <div class="chart-container">
          <header>项目收款总览</header>
          <section>
            <CollectionsTable></CollectionsTable>
          </section>
        </div>
        <div class="chart-container left-bottom">
          <div class="small-col">
            <header>每月总应收款</header>
            <section>
              <BarChart></BarChart>
            </section>
          </div>
          <div class="small-col">
            <!-- <section> -->
              <!-- <ChinaMap></ChinaMap> -->
            <!-- </section> -->
            <a-carousel autoplay>
              <CarouselItem title="储备项目合同额">
                <ChinaMap></ChinaMap>
              </CarouselItem>
               <CarouselItem title="进行中项目合同额">
                <ChinaMap1></ChinaMap1>
              </CarouselItem>
               <CarouselItem title="本年营业收入">
                <ChinaMap2></ChinaMap2>
              </CarouselItem>
            </a-carousel>
          </div>
        </div>
      </div>
      <div class="right">
        <div class="chart-container right-top">
          <div class="small-col">
            <header>合同额</header>
            <section>
              <PieChartThree></PieChartThree>
            </section>
          </div>
          <div class="small-col">
            <TenderProject></TenderProject>
          </div>
        </div>
        <div class="chart-container">
          <div style="height: 100%;">
            <header>储备项目</header>
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

import TenderProject from './components/TenderProject'
import BarChart from './components/BarChart'
import PieChartThree from './components/PieChartThree'
import ChinaMap from './components/ChinaMap'
import ChinaMap1 from './components/ChinaMap1'
import ChinaMap2 from './components/ChinaMap2'
import ProjectTable from './components/ProjectTable'
import CollectionsTable from './components/CollectionsTable'

import CarouselItem from './components/CarouselItem'

export default {
  name: 'BusinessAnalysis',
  components: {
    TenderProject,
    BarChart,
    PieChartThree,
    ChinaMap,
    ChinaMap1,
    ChinaMap2,
    ProjectTable,
    CollectionsTable,
    CarouselItem
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
</style>