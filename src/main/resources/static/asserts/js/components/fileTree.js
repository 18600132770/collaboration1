/*
 * @Description: 文件树组件
 * @Author: supercheney
 * @Date: 2020-08-22 15:06:45
 * @LastEditors: supercheney
 * @LastEditTime: 2020-09-22 21:59:55
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
      <div style="text-align: right;">
        <el-button type="primary" size="mini" @click="addFileFolder">添加文件夹</el-button>
      </div>
      <el-tree
        class="file-tree"
        :data="treeData"
        node-key="id"
        default-expand-all
        :props="defaultProps"
        :expand-on-click-node="false">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span>
          <i v-if="node.level === 1" class="el-icon-folder"></i> 
          <i v-if="node.level > 1"class="el-icon-document"></i>
          {{ node.label }}</span>
          <span>
            <el-button
              v-if="node.level === 1"
              type="text"
              icon="el-icon-upload"
              @click="() => append(data)">
            </el-button>
            <el-button
              v-if="node.level === 1"
              type="text"
              icon="el-icon-edit"
              @click="() => append(data)">
            </el-button>
            <el-button
              v-if="data.deltag !== '0'"
              type="text"
              icon="el-icon-delete-solid"
              @click="() => removeNode(node, data)">
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
          <el-button size="small" @click="addFolderVisible = false">取消</el-button>
        </span>
      </el-dialog>

      <el-dialog
					title="上传文件"
					:visible.sync="uploadVisible"
          width="600px"
          append-to-body
        >

        <el-form ref="form" label-width="120px" size="small">
          <el-form-item label="选择文件：" prop="name">
            <el-upload
              ref="uploadfile"
              class="upload-demo"
              drag
              action
              :http-request="uploadFile"
              :auto-upload="false"
              multiple>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <!--<div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div> -->
            </el-upload>
          </el-form-item>
        </el-form>
        
        <span slot="footer" class="txtCenter">
          <el-button size="small" type="primary" @click="uploadSubmit">确定</el-button>
          <el-button size="small" @click="uploadVisible = false">取消</el-button>
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
        label: 'name',
        children: 'profileList'
      },

      addFolderVisible: false,
      addFolderModel: {
        name: ''
      },
      addFolderRules: {
        name: [{required: true, message: '文件夹名不能为空', trigger: 'blur'}]
      },

      uploadVisible: false,
      currentFolderId: '' // 上传文件时选中的文件夹的id
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
    addFolderSubmit,

    // 删除
    removeNode,
    // 上传文件
    uploadFile,
    uploadSubmit,
    append
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

function removeNode (node, data) {
  let ajaxData = {
    id: data.id,
  }
  if (data.url) {
    ajaxData.type = 'file'
  } else {
    ajaxData.type = 'folder'
  }
  $.ajax({
    data: ajaxData,
    type: 'GET',
    url: '/crud/projectSummary/deleteFileTreeOfProjectSummary',
    success: function (result) {
      if (result.code == 200) {
        _this.$message.success('删除文件夹成功')
        _this.addFolderVisible = false
        _this.getFileTreeData()
      } else {
        console.log('treeData 返回值失败')
      }
    }
  })
}

function append (data) {
  this.uploadVisible = true
  this.currentFolderId = data.id
}

function uploadFile (params) {
  let file = params.file
  let formData = new FormData()

  formData.append('file', file)
  formData.append('id', this.currentFolderId)
  $.ajax({
    data: formData,
    type: 'POST',
    url: '/crud/projectSummaryFileTree/uploadFile',
    contentType: false, //'multipart/form-data' ,
    processData: false,
    success: function (result) {
      if (result.code == 200) {
        _this.$message.success('上传文件成功')
        _this.uploadVisible = false
        _this.getFileTreeData()
        
        _this.$refs.uploadfile.clearFiles()
      } else {
        console.log('treeData 返回值失败')
      }
    }
  })
}

function uploadSubmit () {
  this.$refs.uploadfile.submit()
}
