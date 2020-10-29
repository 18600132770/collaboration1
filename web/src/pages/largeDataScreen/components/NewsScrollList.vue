<template>
  <div class="list-wrapper">
    <a-carousel class="seamless-warp" :auto-play="true" :adaptive-height="true" :after-change="onChange">
      <div class="carouse1-item" v-for="(item, index) in listData" :key="index">
        <img :src="item.src" alt="">
        <div class="text">
          <div class="title">
            {{item.title}}
            <!-- <span style="float: right;">{{item.date}}</span> -->
          </div>
          <p class="num-color">{{item.content}}</p>
        </div>
      </div>
    </a-carousel>
    
    <!-- <vueSeamless 
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
    </vueSeamless> -->
  </div>
</template>

<script>
// import vueSeamless from 'vue-seamless-scroll'
export default {
  name: 'NewsScrollList',
  components: {
    // vueSeamless
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
        }
      ]
    }
  },
  methods: {
    onChange () {}
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
    .carouse1-item {
      height: 36vh;
      position: relative;
      .text {
        position: absolute;
        bottom: 0;
        background-color: rgba(0, 0, 0, .5);
        color: #EFF4F9;
        height: 50px;
        left: 0;
        right: 0;
        padding-left: 5px;
        p {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          font-size: 12px;
        }
      }
      img {
        width: 100%;
        height: 100%;
      }
      .title {
        font-size: 13px;
        font-weight: 600;
        color: #DBB019;
        display: block;
      }
    }
  }
</style>