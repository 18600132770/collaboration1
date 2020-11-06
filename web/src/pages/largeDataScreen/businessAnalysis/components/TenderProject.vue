<template>
  <div class="list-wrapper">
    <ul class="list-container list-header">
      <li class="list-item">
        <a-row>
          <a-col :span="6">项目名称</a-col>
          <a-col :span="6">状态</a-col>
          <a-col :span="6">开标日期</a-col>
          <a-col :span="6">部门负责人</a-col>
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
            <a-col :span="6" class="num-color">{{item.name}}</a-col>
            <a-col :span="6" class="num-color">{{item.status}}</a-col>
            <a-col :span="6">{{item.date}}</a-col>
            <a-col :span="6" class="num-color">{{item.content}}</a-col>
          </a-row>
        </li>
      </ul>
    </vueSeamless>
  </div>
</template>

<script>
import vueSeamless from 'vue-seamless-scroll'
export default {
  name: 'TenderProject',
  components: {
    vueSeamless
  },
  data () {
    return {
      listData: [
        {
          date: '2020-10-16',
          name: '项目1',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-17',
          name: '项目2',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-18',
          name: '项目3',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-19',
          name: '项目4',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-20',
          name: '项目5',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-21',
          name: '项目6',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-22',
          name: '项目7',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-23',
          name: '项目8',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-24',
          name: '项目9',
          status: '已中标',
          content: '张三'
        },
        {
          date: '2020-10-25',
          name: '项目10',
          status: '已中标',
          content: '张三'
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