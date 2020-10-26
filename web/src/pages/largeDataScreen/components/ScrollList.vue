<template>
  <div class="list-wrapper">
    <ul class="list-container list-header">
        <li class="list-item">
          <a-row>
            <a-col :span="6">专业</a-col>
            <a-col :span="6">负责人</a-col>
            <a-col :span="6">参与项目人数</a-col>
            <a-col :span="6">出差人数</a-col>
          </a-row>
        </li>
      </ul>
    <vueSeamless class="seamless-warp" :data="listData" :class-option="{waitTime: 2000,step: 0.4}" >
      <ul class="list-container">
        <li class="list-item" v-for="(item, index) in listData" :key="index">
          <a-row>
            <a-col :span="6">{{item.professional}}</a-col>
            <a-col :span="6">{{item.head}}</a-col>
            <a-col :span="6" class="num-color">{{item.participants}}人</a-col>
            <a-col :span="6" class="num-color">{{item.travel}}人</a-col>
          </a-row>
        </li>
      </ul>
    </vueSeamless>
  </div>
</template>

<script>
import vueSeamless from 'vue-seamless-scroll'
export default {
  name: 'ScrollList',
  components: {
    vueSeamless
  },
  data () {
    return {
      listData: [
        {
          professional: '桥梁所',
          head: '张三',
          participants: '300',
          travel: '100'
        },
        {
          professional: '路基所',
          head: '李四',
          participants: '264',
          travel: '122'
        },
        {
          professional: '隧道所',
          head: '王五',
          participants: '377',
          travel: '70'
        },
        {
          professional: '地质所',
          head: '马六',
          participants: '189',
          travel: '80'
        },
        {
          professional: '桥梁所',
          head: '张三',
          participants: '300',
          travel: '100'
        },
        {
          professional: '路基所',
          head: '李四',
          participants: '264',
          travel: '122'
        },
        {
          professional: '隧道所',
          head: '王五',
          participants: '377',
          travel: '70'
        },
        {
          professional: '地质所',
          head: '马六',
          participants: '189',
          travel: '80'
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
        padding: 10px;
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
          background-color: #2E3347;
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