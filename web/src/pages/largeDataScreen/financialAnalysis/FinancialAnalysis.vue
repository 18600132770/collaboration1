<template>
  <div class="container">
    <header class="header">
      <div class="head-title">
        <!-- <div class="logo"></div> -->
        <span>公司财务分析系统</span>
        <span class="date"><a-icon type="clock-circle" /> {{currentDate}}</span>
      </div>
    </header>
    <section class="main">
      <div class="left">
        <div class="chart-container" style="height: 200px">
          <div class="small-col">
            <!-- <header>每月总应收款</header> -->
            <section style="height: 100%;">
              <ProgressGauge :percent-data="currentData" class="width-50"></ProgressGauge>
              <div class="width-50">
                <div class="remaining-days">
                  <div>目标营业收入</div>
                  <div class="num"><span>200</span> 亿</div>
                  <div>已完成营业收入</div>
                  <div class="num"><span>161.781</span> 亿</div>
                  <div>当月现金流</div>
                  <div class="num"><span>100</span> 亿</div>
                  <div>当前带息负债</div>
                  <div class="num"><span>61.781</span> 亿</div>
                </div>
              </div>
            </section>
          </div>
        </div>
        <div class="chart-container left-bottom">
          <div class="small-col">
            <header>支出</header>
            <section>
              <SpendingTable></SpendingTable>
            </section>
          </div>
        </div>
         <div class="chart-container">
          <div class="small-col">
            <header>当月成本</header>
            <section class="cost">
              <div class="cost-item">
                <div class="title">当月工资总额</div>
                <div class="value">1000万</div>
              </div>
              <div class="cost-item">
                <div class="title">当月企业年金</div>
                <div class="value">2000万</div>
              </div>
              <div class="cost-item">
                <div class="title">其他</div>
                <div class="value">3000万</div>
              </div>
            </section>
          </div>
        </div>
      </div>
      <div class="center">
        <div class="chart-container">
          <header>新签合同额</header>
          <section>
            <LineChart></LineChart>
          </section>
        </div>
        <div class="chart-container">
          <header>营业收入</header>
          <section>
            <LineChart2></LineChart2>
          </section>
        </div>
        <div class="chart-container">
          <header>成本支出</header>
          <section>
            <LineChart3></LineChart3>
          </section>
        </div>
      </div>
      <div class="right">
        <div class="chart-container right-top">
          <div class="small-col">
            <header>预警</header>
            <section>
              <InfoAnnouncements style="width: 100%;height: calc(100% - 46px);"></InfoAnnouncements>
            </section>
          </div>
        </div>
        <div class="chart-container right-bottom">
          <div style="height: 100%;">
            <header>税务</header>
            <section style="height: calc(100% - 46px);">
              <div class="small-col">
                <!-- <header>每月总应收款</header> -->
                <section style="height: 100%;">
                  <div class="width-50">
                    <div class="sum-shui">
                      <div>利润目标</div>
                      <div><span>200</span> 万</div>
                    </div>
                    <ProfitDashChart style="height: calc(100% - 47px)"></ProfitDashChart>
                  </div>
                  <div class="width-50">
                    <div class="sum-shui">
                      <div>本年度累计缴税</div>
                      <div><span>20</span> 亿</div>
                    </div>
                    <DashChart style="height: calc(100% - 47px)"></DashChart>
                  </div>
                </section>
              </div>
            </section>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { format } from 'date-fns'

import ProgressGauge from './components/ProgressGauge'
import LineChart from './components/LineChart'
import LineChart2 from './components/LineChart2'
import LineChart3 from './components/LineChart3'
import InfoAnnouncements from './components/InfoAnnouncements'
import SpendingTable from './components/SpendingTable'
import DashChart from './components/DashChart'
import ProfitDashChart from './components/ProfitDashChart'

export default {
  name: 'FinalcialAnalysis',
  components: {
    ProgressGauge,
    LineChart,
    LineChart2,
    LineChart3,
    InfoAnnouncements,
    SpendingTable,
    DashChart,
    ProfitDashChart
  },
  data () {
    return {
      currentDate: format(new Date(), 'yyyy-MM-dd'),
      currentData: 60
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