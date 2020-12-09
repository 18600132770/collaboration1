<template>
<div style="height: 100%; overflow: hidden;" ref="customTable">
  <a-table
    class="custom-table"
    :columns="columns"
    :data-source="data"
    :pagination="false"
    size="small"
    bordered
    :scroll="{ y: contentheight, x: false }"
  />
</div>
</template>

<script>
const columns = [
  {
    title: '类目',
    dataIndex: 'class',
    align: 'center'
  },
  {
    title: '金额',
    dataIndex: 'spending',
    // width: 80,
    align: 'center'
  },
  {
    title: '预算金额',
    dataIndex: 'yusuan',
    align: 'center'
  },
  {
    title: '占用额度',
    dataIndex: 'zhanyong',
    align: 'center'
  },
  {
    title: '实际占比',
    dataIndex: 'percentage',
    align: 'center'
  },
  {
    title: '预算占比',
    dataIndex: 'percentage1',
    align: 'center'
  },
]

const data = [
  {
    class: '人工',
    spending: '42亿',
    percentage: '27.6%',
    yusuan: '44亿',
    zhanyong: '95.2%',
    percentage1: '26.2%'
  },
  {
    class: '材料',
    spending: '51亿',
    percentage: '33.5%',
    yusuan: '61亿',
    zhanyong: '83.6%',
    percentage1: '33.4%'
  },
  {
    class: '机械',
    spending: '50亿',
    percentage: '32.8%',
    yusuan: '54亿',
    zhanyong: '92.6%',
    percentage1: '32.1%'
  },
  {
    class: '管理',
    spending: '3亿',
    percentage: '2%',
    yusuan: '2亿',
    zhanyong: '136.3%',
    percentage1: '1.2%'
  },
  {
    class: '其他成本',
    spending: '6.3亿',
    percentage: '4.1%',
    yusuan: '7亿',
    zhanyong: '90%',
    percentage1: '4.2%'
  },
  {
    class: '总计',
    spending: '152.3',
    percentage: '100%',
    yusuan: '168亿',
    zhanyong: '-',
    percentage1: '100%'
  },
]
/* for (let i = 0; i < 50; i++) {
  data.push({
    key: i,
    num: i + 1,
    name: `项目 ${i + 1}`,
    regional: '北京',
    head: '李四',
    domain: '交通',
    stage: '施工配合',
    contractAmount: '540',
    date: '2020-11-06',
    master: '张三',
    summary: `London, Park Lane no. ${i}`,
  })
} */

export default {
  data() {
    return {
      data,
      columns,
      contentheight: 0
    }
  },
  mounted () {
    this.contentheight = window.innerHeight - 485
    const _this = this
    window.addEventListener('resize', function () {
      _this.contentheight = window.innerHeight - 485
    })
  }
}
</script>

<style lang="less" scoped>
  .custom-table {
    height: 100%;
    width: calc(100% + 30px);
    // /deep/.ant-table td { white-space: nowrap; }
    /deep/.ant-table-fixed-header > .ant-table-content > .ant-table-scroll > .ant-table-body {
      background: transparent;
    }
    /deep/ .ant-table-tbody > tr > td {
      border-color: #23242A;
      color: #AEAFB3;
      font-size: 12px;
      padding: 8px 2px;
    }
    /deep/.ant-table-thead > tr.ant-table-row-hover:not(.ant-table-expanded-row):not(.ant-table-row-selected) > td, 
    /deep/.ant-table-tbody > tr.ant-table-row-hover:not(.ant-table-expanded-row):not(.ant-table-row-selected) > td, 
    /deep/.ant-table-thead > tr:hover:not(.ant-table-expanded-row):not(.ant-table-row-selected) > td, 
    /deep/.ant-table-tbody > tr:hover:not(.ant-table-expanded-row):not(.ant-table-row-selected) > td {
      background: transparent;
    }
    /deep/ .ant-table-small {
      border: none;
    }
    /deep/ .ant-table-thead > tr > th {
      border-color:#23242A;
      color: #AEAFB3;
      font-size: 13px;
      font-weight: 600;
    }
    /deep/ .ant-table-bordered.ant-table-fixed-header .ant-table-scroll .ant-table-header::-webkit-scrollbar {
      border: none;
    }
    /deep/ .ant-table-tbody {
      tr {
        &:nth-child(even) {
          background-color: #1E2026;
        }
      }
    }
  }
</style>