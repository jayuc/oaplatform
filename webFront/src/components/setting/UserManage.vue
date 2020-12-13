<template>
  <div style="height: 100%;" ref="pageTableContainer">
      <div style="height:20px;"></div>
      <div class="form-search-container">
          <span class="form-search-title">查询条件</span>
          <el-form :inline="true" :model="formData" class="demo-form-inline">
              <el-form-item label="用户名：">
                  <el-input v-model="formData.userName" placeholder="请输入用户名"></el-input>
              </el-form-item>
              <el-form-item>
                  <el-button type="primary" @click="submit" :disabled="searchBtnStatus">查 询</el-button>
              </el-form-item>
          </el-form>
      </div>
      <div class="form-table-container" :style="tableContainerStyle">
          <el-table
                  ref="elTable"
                  :data="tableData"
                  border
                  :height="tableHeight"
                  header-row-class-name="form-table-header"
                  header-cell-class-name="form-table-header-cell"
                  style="width: 100%">
              <el-table-column
                      prop="name"
                      label="姓名"
                      align="center"
                      width="180">
              </el-table-column>
              <el-table-column
                      prop="age"
                      label="年龄"
                      align="center"
                      width="180">
              </el-table-column>
              <el-table-column
                      prop="mobileTel"
                      align="center"
                      width="180"
                      label="手机号码">
              </el-table-column>
              <el-table-column
                      prop="operation"
                      align="center"
                      label="操作">
              </el-table-column>
          </el-table>
          <div style="height: 10px;"></div>
          <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="formData.pageNumber"
                  :page-sizes="[15, 30, 45, 60]"
                  :page-size="formData.pageSize"
                  layout="prev, pager, next, jumper, total, sizes"
                  :total="total">
          </el-pagination>
      </div>
  </div>
</template>

<script>
    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';

    export default {
        name: 'user-setting',
        data(){
            return {
                tableContainerStyle: '',
                tableHeight: 500,
                formData: {
                  userName: '',
                  pageNumber: 1,
                  pageSize: 15,
                },
                searchBtnStatus: false,
                total: 0,
                tableData: []
            }
        },
        methods: {
            submit(){
                console.log(this.formData);
                RestUtil.get('user/list', this.formData, {
                    enableLoading: true,       // 启动请求期间的正在加载
                    loadingStartFun: () => {   // 请求开始前执行
                        this.searchBtnStatus = true;
                    },
                    loadingEndFun: () => {     // 请求开始后执行
                        this.searchBtnStatus = false;
                    }
                }).then((result) => {
                    let data = result.result;
                    let error = result.error;
                    if(error){
                      TipUtil.error(error);
                      return;
                    }
                    if(data){
                      this.tableData = data.rows;
                      this.total = data.total;
                    }
                }, () => {

                });
            },
            handleSizeChange(currentPageSize) {
                this.formData.pageSize = currentPageSize;
                this.submit();
            },
            handleCurrentChange(currentPageNumber) {
                this.formData.pageNumber = currentPageNumber;
                this.submit();
            },
            afterCreated(){
                let pageHeight = Config.get('mainBodyHeight');
                let tableContainerHeight = pageHeight - 144;
                this.tableContainerStyle = 'height:' + tableContainerHeight + 'px';
                this.tableHeight = tableContainerHeight - 32;
            }
        },
        created(){
            this.afterCreated();
        },
        mounted(){
            this.submit();
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
