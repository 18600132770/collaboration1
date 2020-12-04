<!--
 * @Description: file content
 * @Author: supercheney
 * @Date: 2020-12-04 23:44:13
 * @LastEditors: supercheney
 * @LastEditTime: 2020-12-05 00:20:39
 * @FilePath: \web\src\pages\largeDataScreen\businessAnalysis\components\CarouselItem.vue
-->
<template>
  <div class="item-container">
    <header>{{title}}</header>
    <div>
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: ['title'],
  components: {
  },
  data () {
    return {

    }
  }
}
</script>

<style lang="less" scoped>
  .item-container {
    height: 280px;
    header {
      height: 30px;
      line-height: 30px;
      color: #fff;
      padding: 0 10px;
      font-weight: 600;
      font-size: 16px;
    }
    > div {
      height: calc(100% - 40px);
    }
  }
</style>
