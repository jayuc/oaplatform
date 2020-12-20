<template>
    <el-container style="height: 100%; border: 1px solid #eee">

        <el-header height="104px">
            <div class="form-search-top-div"></div>
            <div class="form-search-container">
                <span class="form-search-title">查询条件</span>
                <el-form :inline="true" :model="formData" class="demo-form-inline">
                    <el-form-item label="是否完成：">
                        <el-radio v-model="formData.stopFlag" :label="0">全部</el-radio>
                        <el-radio v-model="formData.stopFlag" :label="1">已完成</el-radio>
                        <el-radio v-model="formData.stopFlag" :label="2">未完成</el-radio>
                    </el-form-item>
                    <el-form-item style="margin-left: 10px;">
                        <el-button type="primary" @click="submit" :disabled="searchBtnStatus">查 询</el-button>
                    </el-form-item>
                    <el-form-item style="margin-left: 10px;">
                        <el-button type="success" @click="openAddLeave" :disabled="searchBtnStatus">新 增</el-button>
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
                          prop="applyName"
                          label="申请人"
                          align="center"
                          width="120">
                    </el-table-column>
                    <el-table-column
                          prop="applyOrgId"
                          label="申请人单位"
                          align="center"
                          :formatter="formatOrg"
                          width="280">
                    </el-table-column>
                    <el-table-column
                          prop="workAge"
                          align="center"
                          width="120"
                          label="工龄（年）">
                    </el-table-column>
                    <el-table-column
                          prop="holidayType"
                          align="center"
                          width="140"
                          :formatter="handleHolidayType"
                          label="休假标准">
                    </el-table-column>
                    <el-table-column
                          prop="startTime"
                          align="center"
                          width="180"
                          label="开始日期">
                    </el-table-column>
                    <el-table-column
                          prop="endTime"
                          align="center"
                          width="180"
                          label="结束日期">
                    </el-table-column>
                    <el-table-column
                          prop="days"
                          align="center"
                          width="100"
                          label="天数">
                    </el-table-column>
                    <el-table-column
                            prop="stopFlag"
                            align="center"
                            width="120"
                            :formatter="formatStopFlag"
                            label="是否完成">
                    </el-table-column>
                    <el-table-column
                          prop="operation"
                          align="center"
                          label="操作">
                      <template slot-scope="scope">
                          <el-button v-if="ifShowDetailButton(scope.row)" @click="showBill(scope.row)" type="text" size="small">查看</el-button>
                          <el-button v-if="ifShowApproveButton(scope.row)" @click="approveBill(scope.row)" type="text" size="small">审批</el-button>
                      </template>
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

        <add-leave ref="addLeave" @complete="submit" />

        <approve-leave ref="approveLeave" />

    </el-container>
</template>

<script>
    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';
    import AddLeave from './addLeave.vue';
    import ApproveLeave from './approveLeave.vue';
    import CodeUtil from '@/utils/CodeUtil';
    import OrgUtil from '@/utils/OrgUtil';
    import user from '@/user';

    export default {
        name: 'process-leave',
        components: {
            AddLeave,
            ApproveLeave
        },
        data(){
            return {
                tableContainerStyle: '',
                tableHeight: 500,
                formData: {
                    pageNumber: 1,
                    pageSize: 15,
                    stopFlag: 2
                },
                searchBtnStatus: false,
                total: 0,
                tableData: []
            }
        },
        methods: {
            submit(){
                RestUtil.get('oa/bill/list', this.formData, {
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
            formatOrg(row, column, cellValue){
                if(cellValue){
                    let org = OrgUtil.getOrgById(cellValue);
                    if(org && org.attribute){
                        return org.attribute.shortOrgName;
                    }
                }
                return '';
            },
            formatStopFlag(row, column, cellValue){
                if(cellValue){
                    if(cellValue == 1){
                        return '已完成';
                    }
                    if(cellValue == 2){
                        return '未完成';
                    }
                    return '';
                }
                return '';
            },
            // 是否显示审批按钮
            ifShowApproveButton(row){
                let nextApproveList = row['nextApproveList'];
                let userId = user.get('userId');
                if(typeof nextApproveList === 'string'){
                    return nextApproveList.indexOf(',' + userId + ',') > -1;
                }
                return false;
            },
            // 是否显示查看
            ifShowDetailButton(row){
                return row.applyId == user.get('userId');
            },
            openAddLeave(){
                this.$refs.addLeave.open({}, 'oa/bill/deliver', 'add');
            },
            showBill(row){
                console.log(row);
            },
            approveBill(row){
                this.$refs.approveLeave.open(row, 'oa/bill/approve');
            },
            handleHolidayType(row, column, cellValue){
                return CodeUtil.getName(3, cellValue);
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

<style scoped>

</style>
