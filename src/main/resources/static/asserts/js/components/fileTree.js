/*
 * @Description: 文件树组件
 * @Author: supercheney
 * @Date: 2020-08-22 15:06:45
 * @LastEditors: supercheney
 * @LastEditTime: 2020-08-28 23:29:50
 * @FilePath: \collaboration1\src\main\resources\static\asserts\js\components\fileTree.js
 */

Vue.component('file-tree', {
  props: {
    projectId: {
      type: [String, Number],
      required: true
    }
  },
  template: 
    `<div>
      <el-button type="primary" size="mini" @click="addFileFolder">添加文件夹</el-button>
      <el-tree
        class="file-tree"
        :data="treeData"
        node-key="id"
        default-expand-all
        :props="defaultProps"
        :expand-on-click-node="false">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span>{{ node.label }}</span>
          <span>
            <el-button
              v-if="node.level === 1"
              type="text"
              size="small"
              icon="el-icon-upload"
              @click="() => append(data)">
            </el-button>
            <el-button
              v-if="data.deltag !== '0'"
              type="text"
              size="small"
              icon="el-icon-delete-solid"
              @click="() => remove(node, data)">
            </el-button>
          </span>
        </span>
      </el-tree>

      <el-dialog
					title="添加文件夹"
					:visible.sync="addFolderVisible"
          width="600px"
          append-to-body
        >

        <el-form ref="form" label-width="120px" size="small" :model="addFolderModel" :rules="addFolderRules">
          <el-form-item label="文件夹名称：" prop="name">
            <el-input v-model.trim="addFolderModel.name" placeholder="请输入文件夹名称" clearable></el-input>
          </el-form-item>
        </el-form>
        
        <span slot="footer" class="txtCenter">
          <el-button size="small" type="primary" @click="addFolderSubmit">确定</el-button>
          <el-button size="small" type="primary" @click="addFolderVisible = false">取消</el-button>
        </span>
      </el-dialog>
      
    </div>`,
  data () {
    const data = [{
      id: 1,
      label: '一级 1',
      children: [{
        id: 4,
        label: '二级 1-1',
        children: [{
          id: 9,
          label: '三级 1-1-1'
        }, {
          id: 10,
          label: '三级 1-1-2'
        }]
      }]
    }, {
      id: 2,
      label: '一级 2',
      children: [{
        id: 5,
        label: '二级 2-1'
      }, {
        id: 6,
        label: '二级 2-2'
      }]
    }, {
      id: 3,
      label: '一级 3',
      children: [{
        id: 7,
        label: '二级 3-1'
      }, {
        id: 8,
        label: '二级 3-2'
      }]
    }];
    return {
      data: JSON.parse(JSON.stringify(data)),
      treeData: [],
      defaultProps: {
        label: 'fileTreeName'
      },

      addFolderVisible: false,
      addFolderModel: {
        name: ''
      },
      addFolderRules: {
        name: [{required: true, message: '文件夹名不能为空', trigger: 'blur'}]
      }
    }
  },
  mounted () {
    this.getFileTreeData()
  },
  watch: {
    projectId () {
      this.getFileTreeData()
    }
  },
  methods: {
    getFileTreeData,
    addFileFolder,
    addFolderSubmit
  }
})

function getFileTreeData () {
  _this = this
  let ajaxData = {
    id: this.projectId
  }
  $.ajax({
    data: ajaxData,
    type: 'GET',
    // url: '/crud/fileTree/findFileTreeByProjectSummaryId',
    url: '/crud/fileTree/findFileTreeByProjectSummaryId',
    success: function (result) {
      if (result.code == 200) {
        _this.treeData = result.data
        _this.treeData.map(item => {
          if (!item.children || item.children.length === 0) {
            item.children = []
            item.isLeaf = true
          }
        })
       /*  _this.treeData[0].children.push({
          id: 100,
          fileTreeName: '测试'
        }) */
      } else {
        console.log('treeData 返回值失败')
      }
    },
  })
}

function addFileFolder () {
  this.addFolderVisible = true
}

function addFolderSubmit () {
  _this = this

  this.$refs.form.validate((valid) => {
    if (valid) {
      let ajaxData = {
        id: this.projectId,
        folderName: this.addFolderModel.name
      }
      $.ajax({
        data: ajaxData,
        type: 'GET',
        url: '/crud/fileTree/addFileTreeOfProjectSummary',
        success: function (result) {
          if (result.code == 200) {
            _this.$message.success('创建文件夹成功')
            _this.addFolderVisible = false
            _this.getFileTreeData()
          } else {
            console.log('treeData 返回值失败')
          }
        }
      })
    }
  })
}