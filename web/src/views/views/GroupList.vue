<template>
  <el-main>
    <el-form
      ref="searchForm"
      :model="parms"
      label-width="80px"
      :inline="true"
      size="small"
    >
      <el-form-item label="群组名称">
        <el-input v-model="parms.id" />
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="searchBtn">搜索</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="addBtn">新增群组</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :height="tableHeight"
      :data="filPersons"
      border
      stripe
      style="width: 100%"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="答者">
              <span>{{ props.row.answerer }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="群组名称"
        prop="id"
      />
      <el-table-column
        label="群组介绍"
        prop="name"
      />
      <el-table-column
        label="标签"
        prop="desc"
      />
      <el-table-column align="center" width="180" label="操作">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-edit"
            type="primary"
            size="small"
            @click="editBtn(scope.row)"
          >编辑
          </el-button>
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="small"
            @click="deleteBtn(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      :current-page.sync="parms.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="parms.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="parms.total"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
    />

    <!-- 新增弹框 -->
    <sys-dialog
      :title="addDialog.title"
      :height="addDialog.height"
      :width="addDialog.width"
      :visible="addDialog.visible"
      @onClose="onClose"
      @onConfirm="onConfirm"
    >
      <template slot="content">
        <el-form
          ref="addForm"
          :model="addModel"
          label-width="80px"
          :inline="true"
          size="small"
        >
          <el-form-item prop="phone" label="群组名称">
            <el-input v-model="addModel.id" />
          </el-form-item>
          <el-form-item prop="email" label="群组介绍">
            <el-input v-model="addModel.name" />
          </el-form-item>
          <el-form-item prop="username" label="标签">
            <el-input v-model="addModel.desc" />
          </el-form-item>
          <el-form-item prop="password" label="答者">
            <el-input v-model="addModel.answerer" />
          </el-form-item>
        </el-form>
      </template>
    </sys-dialog>
  </el-main>
</template>

<style>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
import { nanoid } from 'nanoid'
import SysDialog from '@/components/system/SysDialog'
import { MessageBox, Message } from 'element-ui'

export default {
  components: {
    SysDialog
  },
  data() {
    return {
      tableHeight: 0,
      rules: {
        id: [
          {
            trigger: 'change',
            message: '请填写群组名称',
            required: true
          }
        ],
        name: [
          {
            trigger: 'change',
            message: '请填写群组介绍',
            required: true
          }
        ],
        desc: [
          {
            trigger: 'change',
            message: '请填写群组标签',
            required: true
          }
        ],
        answerer: [
          {
            trigger: 'change',
            message: '请填写答者',
            required: true
          }
        ]
      },
      tableGroup: [{
        unique: nanoid(),
        id: 'groupOne',
        name: '调查不同阶段学生对文具需求',
        desc: '学生',
        answerer: 'answerer1 answerer2 answerer3 answerer4 answerer5 answerer6 answerer7'
      }, {
        unique: nanoid(),
        id: 'groupTwo',
        name: '幼儿园时期的孩子年龄太小，作为老师需要了解家长平时的育儿方法才能更好的互补配合',
        desc: '幼儿父母',
        answerer: 'answerer1 answerer2 answerer3 answerer4 answerer5 answerer6 answerer7'
      }, {
        unique: nanoid(),
        id: 'groupThree',
        name: '熬夜是很多大学生习以为常的事情，为了更好的倡导大学生正确的生活习惯，对此开展了调查',
        desc: '大学生',
        answerer: 'answerer1 answerer2 answerer3 answerer4 answerer5 answerer6 answerer7'
      }, {
        unique: nanoid(),
        id: 'groupFour',
        name: '养老院的老人可能在衣食上有保障，但老年人精神情感上的需求也需要关注',
        desc: '老年人',
        answerer: 'answerer1 answerer2 answerer3 answerer4 answerer5 answerer6 answerer7'
      }],
      parms: {
        id: '',
        currentPage: 1,
        pageSize: 10,
        total: 4
      },
      // 新增表单绑定的数据域
      addModel: {
        unique: '',
        id: '',
        name: '',
        desc: '',
        answerer: '',
        editType: '' // 0：新增 1：编辑
      },
      addDialog: {
        title: '',
        height: 180,
        width: 650,
        visible: false
      }
    }
  },
  computed: {
    // eslint-disable-next-line vue/return-in-computed-property
    filPersons() {
      return this.tableGroup.filter((p) => {
        return p.id.indexOf(this.parms.id) !== -1
      })
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = window.innerHeight - 200
    })
  },
  methods: {
    searchBtn() {
      // this.getList()
    },
    currentChange(val) {
      console.log(val)
      this.parms.currentPage = val
      // 重新获取列表
      this.getList()
    },
    // 页容量改变时触发
    sizeChange(val) {
      console.log(val)
      this.parms.pageSize = val
      this.parms.currentPage = 1
      this.getList()
    },
    editBtn(row) {
      console.log(row)
      // 表单的清空
      this.resetForm('addForm', this.addModel)
      // 设置弹框属性
      this.addDialog.title = '编辑群组'
      this.addDialog.visible = true
      // 设置要编辑的数据回显
      // 把当前要编辑的数据设置到表单绑定的数据域
      this.objCoppy(row, this.addModel)
      console.log(this.addModel)
      // 设置为编辑
      this.addModel.editType = '1'
      console.log('编辑成功')
    },
    resetForm(formName, obj) {
      if (this.$refs[formName]) {
        this.$refs[formName].resetFields()
      }
      // 清空表单数据域
      Object.keys(obj).forEach(key => {
        obj[key] = ''
      })
    },
    myconfirm(text) {
      return new Promise((resolve, reject) => {
        MessageBox.confirm(text, '系统提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          resolve(true)
        }).catch(() => {
          Message.warning('已取消')
          reject(false)
        })
      }).catch(() => {

      })
    },

    objCoppy(obj1, obj2) {
      Object.keys(obj2).forEach(key => {
        obj2[key] = obj1[key]
      })
    },
    addBtn() {
      // // 表单的清空
      this.resetForm('addForm', this.addModel)
      this.addDialog.title = '新增群组'
      this.addDialog.visible = true
      this.addModel.editType = '0'
      this.parms.total++
      console.log('添加成功')
    },
    async deleteBtn(row) {
      console.log(row)
      // 信息确认提示
      const confirm = await this.myconfirm('确定删除该数据吗?')
      console.log(confirm)
      if (confirm) {
        this.tableGroup = this.tableGroup.filter(group => group.id !== row.id)
      }
      this.parms.total--
      console.log('删除成功')
    },
    onConfirm() {
      if (this.addModel.editType === '0') {
        // 新增
        const obj = {
          unique: nanoid(),
          id: this.addModel.id,
          name: this.addModel.name,
          desc: this.addModel.desc,
          answerer: this.addModel.answerer
        }
        this.filPersons.push(obj)
      } else {
        this.filPersons.forEach((group) => {
          if (group.unique === this.addModel.unique) {
            group.id = this.addModel.id
            group.name = this.addModel.name
            group.desc = this.addModel.desc
            group.answerer = this.addModel.answerer
          }
        })
        console.log('成功')
      }
      this.addDialog.visible = false
    },
    onClose() {
      this.addDialog.visible = false
    }
  }
}
</script>
