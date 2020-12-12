<template>
  <div>
      <div style="height:20px;"></div>
      <div class="form-search-container">
          <span class="form-search-title">查询条件</span>
          <el-form :inline="true" :model="formData" class="demo-form-inline">
              <el-form-item label="用户名">
                  <el-input v-model="formData.userName" placeholder="请输入用户名"></el-input>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="submit">查 询</el-button>
              </el-form-item>
          </el-form>
      </div>
      <div class="form-table-container">
          <el-table
                  :data="tableData"
                  border
                  header-row-class-name="form-table-header"
                  header-cell-class-name="form-table-header-cell"
                  style="width: 100%">
              <el-table-column
                      prop="date"
                      label="日期"
                      align="center"
                      width="180">
              </el-table-column>
              <el-table-column
                      prop="name"
                      label="姓名"
                      align="center"
                      width="180">
              </el-table-column>
              <el-table-column
                      prop="address"
                      align="center"
                      label="地址">
              </el-table-column>
          </el-table>
      </div>
  </div>
</template>

<script>

  export default {
      name: 'user-setting',
      data(){
          return {
              formData: {
                  userName: ''
              },
              tableData: [{
                  date: '2016-05-02',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1518 弄'
              }, {
                  date: '2016-05-04',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1517 弄'
              }, {
                  date: '2016-05-01',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1519 弄'
              }, {
                  date: '2016-05-03',
                  name: '王小虎',
                  address: '上海市普陀区金沙江路 1516 弄'
              }]
          }
      },
      methods: {
          submit(){
              console.log(this.formData);
          }
      }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
