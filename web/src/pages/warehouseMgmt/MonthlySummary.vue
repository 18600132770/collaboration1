<template>
  <a-card>
    <div>
      <div class="operator">
        <span>
          <a-date-picker @change="onDateChange" />
          <a-button @click="addNew" type="link">{{$t('export')}}</a-button>
        </span>
        <a-input-search :placeholder="$t('enterKeywords')" style="width: 250px" @search="onSearch" />
      </div>
      <standard-table
        :columns="columns"
        :dataSource="dataSource"
        :selectedRows.sync="selectedRows"
        @clear="onClear"
        @change="onChange"
        @selectedRowChange="onSelectChange"
      >
        <div slot="progress" slot-scope="{text}">
          <div style="width: 120px">
            <a-progress :percent="+text" size="small" />
          </div>
        </div>
        <div slot="action" slot-scope="{text, record}">
          <a style="margin-right: 8px">
            <a-icon type="edit"/>{{$t('edit')}}
          </a>
          <a @click="deleteRecord(record.key)">
            <a-icon type="delete" />{{$t('delete')}}
          </a>
        </div>
      </standard-table>
    </div>
  </a-card>
</template>

<script>
import StandardTable from '@/components/table/StandardTable'
// import AddProject from './components/AddProject'

const dataSource = []

for (let i = 0; i < 100; i++) {
  dataSource.push({
    key: i,
    no: 'NO ' + i,
    materialsName: '顶调电位计',
    materialsType: '5KΩ',
    materialsUnit: '个',
    materialsUnitPrice: '3.5',
    materialsSupplier: '',
    materialsInputNum: '29',
    materialsInputDate: '2018-01-19',
    materialsOutputNum: '',
    materialsOutputDate: '',
    materialsStorageLocation: '1号库房1-2',
    remark: '',
    createTime: '2018-15-24 14:57:40'
  })
}

export default {
  name: 'QueryList',
  components: {StandardTable, /* AddProject */},
  i18n: require('./i18n'),
  data () {
    return {
      columns: [
        {
          title: this.$t('materialsName'),
          dataIndex: 'materialsName'
        },
        {
          title: this.$t('materialsType'),
          dataIndex: 'materialsType'
        },
        {
          title: this.$t('materialsUnit'),
          dataIndex: 'materialsUnit',
        },
        {
          title: this.$t('materialsUnitPrice'),
          dataIndex:'materialsUnitPrice',
        },
        {
          title: this.$t('materialsSupplier'),
          dataIndex: 'materialsSupplier',
        },
        {
          title: this.$t('materialsInputNum'),
          dataIndex: 'materialsInputNum',
        },
        {
          title: this.$t('materialsInputDate'),
          dataIndex: 'materialsInputDate',
        },
        {
          title: this.$t('materialsOutputNum'),
          dataIndex: 'materialsOutputNum',
        },
        {
          title: this.$t('materialsOutputDate'),
          dataIndex: 'materialsOutputDate',
        },
        {
          title: this.$t('materialsStorageLocation'),
          dataIndex: 'materialsStorageLocation',
        },
        {
          title: this.$t('remark'),
          dataIndex: 'remark',
        },
        {
          title: this.$t('createTime'),
          dataIndex: 'createTime',
        }/* ,
        {
          title: this.$t('operate'),
          scopedSlots: { customRender: 'action' }
        } */
      ],
      dataSource: dataSource,
      selectedRows: []
    }
  },
  authorize: {
    deleteRecord: 'delete'
  },
  methods: {
    onSearch () {
      console.log('search')
    },
    deleteRecord(key) {
      this.dataSource = this.dataSource.filter(item => item.key !== key)
      this.selectedRows = this.selectedRows.filter(item => item.key !== key)
    },
    remove () {
      this.dataSource = this.dataSource.filter(item => this.selectedRows.findIndex(row => row.key === item.key) === -1)
      this.selectedRows = []
    },
    onClear() {
      this.$message.info('您清空了勾选的所有行')
    },
    onStatusTitleClick() {
      this.$message.info('你点击了状态栏表头')
    },
    onChange() {
      this.$message.info('表格状态改变了')
    },
    onSelectChange() {
      this.$message.info('选中行改变了')
    },
    addNew () {
      this.dataSource.unshift({
        key: this.dataSource.length,
        no: 'NO ' + this.dataSource.length,
        materialsName: '顶调电位计',
        materialsType: '5KΩ',
        materialsUnit: '个',
        materialsUnitPrice: '3.5',
        materialsSupplier: '',
        materialsInputNum: '29',
        materialsInputDate: '2018-01-19',
        materialsOutputNum: '',
        materialsOutputDate: '',
        materialsStorageLocation: '1号库房1-2',
        remark: '',
        createTime: '2018-15-24 14:57:40'
      })
    },
    handleMenuClick (e) {
      if (e.key === 'delete') {
        this.remove()
      }
    },
    onDateChange () {}
  }
}
</script>

<style lang="less" scoped>
  .search{
    margin-bottom: 54px;
  }
  .fold{
    width: calc(100% - 216px);
    display: inline-block
  }
  .operator{
    margin-bottom: 18px;
    display: flex;
    justify-content: space-between;
  }
  @media screen and (max-width: 900px) {
    .fold {
      width: 100%;
    }
  }
</style>
