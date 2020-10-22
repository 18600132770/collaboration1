<template>
  <a-card>
    <div>
      <div class="operator">
        <AddProject></AddProject>
        <!-- <a-button @click="addNew" type="primary" icon="plus">{{$t('add')}}</a-button> -->
        <a-input-search placeholder="input search text" style="width: 250px" @search="onSearch" />
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
import AddProject from './components/AddProject'

const dataSource = []

for (let i = 0; i < 100; i++) {
  dataSource.push({
    key: i,
    no: 'NO ' + i,
    projectName: '项目一',
    designPhase: '投标阶段',
    principal: '张三',
    chiefEngineer: '李四',
    startTime: '2018-07-26',
    endTime: '2021-07-26',
    remainingDays: (Math.random() * 100 + 100).toFixed(0),
    progress: (Math.random() * 100).toFixed(0),
    emergencyLevel: '紧急'
  })
}

export default {
  name: 'QueryList',
  components: {StandardTable, AddProject},
  i18n: require('./i18n'),
  data () {
    return {
      columns: [
        {
          title: this.$t('projectNo'),
          dataIndex: 'no'
        },
        {
          title: this.$t('projectName'),
          dataIndex: 'projectName'
        },
        {
          title: this.$t('designPhase'),
          dataIndex: 'designPhase',
        },
        {
          title: this.$t('principal'),
          dataIndex:'principal',
        },
        {
          title: this.$t('chiefEngineer'),
          dataIndex: 'chiefEngineer',
        },
        {
          title: this.$t('startTime'),
          dataIndex: 'startTime',
        },
        {
          title: this.$t('endTime'),
          dataIndex: 'endTime',
        },
        {
          title: this.$t('remainingDays'),
          dataIndex: 'remainingDays',
        },
        {
          title: this.$t('progress'),
          dataIndex: 'progress',
          scopedSlots: { customRender: 'progress' }
        },
        {
          title: this.$t('emergencyLevel'),
          dataIndex: 'emergencyLevel',
        },
        {
          title: this.$t('operate'),
          scopedSlots: { customRender: 'action' }
        }
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
        projectName: '项目一',
        designPhase: '投标阶段',
        principal: '张三',
        chiefEngineer: '李四',
        startTime: '2018-07-26',
        endTime: '2021-07-26',
        remainingDays: '200',
        progress: 50,
        emergencyLevel: '紧急'
      })
    },
    handleMenuClick (e) {
      if (e.key === 'delete') {
        this.remove()
      }
    }
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
