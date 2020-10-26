<template>
  <div class="list-wrapper">
    <!-- <ul class="list-container list-header">
      <li class="list-item">
        <a-row>
          <a-col :span="6">类型</a-col>
          <a-col :span="12">主题</a-col>
          <a-col :span="4">时间</a-col>
        </a-row>
      </li>
    </ul> -->
    <vueSeamless 
      :data="listData" 
      :class-option="{waitTime: 2000,step: 0.4}" 
      class="seamless-warp"
    >
      <ul class="list-container">
        <li class="list-item" v-for="(item, index) in listData" :key="index">
          <a-row>
            <a-col :span="6">
              <img :src="item.src" alt="">
            </a-col>
            <a-col :span="14">
              <span>{{item.title}}</span>
              <p>{{item.content}}</p></a-col>
            <a-col :span="4" class="date">{{item.date}}</a-col>
          </a-row>
        </li>
      </ul>
    </vueSeamless>
  </div>
</template>

<script>
import vueSeamless from 'vue-seamless-scroll'
export default {
  name: 'NewsScrollList',
  components: {
    vueSeamless
  },
  data () {
    return {
      listData: [
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-16',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-17',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-18',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-19',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-20',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-21',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-22',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-23',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-24',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        },
        {
          title: 'SOM 建筑事务所与由城市规划师',
          date: '2020-10-25',
          src: 'https://static.vsochina.com/newsfile/2019/0718/20190718023817275.jpg',
          content: 'SOM 建筑事务所与由城市规划师，景观设计师和社区智库组成的团队一起'
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
  ul, li, p {
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
          img {
            width: 80%;
            // height: 100%;
          }
          span {
            font-size: 13px;
            font-weight: 600;
            color: #DBB019;
            display: inline-block;
            margin-bottom: 8px;
          }
          p {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }
        .num-color {
          color: #DBB019;
        }
        &:nth-child(even) {
          background-color: #2E3347;
        }
        .date {
          line-height: 50px;
          text-align: right;
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
    height: 100%;
    overflow: hidden;
  }
</style>