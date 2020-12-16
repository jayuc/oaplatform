<template>

    <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside width="200px">

        </el-aside>

        <el-container>

            <el-header height="104px">
                <div class="form-search-top-div"></div>
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
            </el-header>

            <el-main>
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
                              prop="userCode"
                              label="编号"
                              align="center"
                              width="200">
                        </el-table-column>
                        <el-table-column
                              prop="userName"
                              label="姓名"
                              align="center"
                              width="160">
                        </el-table-column>
                        <el-table-column
                              prop="sex"
                              label="性别"
                              align="center"
                              :formatter="formatSex"
                              width="100">
                        </el-table-column>
                        <el-table-column
                              prop="orgId"
                              label="所属机构"
                              align="center"
                              :formatter="formatOrg"
                              width="240">
                        </el-table-column>
                        <el-table-column
                              prop="position"
                              label="岗位"
                              align="center"
                              width="280">
                        </el-table-column>
                        <el-table-column
                              prop="loginName"
                              label="登录名"
                              align="center"
                              width="140">
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
            </el-main>

        </el-container>

    </el-container>

</template>

<script>
    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';
    import OrgUtil from '@/utils/OrgUtil';
    import CodeUtil from '@/utils/CodeUtil';

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
//                console.log(this.formData);
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
            formatSex(row, column, cellValue){
                return CodeUtil.getName(4, cellValue);
            },
            formatOrg(row, column, cellValue){
                let org = OrgUtil.getOrgById(cellValue);
                if(org){
                    return org.attribute.shortOrgName;
                }
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
                let tableContainerHeight = pageHeight - 146;
                this.tableContainerStyle = 'height:' + tableContainerHeight + 'px';
                this.tableHeight = tableContainerHeight - 34;
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
