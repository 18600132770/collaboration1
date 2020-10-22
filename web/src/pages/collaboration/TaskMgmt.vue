<template>
  <div class="collaboration">
    <a-layout id="components-layout-demo-custom-trigger">
      <a-card class="list-card" :bordered="false">
        <a-layout-sider :trigger="null" collapsible style="background: #fff; height: 100%;">
          <div class="title">{{$t('projectList')}}</div>
          <a-menu theme="light" mode="inline" :default-selected-keys="['1']">
            <a-menu-item key="1">
              <a-icon type="project" />
              <span>北京地铁</span>
            </a-menu-item>
            <a-menu-item key="2">
              <a-icon type="project" />
              <span>天津地铁</span>
            </a-menu-item>
            <a-menu-item key="3">
              <a-icon type="project" />
              <span>马东铁路</span>
            </a-menu-item>
          </a-menu>
        </a-layout-sider>
      </a-card>
      <a-layout>
        <a-layout-content>
          <a-card :bordered="false">
            <div>
              <a-descriptions :title="$t('projectInfo')" size="small" :column="{ xs: 2, sm: 2, md: 4}">
                <a-descriptions-item :label="$t('projectNo')">
                  20201010
                </a-descriptions-item>
                <a-descriptions-item :label="$t('projectName')">
                  北京地铁
                </a-descriptions-item>
                <a-descriptions-item :label="$t('projectStage')">
                  紧急
                </a-descriptions-item>
                <a-descriptions-item :label="$t('principal')">
                  张三
                </a-descriptions-item>
                <a-descriptions-item :label="$t('chiefEngineer')">
                  李四
                </a-descriptions-item>
                <a-descriptions-item :label="$t('startTime')">
                  2020-10-10
                </a-descriptions-item>
                <a-descriptions-item :label="$t('endTime')">
                  2022-10-10
                </a-descriptions-item>
              </a-descriptions>
            </div>
            <div>
              <div class="operator">
                <div class="title">{{$t('taskList')}}</div>
                <a-space>
                  <a-button @click="addNew" type="primary" icon="plus">新建任务</a-button>
                  <a-button icon="arrow-up">{{$t('upperOrderData')}}</a-button>
                  <a-button icon="arrow-down">{{$t('preface')}}</a-button>
                  <a-button icon="file">{{$t('proFiles')}}</a-button>
                </a-space>
              </div>
              <standard-table
                :columns="columns"
                :dataSource="dataSource"
                :selectedRows.sync="selectedRows"
                @clear="onClear"
                @change="onChange"
                @selectedRowChange="onSelectChange"
              >
                <div slot="description" slot-scope="{text}">
                  {{text}}
                </div>
                <div slot="action" slot-scope="{text, record}">
                  <a style="margin-right: 8px">
                    <a-icon type="plus"/>{{$t('approve')}}
                  </a>
                  <a @click="deleteRecord(record.key)">
                    <a-icon type="delete" />{{$t('delete')}}
                  </a>
                </div>
                <template slot="statusTitle">
                  <a-icon @click.native="onStatusTitleClick" type="info-circle" />
                </template>
              </standard-table>
            </div>
          </a-card>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<script>
import StandardTable from '@/components/table/StandardTable'

const dataSource = []

for (let i = 0; i < 100; i++) {
  dataSource.push({
    key: i,
    taskName: 'NO ' + i,
    taskLevel: '紧急',
    design: '张三',
    review: '李四',
    verify: '王五',
    approval: '马六',
    fileForReview: '文件',
    startTime: '2018-07-26',
    endTime: '2018-07-26'
  })
}

export default {
  name: 'QueryList',
  components: {StandardTable},
  i18n: require('./i18n'),
  data () {
    return {
      advanced: true,
      columns: [
        {
          title: this.$t('taskName'),
          dataIndex: 'taskName'
        },
        {
          title: this.$t('taskLevel'),
          dataIndex: 'taskLevel',
          scopedSlots: { customRender: 'description' }
        },
        {
          title: this.$t('design'),
          dataIndex: 'design',
        },
        {
          title: this.$t('review'),
          dataIndex: 'review',
          needTotal: true,
        },
        {
          title: this.$t('verify'),
          dataIndex: 'verify',
        },
        {
          title: this.$t('approval'),
          dataIndex: 'approval',
        },
        {
          title: this.$t('fileForReview'),
          dataIndex: 'fileForReview',
        },
        {
          title: this.$t('startTime'),
          dataIndex: 'startTime',
          sorter: true
        },
        {
          title: this.$t('endTime'),
          dataIndex: 'endTime',
          sorter: true
        },
        {
          title: '操作',
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
    deleteRecord(key) {
      this.dataSource = this.dataSource.filter(item => item.key !== key)
      this.selectedRows = this.selectedRows.filter(item => item.key !== key)
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
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
        taskName: 'NO ' + this.dataSource.length,
        taskLevel: '紧急',
        design: '张三',
        review: '李四',
        verify: '王五',
        approval: '马六',
        fileForReview: '文件',
        startTime: '2018-07-26',
        endTime: '2018-07-26'
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
  .collaboration {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    color: rgba(0, 0, 0, 0.65);
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    list-style: none;
    -webkit-font-feature-settings: 'tnum';
    font-feature-settings: 'tnum';
    position: relative;
    background: #fff;
    border-radius: 2px;
    -webkit-transition: all 0.3s;
    transition: all 0.3s;
    border: 1px solid #f0f0f0;
  }
  .collaboration .title {
    margin-bottom: 20px;
    color: rgba(0, 0, 0, 0.85);
    font-weight: bold;
    font-size: 16px;
    line-height: 1.5;
  }
  .operator {
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
    .title {
      margin-bottom: 0;
      line-height: 2;
    }
    .space {
      margin-left: 8px;
    }
  }
</style>
