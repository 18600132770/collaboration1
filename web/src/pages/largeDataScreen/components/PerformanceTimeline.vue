<template>
  <div class="timeline-wrapper">
    <div class="beaty-scroll" style="overflow-y: scroll;">
      <a-timeline>
        <a-timeline-item v-for="(item, index) in performanceData" :key="index" :color="iconColor[item.type]">
          <a-icon slot="dot" v-if="item.type" :type="iconType[item.type]" style="font-size: 16px;" />
          <span class="date">{{item.date}}</span>
          <span class="content">{{item.text}}</span> 
        </a-timeline-item>
      </a-timeline>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PerformanceTimeline',
  data () {
    return {
      iconType: {
        success: 'check-circle',
        doing: 'clock-circle-o',
        error: 'close-circle'
      },
      iconColor: {
        success: 'green',
        error: 'red',
      },
      performanceData: [
        {
          date: '2019-09-01',
          text: 'Create a services site',
          type: 'success'
        },
        {
          date: '2020-03-11',
          text: 'Create a services site',
          type: 'success'
        },
        {
          date: '2020-08-02',
          text: 'Create a services site',
          type: 'doing'
        },
        {
          date: '2020-11-16',
          text: 'Create a services site',
          type: ''
        },
        {
          date: '2021-02-10',
          text: 'Create a services site',
          type: ''
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
  .timeline-wrapper {
    padding: 8px;
    height: 100%;
    overflow: hidden;
    > div {
      width: calc(100% + 30px);
      height: 100%;
    }
    /deep/ .ant-timeline {
      padding-top: 10px;
      padding-left: 100px;
      .ant-timeline-item-head {
        background-color: #24293D;
      }
      .ant-timeline-item-content {
        color: #EFF4F9;
      }
      .date {
        position: relative;
        left: -100px;
      }
      .content {
        position: relative;
        left: -70px;
      }
    }
  }
</style>