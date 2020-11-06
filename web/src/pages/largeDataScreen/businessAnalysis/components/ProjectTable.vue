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
    title: '部门序号',
    dataIndex: 'num',
    align: 'center'
  },
  {
    title: '区域',
    dataIndex: 'regional',
    width: 80,
    align: 'center'
  },
  {
    title: '经营负责人',
    dataIndex: 'head',
    align: 'center'
  },
  {
    title: '项目名称',
    dataIndex: 'name',
    align: 'center'
  },
  {
    title: '领域',
    dataIndex: 'domain',
    align: 'center'
  },
  {
    title: '阶段',
    dataIndex: 'stage',
    align: 'center'
  },
  {
    title: '预计合同额',
    dataIndex: 'contractAmount',
    align: 'center'
  },
  {
    title: '预计签订时间',
    dataIndex: 'date',
    align: 'center',
    width: 100
  },
  {
    title: '业主',
    dataIndex: 'master',
    align: 'center'
  },
  {
    title: '概况',
    dataIndex: 'summary',
    ellipsis: true
  },
]

const data = []
for (let i = 0; i < 50; i++) {
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
}

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
      font-size: 12px;
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