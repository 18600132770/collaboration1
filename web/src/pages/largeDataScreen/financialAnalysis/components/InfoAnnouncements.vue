<template>
  <div class="list-wrapper">
    <ul class="list-container list-header">
      <li class="list-item">
        <a-row>
          <!-- <a-col :span="4">类型</a-col> -->
          <a-col :span="16">公告</a-col>
          <a-col :span="8">时间</a-col>
        </a-row>
      </li>
    </ul>
    <vueSeamless 
      :data="listData" 
      :class-option="{waitTime: 2000,step: 0.4}" 
      class="seamless-warp"
    >
      <ul class="list-container">
        <li class="list-item" v-for="(item, index) in listData" :key="index">
          <a-row>
            <!-- <a-col :span="4" class="num-color">{{item.topic}}</a-col> -->
            <a-col :span="16" class="num-color" :class="{'danger': item.type === 'danger', 'blue': item.type === 'blue'}">{{item.content}}</a-col>
            <a-col :span="8">{{item.date}}</a-col>
          </a-row>
        </li>
      </ul>
    </vueSeamless>
  </div>
</template>

<script>
import vueSeamless from 'vue-seamless-scroll'
export default {
  name: 'InfoAnnouncements',
  components: {
    vueSeamless
  },
  data () {
    return {
      listData: [
        {
          date: '2020-10-16',
          topic: '通知',
          content: '马东铁路收款延期',
          type: 'danger'
        },
        {
          date: '2020-10-17',
          topic: '提醒',
          content: '中国银行还款日期2020年12月20日',
          type: 'warning'
        },
        {
          date: '2020-10-18',
          topic: '通知',
          content: '10月预算超支',
          type: 'warning'
        },
        {
          date: '2020-10-19',
          topic: '会议',
          content: '新税法正式实施',
          type: 'blue'
        },
        {
          date: '2020-10-20',
          topic: '会议',
          content: '马东铁路收款延期',
          type: 'danger'
        },
        {
          date: '2020-10-21',
          topic: '通知',
          content: '中国银行还款日期2020年12月20日',
          type: 'warning'
        },
        {
          date: '2020-10-22',
          topic: '提醒',
          content: '10月预算超支',
          type: 'warning'
        },
        {
          date: '2020-10-23',
          topic: '通知',
          content: '新税法正式实施',
          type: 'blue'
        },
        {
          date: '2020-10-24',
          topic: '通知',
          content: '中国银行还款日期2020年12月20日',
          type: 'warning'
        },
        {
          date: '2020-10-25',
          topic: '通知',
          content: '马东铁路收款延期',
          type: 'danger'
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
  ul, li {
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .list-wrapper {
    height: 100%;
    .list-container {
      .list-item {
        color: #EFF4F9;
        padding: 8px;
        .ant-col {
          color: #757C8C;
          font-size: 12px;
          font-weight: 600;
          white-space: nowrap;
        }
        .num-color {
          color: #DBB019;
        }
        .danger {
            color: red !important;
        }
        .blue {
          color: blueviolet !important;
        }
        &:nth-child(even) {
          background-color: #1E2026;
        }
      }
    }
    /deep/ .list-header {
      .list-item {
        .ant-col {
          font-weight: 600;
          font-size: 14px;
          color: #EFF4F9;
        }
      }
    }
  }
  .seamless-warp {
    height: calc(100% - 37px);
    overflow: hidden;
  }
</style>