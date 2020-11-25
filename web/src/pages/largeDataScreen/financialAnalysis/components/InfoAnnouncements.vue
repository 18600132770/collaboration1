<template>
  <div class="list-wrapper">
    <ul class="list-container list-header">
      <li class="list-item">
        <a-row>
          <a-col :span="6">时间</a-col>
          <a-col :span="4">类型</a-col>
          <a-col :span="12">主题</a-col>
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
            <a-col :span="6">{{item.date}}</a-col>
            <a-col :span="4" class="num-color">{{item.topic}}</a-col>
            <a-col :span="14" class="num-color">{{item.content}}</a-col>
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
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-17',
          topic: '提醒',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-18',
          topic: '通知',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-19',
          topic: '会议',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-20',
          topic: '会议',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-21',
          topic: '通知',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-22',
          topic: '提醒',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-23',
          topic: '通知',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-24',
          topic: '通知',
          content: '各单位请注意，各单位请注意'
        },
        {
          date: '2020-10-25',
          topic: '通知',
          content: '各单位请注意，各单位请注意'
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