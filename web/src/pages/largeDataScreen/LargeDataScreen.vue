<template>
  <div class="container">
    <header class="header">
      <span>智慧运营管理中心</span>
    </header>
    <section class="main">
      <div class="left">
        <div class="chart-container">
          <header>目标管理</header>
          <!-- height: calc(100% - 30px); -->
          <!-- <a-carousel :autoplay="true">
            <div> -->
              <section class="circle-pregress">
                <ProgressGauge class="width-50"></ProgressGauge>
                <div class="remaining-days">
                  <div>目标营业收入</div>
                  <div><span>200</span> 亿</div>
                  <div>已完成营业收入</div>
                  <div><span>161.781</span> 亿</div>
                </div>
              </section>
            <!-- </div>
            <div>
              <section class="circle-pregress">
                <ProgressGauge1 class="width-50"></ProgressGauge1>
                <div class="remaining-days">
                  <div>总合同额</div>
                  <div><span>483</span> 亿</div>
                </div>
              </section>
            </div>
          </a-carousel> -->
        </div>
        <div class="chart-container">
          <header>经营状况（净利润/营业收入）</header>
          <section>
            <BarChart></BarChart>
          </section>
        </div>
        <div class="chart-container">
          <header>人工投入曲线</header>
          <section>
            <LineChart></LineChart>
          </section>
        </div>
      </div>
      <div class="center">
        <div class="chart-container total-card">
          <div class="card-item">
            <div>
              合同总额<span>328</span>亿元
            </div>
          </div>
          <div class="card-item">
            <div>
              项目数量<span>715</span>个
            </div>
          </div>
          <div class="card-item">
            <div>
              员工人数<span>4280</span>人
            </div>
          </div>
        </div>
        <div class="map-container">
          <ChinaMap></ChinaMap>
        </div>
        <!-- <div class="chart-container"></div> -->
      </div>
      <div class="right">
        <div class="chart-container">
          <header @click="linkToProject" style="cursor: pointer;">重点项目</header>
          <section>
            <VerBarChart></VerBarChart>
          </section>
        </div>
        <div class="chart-container">
          <header>行业板块占比</header>
          <section>
            <PieChart></PieChart>
          </section>
        </div>
        <div class="chart-container">
          <header>成本分析</header>
          <section>
            <RadarChart></RadarChart>
          </section>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import BarChart from './components/BarChart'
import ChinaMap from './components/ChinaMap'
import LineChart from './components/LineChart'
import VerBarChart from './components/VerBarChart'
import ProgressGauge from './components/ProgressGauge'
// import ProgressGauge1 from './components/ProgressGauge1'
import PieChart from './components/PieChart'
import RadarChart from './components/RadarChart'

export default {
  name: 'LargeDataScreen',
  components: {
    BarChart,
    ChinaMap,
    LineChart,
    VerBarChart,
    ProgressGauge,
    // ProgressGauge1,
    PieChart,
    RadarChart
  },
  data () {
    return {
      
    }
  },
  methods: {
    linkToProject () {
      const { href } = this.$router.resolve({
        path: '/projectdatascreen'
      })

      window.open(href, '_blank')
    }
  }
}
</script>

<style lang="less" scoped>
  @header-height: 60px;
  @chart-title-color: #15A3FF;

  .container {
    height: 100%;
    width: 100%;
    background-image: url(~@/assets/img/bigscreen/bg.png);
    background-size: 100% 100%;
    .header {
      height: @header-height;
      background-image: url(~@/assets/img/bigscreen/header.png);
      background-size: 100% 100%;
      text-align: center;
      span {
        font-size: 30px;
        color: @chart-title-color;
        font-weight: 600;
        line-height: @header-height;
      }

      // border: 1px solid #ddd;
    }
    .main {
      height: calc(100% - @header-height);
      display: flex;

      .left, .right {
        width: 500px;
        padding: 8px 8px 8px 16px;
        display: flex;
        flex-direction: column;
        .chart-container {
          margin: 8px 0;
          flex: auto;
          background-image: url(~@/assets/img/bigscreen/cyjgborder.png);
          background-size: 100% 100%;
          height: calc(33.3% - 20px);
          header {
            height: 46px;
            line-height: 46px;
            color: @chart-title-color;
            padding: 0 10px;
            font-weight: 600;
            font-size: 18px;
          }
          section {
            width: 100%;
            height: calc(100% - 46px);
          }
          section.circle-pregress {
            display: flex;
          }
        }
        // border: 1px solid #ddd;
      }
      .right {
        padding: 8px 16px 8px 8px;
      }
      .center {
        display: flex;
        flex-direction: column;
        flex: 1;
        padding: 16px 8px;
        .chart-container {
          height: 110px;
          // border: 1px solid #ddd;
          &:last-child {
            height: 200px;
          }
        }
        .map-container {
          margin: 16px 0;
          flex: 1;
        }
      }
    }
    /deep/ .ant-carousel .slick-dots {
      display: none !important;
    }
  }

  .width-50 {
    width: 50% !important;
  }

  .remaining-days {
    width: 50%;
    height: 100%;
    display: flex;
    flex-direction: column;
    color: #ffffff;
    align-items: center;
    justify-content: center;
    > div {
      width: 100%;
      color: @chart-title-color;
      padding: 0;
      font-size: 16px;
      font-weight: 600;
      &:last-child, &:nth-child(2){
        text-align: right;
        margin-right: 35px;
      }
      span {
        text-align: center;
        font-size: 30px;
      }
    }
  }

  .total-card {
    width: 100%;
    display: flex;
    justify-content: space-around;
    align-items: flex-end;
    .card-item {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 80%;
      width: 25%;
      background-image: url(~@/assets/img/bigscreen/card-bg.png);
      background-size: 100% 100%;
      color: @chart-title-color;
      font-weight: 600;
      span {
        font-size: 24px;
        padding: 0 10px;
      }
    }
  }

  @media screen and (max-width: 1920px) {
    .container {
      .main {
        .left, .right {
          width: 420px;
          .chart-container {
            header {
              height: 30px;
              line-height: 30px;
              color: @chart-title-color;
              padding: 0 10px;
              font-weight: 600;
            }
            section {
              height: calc(100% - 30px);
            }
          }
        }
        .center {
          .chart-container {
            height: 100px;
            &:last-child {
              height: 130px;
            }
          }
        }
      }
    }
  }

</style>
