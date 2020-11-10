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
   <div class="card-wrap">
      <div class="card-item" v-for="(item, index) in countData" :key="index">
        <a-statistic :title="item.title" :precision="0" :value="item.value" suffix="亿元" />
      </div>
    </div>
</div>
</template>

<script>
const columns = [
  {
    title: '部门',
    dataIndex: 'department',
    align: 'center'
  },
  {
    title: '合同总额',
    dataIndex: 'total',
    align: 'center'
  },
  {
    title: '累计收款',
    dataIndex: 'received',
    align: 'center'
  },
  {
    title: '应收款',
    dataIndex: 'receivables',
    align: 'center'
  },
  {
    title: '应收未收款',
    dataIndex: 'outstanding',
    align: 'center'
  },
  {
    title: '预计年底收款',
    dataIndex: 'expected',
    align: 'center'
  }
]

const data = []
for (let i = 0; i < 50; i++) {
  let num = (i+1) * (Math.random() * 100 + 100).toFixed(0)
  data.push({
    key: i,
    num: i + 1,
    total: `${num}`,
    department: '桥隧院',
    received: num - 50,
    receivables: num,
    outstanding: 50,
    expected: 50,
  })
}

export default {
  data() {
    return {
      data,
      columns,
      contentheight: 0,
      countData: [
        {
          title: '合同总额',
          value: '43123'
        },
        {
          title: '累计收款',
          value: '22123'
        },
        {
          title: '应收款',
          value: '2123'
        },
        {
          title: '应收未收款',
          value: '1123'
        },
        {
          title: '预计年底收款',
          value: '6523'
        },
      ]
    }
  },
  mounted () {
    this.contentheight = window.innerHeight - 555
    const _this = this
    window.addEventListener('resize', function () {
      _this.contentheight = window.innerHeight - 555
    })
  }
}
</script>

<style lang="less" scoped>
  .custom-table {
    // height: 100%;
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
  .card-wrap {
    display: flex;
    justify-content: space-around;
    padding-top: 10px;
    .card-item {
      width: 120px;
      color: #ffffff;
      background: #222631;
    }
    /deep/ .ant-statistic {
      padding: 4px 8px 0;
      .ant-statistic-title {
        color: #95989E;
      }
      .ant-statistic-content {
        color: #6FB7EF;
      }
      .ant-statistic-content-suffix {
        font-size: 12px;
      }
    }
  }
</style>