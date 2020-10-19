<template>
  <page-layout>
    <div class="card-list">
      <a-list
        :grid="{gutter: 24, lg: 4, md: 2, sm: 1, xs: 1}"
        :dataSource="dataSource"
      >
        <a-list-item slot="renderItem" slot-scope="item" @click="linkToDataScreen(item)">
          <template v-if="false && item.add">
            <a-button class="new-btn" type="dashed">
              <a-icon type="plus" />新增产品
            </a-button>
          </template>
          <template v-else>
            <a-card>
              <img slot="cover" src="~@/assets/img/bigscreen/datascreen.png" height="164"/>
              <a-card-meta title="数据大屏">
                <div slot="description">
                  城镇中有那么多的酒馆，她却偏偏走进了我的酒馆
                </div>
              </a-card-meta>
            </a-card>
          </template>
        </a-list-item>
      </a-list>
    </div>
  </page-layout>
</template>

<script>
import PageLayout from '@/layouts/PageLayout'

const dataSource = []
dataSource.push({
  add: true
})
for (let i = 0; i < 11; i++) {
  dataSource.push({
    title: 'Alipay',
    avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
    content: '在中台产品的研发过程中，会出现不同的设计规范和实现方式，但其中往往存在很多类似的页面和组件，这些类似的组件会被抽离成一套标准规范。'
  })
}

export default {
  name: 'CardList',
  components: {
    PageLayout
  },
  data () {
    return {
      desc: '段落示意：蚂蚁金服务设计平台 ant.design，用最小的工作量，无缝接入蚂蚁金服生态， 提供跨越设计与开发的体验解决方案。',
      linkList: [
        {icon: 'rocket', href: '/#/', title: '快速开始'},
        {icon: 'info-circle-o', href: '/#/', title: '产品简介'},
        {icon: 'file-text', href: '/#/', title: '产品文档'}
      ],
      extraImage: 'https://gw.alipayobjects.com/zos/rmsportal/RzwpdLnhmvDJToTdfDPe.png',
      dataSource
    }
  },
  methods: {
    // 跳转到数据大屏
    linkToDataScreen
  }
}

function linkToDataScreen (item) {
  const { href } = this.$router.resolve({
    path: '/largeDataScreen',
    query: {
      item
    }
  })
  window.open(href, '_blank')
}
</script>

<style lang="less" scoped>
  .card-avatar {
    width: 48px;
    height: 48px;
    border-radius: 48px;
  }
  .new-btn{
    border-radius: 2px;
    width: 100%;
    height: 187px;
  }
  .meta-content{
    position: relative;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    height: 64px;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
  }

</style>
