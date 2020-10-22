<template>
  <div class="file-tree">
    <a-tree 
      :tree-data="treeData" 
      default-expand-all 
      show-icon
      :default-selected-keys="['0-0-0']"
    >
      <template slot="folder" slot-scope="{ expanded, children, isLeaf }">
        <a-icon v-if="!isLeaf" :type="expanded ? 'folder-open' : 'folder'" />
        <a-icon v-else type="file" />
      </template>
      <template slot="custom" slot-scope="item">
        <span>{{ item.title }}</span>
        <a-space>
          <a-button
            type="primary"
            size="small"
            @click="()=> append(item)"
          >新增</a-button>
          <a-button
            type="primary"
            size="small"
            @click="()=> edit(item)"
          >编辑</a-button>
          <a-button type="danger" size="small" @click="(e)=> remove(item)">删除</a-button>
        </a-space>
      </template>
    </a-tree>
  </div>
</template>
<script>
const treeData = [
  {
    title: 'parent 1',
    key: '0-0',
    scopedSlots: {title: 'custom', icon: 'folder'},
    children: [
      { title: 'leaf', key: '0-0-0', isLeaf: true, scopedSlots: {title: 'custom', icon: 'folder'}},
      { title: 'leaf', key: '0-0-1', isLeaf: true, scopedSlots: {title: 'custom', icon: 'folder'}},
    ],
  },
];

export default {
  i18n: require('../i18n'),
  data() {
    return {
      treeData,
      visible: false,
      top: 0,
      left: 0,
    };
  },
  methods: {
    onSelect(selectedKeys, info) {
      console.log('selected', selectedKeys, info);
    },
    onCheck(checkedKeys, info) {
      console.log('onCheck', checkedKeys, info);
    },
    append () {},
    edit () {},
    remove () {},
  },
};
</script>

<style lang="less" scoped>
  .file-tree {
    position: relative;
    /deep/.ant-tree li .ant-tree-node-content-wrapper {
      width: calc(100% - 24px);
      .ant-tree-title {
        width: 100%;
        // display: flex;
        // justify-content: space-between;
      }
    }
    /deep/ .ant-tree {
      .ant-space {
        float: right;
      }
      .ant-btn-sm {
        font-size: 12px;
      }
    }
  }
</style>
