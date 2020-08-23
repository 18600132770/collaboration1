<!--
 * @Description: layout页面
 * @Author: supercheney
 * @Date: 2020-08-22 15:54:47
 * @LastEditors: supercheney
 * @LastEditTime: 2020-08-22 23:02:28
 * @FilePath: \webpage\src\views\Layout\index.vue
-->

<template>
  <div class="main-layout">
    <div class="l-header" ref="header" v-show="!hideHeader">
      <Header></Header>
    </div>
    <div class="main" ref="main" :class="{'hide-header': hideHeader, 'hide-footer': hideFooter}">
      <router-view />
    </div>
    <div class="l-footer" ref="footer" v-if="!hideFooter">
      <!-- <Footer></Footer> -->
    </div>
  </div>
</template>
<script>
import Header from './Header'
// import Footer from './Footer'

import { mapGetters } from 'vuex'

export default {
  data () {
    return {
      mainHeight: ''
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.mainHeight = this.$refs.main // .getBoundingClientRect().height
    })
  },
  computed: {
    ...mapGetters(['hideHeader', 'hideFooter'])
  },
  components: {
    Header
    // Footer
  }
}
</script>

<style lang="less">
.main-layout {
  height: 100%;
  // padding-top: 82px;
  // padding-bottom: 65px;
  // position: relative;
  .l-header {
    height: 65px;
    width: 100%;
    position: fixed;
    top: 0;
  }
  .l-footer {
    position: absolute;
    bottom: 0;
    height: 65px;
    width: 100%;
  }
  .main {
    position: absolute;
    top: 65px;
    bottom: 65px;
    left: 0;
    right: 0;
    background: #fff;
    // height: 100%;
    // padding-top: 82px;
    // padding-bottom: 65px;
    .l-scrollbar {
      height: 100%;
      .el-scrollbar__wrap {
        overflow-x: hidden;
        margin-right: -19px !important;
      }
    }
  }
  .hide-header {
    top: 0;
  }
  .hide-footer {
    bottom: 0;
  }
  .el-scrollbar__view {
    height: 100%;
  }
}
</style>
