<template>
  <div class="container">
    <header class="header">
      <div class="head-title">
        <div class="logo">企业LOGO</div>
        <span>项目信息实时监控</span>
      </div>
      <div class="project-select"></div>
    </header>
    <section class="section">
      <a-row :gutter="16" class="card row-1">
        <a-col class="gutter-row" :span="4">
          <div class="gutter-box">
            <div class="main-num">
              <a-statistic title="总合同额" :value="112893" style="margin-right: 50px">
                <template #suffix>万元</template>
              </a-statistic>
            </div>
            <div class="sub">
              <a-statistic title="分包合同额" :value="112893">
                <template #suffix>万元</template>
              </a-statistic>
              <a-statistic title="实际合同额" :precision="2" :value="2893">
                <template #suffix>万元</template>
              </a-statistic>
            </div>
          </div>
        </a-col>
        <a-col class="gutter-row" :span="4">
          <div class="gutter-box">
            <div class="main-num">
              <a-statistic title="累计收款" :value="370180" style="margin-right: 50px">
                <template #suffix>万元</template>
              </a-statistic>
            </div>
            <div class="sub">
              <a-statistic title="应收款" :value="201745">
                <template #suffix>万元</template>
              </a-statistic>
              <a-statistic title="应收未收款" :precision="2" :value="2893">
                <template #suffix>万元</template>
              </a-statistic>
            </div>
          </div>
        </a-col>
        <a-col class="gutter-row" :span="4">
          <div class="gutter-box">
            <div class="main-num">
              <a-statistic title="累计成本" :value="370180" style="margin-right: 50px">
                <template #suffix>万元</template>
              </a-statistic>
            </div>
            <div class="sub">
              <a-statistic title="人工" :value="201745">
                <template #suffix>万元</template>
              </a-statistic>
              <a-statistic title="其他" :precision="2" :value="2893">
                <template #suffix>万元</template>
              </a-statistic>
            </div>
          </div>
        </a-col>
        <a-col class="gutter-row" :span="4">
          <div class="gutter-box">
            <div class="main-num">
              <a-statistic title="当前利润" :value="2045" style="margin-right: 50px">
                <template #suffix>万元</template>
              </a-statistic>
            </div>
            <div class="sub">
              <a-statistic title="净利润" :value="1024">
                <template #suffix>万元</template>
              </a-statistic>
              <a-statistic title="利润率" :precision="2" :value="2.49">
                <template #suffix>%</template>
              </a-statistic>
            </div>
          </div>
        </a-col>
        <a-col class="gutter-row" :span="8">
          <div class="gutter-box">
            <!-- https://gallery.echartsjs.com/editor.html?c=xnUHWTiDTY&v=3 -->
            <ProjectProgress></ProjectProgress>
          </div>
        </a-col>
      </a-row>

      <a-row :gutter="16" class="card row-2">
        <a-col class="gutter-row" :span="8">
          <div class="gutter-box bg-transparent" style="position: relative;">
            <div class="line-chart-title">进度折线图</div>
            <PieLineChart></PieLineChart>
          </div>
        </a-col>
        <a-col class="gutter-row" :span="16">
          <div class="gutter-box bg-transparent">
            <a-row style="height: 100%;">
              <!-- 绩表 -->
              <a-col :span="10" style="height: 100%;">
                <PerformanceTimeline></PerformanceTimeline>
              </a-col>
              <a-col :span="14" style="height: 100%;">
                <ScrollList></ScrollList>
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-row>

      <a-row :gutter="16" class="card row-3">
        <a-col class="gutter-row" :span="8">
          <div class="gutter-box">
            col-6
          </div>
        </a-col>
        <a-col class="gutter-row" :span="8">
          <div class="gutter-box bg-transparent">
            <NewsScrollList></NewsScrollList>
          </div>
        </a-col>
        <a-col class="gutter-row" :span="8">
          <div class="gutter-box bg-transparent">
            <InfoAnnouncements></InfoAnnouncements>
          </div>
        </a-col>
      </a-row>
    </section>
  </div>
</template>

<script>
import PieLineChart from './components/PieLineChart'
import PerformanceTimeline from './components/PerformanceTimeline'
import ScrollList from './components/ScrollList'
import InfoAnnouncements from './components/InfoAnnouncements'
import NewsScrollList from './components/NewsScrollList'
import ProjectProgress from './components/ProjectProgress'


export default {
  components: {
    PieLineChart, 
    PerformanceTimeline, 
    ScrollList, 
    InfoAnnouncements,
    NewsScrollList,
    ProjectProgress
  }
}
</script>

<style lang="less" scoped>
  @header-height: 60px;
  @chart-title-color: #fff;

  .bg-transparent {
    background-color: transparent !important;
  }

  .container {
    padding: 15px 20px;
    width: 100vw;
    height: 100vh;
    background-color: #24293D;
    .header {
      height: @header-height;
      border-bottom: 1px solid #2F3448;
      display: flex;
      justify-content: space-between;
      .head-title {
        display: flex;
        align-items: center;
        .logo {
          border: 2px solid #DBB019;
          width: 125px;
          height: 40px;
          color: #DBB019;
          line-height: 40px;
          text-align: center;
        }
        span {
          font-size: 28px;
          font-weight: 600;
          color: @chart-title-color;
          margin-left: 20px;
        }
      }
    }
    .section {
      padding: 10px 0;
      .card {
        margin-bottom: 16px;
        .gutter-box {
          height: 100%;
          background-color: #2E3347;
          height: 19vh;
        }
        &:last-child {
          margin-bottom: 0;
        }
      }
      .card.row-2 .gutter-box {
        height: 27vh;
      }
      .card.row-3 .gutter-box {
        height: 36vh;
      }
      .card.row-1 {
        .gutter-box {
          padding: 15px;
          display: flex;
          flex-direction: column;
          > div {
            height: 60%;
            width: 100%;
          }
          /deep/ .main-num {
            .ant-statistic-title {
              font-size: 14px;
              color: #EFF4F9;
              font-weight: 600;
            }
            .ant-statistic-content-value {
              font-size: 28px;
              color: #DBB019;
            }
            .ant-statistic-content-suffix {
              color: #DBB019;
            }
          }
          /deep/ .sub {
            padding-top: 10px;
            display: flex;
            height: 40%;
            .ant-statistic {
              width: 50%;
              line-height: 1;
            }
            .ant-statistic-title {
              color: #5B5A6C;
              font-size: 12px;
              font-weight: 600;
              margin-bottom: 0;
            }
            .ant-statistic-content-value {
              font-size: 16px;
              color: #EFF4F9;
            }
            .ant-statistic-content-suffix {
              color: #EFF4F9;
              font-size: 12px;
            }
          }
        }
      }
      .line-chart-title {
        font-size: 14px; 
        position: absolute; 
        left: 25%; 
        top: 7%;
        color: #fff; 
        font-weight: 600; 
      }
    }
  }

 @media screen and (min-width: 1920px) {
    .container {
      section.section {
        .card {
          .gutter-box {
            height: 20vh;
          }
        }
        .card.row-2 .gutter-box {
          height: 28vh;
        }
        .card.row-3 .gutter-box {
          height: 39vh;
        }
      }
    }
  }
</style>