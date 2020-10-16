<template>
  <div class="container">
    <header class="header">

    </header>
    <section class="main">
      <div class="left">
        <div class="chart-container"></div>
        <div class="chart-container"></div>
        <div class="chart-container"></div>
      </div>
      <div class="center">
        <div class="chart-container"></div>
        <div class="map-container"></div>
        <div class="chart-container"></div>
      </div>
      <div class="right">
        <div class="chart-container"></div>
        <div class="chart-container"></div>
        <div class="chart-container"></div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'LargeDataScreen',
  components: {
  },
  data () {
    return {
      
    }
  }
}
</script>

<style lang="less" scoped>
  @header-height: 80px;

  .container {
    height: 100%;
    width: 100%;
    .header {
      height: @header-height;

      border: 1px solid #ddd;
    }
    .main {
      height: calc(100% - @header-height);
      display: flex;

      .left, .right {
        width: 500px;
        padding: 8px 8px 8px 16px;
        display: flex;
        flex-direction: column;
        .chart-container {
          margin: 8px 0;
          flex: auto;
          border: 1px solid #ddd;
        }
        // border: 1px solid #ddd;
      }
      .right {
        padding: 8px 16px 8px 8px;
      }
      .center {
        display: flex;
        flex-direction: column;
        flex: 1;
        padding: 16px 8px;
        .chart-container {
          height: 110px;
          border: 1px solid #ddd;
          &:last-child {
            height: 200px;
          }
        }
        .map-container {
          margin: 16px 0;
          flex: 1;
          border: 1px solid #ddd;
        }
      }
    }
  }

</style>
