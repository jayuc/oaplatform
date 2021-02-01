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
                    <el-form-item style="margin-left: 20px;">
                        <el-switch
                                v-model="containMyApprove"
                                active-text="包括我的审批">
                        </el-switch>
                    </el-form-item>
                    <el-form-item style="margin-left: 20px;">
                        <el-button type="primary" @click="submit" :disabled="searchBtnStatus">查 询</el-button>
                    </el-form-item>
                    <el-form-item style="margin-left: 20px;">
                        <el-button type="success" @click="openAddLeave" :disabled="searchBtnStatus">出差交通工具调整申请</el-button>
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
                          minWidth="100">
                    </el-table-column>
                    <el-table-column
                          prop="applyOrgId"
                          label="申请人单位"
                          align="center"
                          :formatter="formatOrg"
                          minWidth="210">
                    </el-table-column>
                    <el-table-column
                            prop="travelTool"
                            align="center"
                            minWidth="140"
                            :formatter="formatTravelTool"
                            label="规定交通工具">
                    </el-table-column>
                    <el-table-column
                            prop="holidayType"
                            align="center"
                            minWidth="140"
                            :formatter="formatTravelTool"
                            label="调整交通工具">
                    </el-table-column>
                    <el-table-column
                          prop="content"
                          align="center"
                          minWidth="220"
                          label="原因说明">
                    </el-table-column>
                    <el-table-column
                            prop="stopFlag"
                            align="center"
                            minWidth="80"
                            :formatter="formatStopFlag"
                            label="是否完成">
                    </el-table-column>
                    <el-table-column
                          prop="operation"
                          align="center"
                          minWidth="160"
                          label="操作">
                      <template slot-scope="scope">
                          <el-button v-if="ifShowDetailButton(scope.row)" @click="showBill(scope.row)"
                                     plain
                                     type="success"
                                     size="small">查看</el-button>
                          <el-button v-if="ifShowDetailButton(scope.row)" @click="showApproveList(scope.row)"
                                     plain
                                     type="info"
                                     size="small">审批记录</el-button>
                          <el-button v-if="ifShowApproveButton(scope.row)" @click="approveBill(scope.row)"
                                     plain
                                     type="warning"
                                     size="small">审批</el-button>
                          <el-button v-if="ifShowHandleRejectButton(scope.row)" @click="handleRejectBill(scope.row)"
                                     plain
                                     type="primary"
                                     size="small">驳回处置</el-button>
                          <el-button @click="deleteOne(scope.row)"
                                     v-if="ifShowDeleteButton(scope.row)"
                                     icon="el-icon-delete"
                                     title="删除"
                                     type="danger" size="small"></el-button>
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

        <add-business-trip ref="addLeave" @complete="submit" />

        <approve-business-trip ref="approveLeave" @complete="submit" />

        <reject-dialog ref="rejectDialog" @complete="submit" />

        <details-business-trip ref="detailsLeave" />

        <PrintTravelToolSetMain ref="tbRef" />

    </el-container>
</template>

<script>
    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';
    import AddBusinessTrip from './addTravelToolSet.vue';
    import ApproveBusinessTrip from './approveTravelToolSet.vue';
    import DetailsBusinessTrip from './detailsTravelToolSet.vue';
    import RejectDialog from './handleRejectTravelToolSet.vue';
    import PrintTravelToolSetMain from './printShow/printTravelToolSetMain.vue';
    import OrgUtil from '@/utils/OrgUtil';
    import CodeUtil from '@/utils/CodeUtil';
    import user from '@/user';
    import PermissionUtil from '@/utils/PermissionUtil';

    export default {
        name: 'process-travel-tool',
        components: {
            AddBusinessTrip,
            ApproveBusinessTrip,
            DetailsBusinessTrip,
            RejectDialog,
            PrintTravelToolSetMain
        },
        data(){
            return {
                tableContainerStyle: '',
                tableHeight: 500,
                formData: {
                    pageNumber: 1,
                    pageSize: 15,
                    stopFlag: 0,
                    billType: 6
                },
                searchBtnStatus: false,
                containMyApprove: false,
                total: 0,
                tableData: []
            }
        },
        methods: {
            submit(){
                if(this.containMyApprove){
                    this.formData.historyApproveList = '1';
                }else {
                    this.formData.historyApproveList = null;
                }
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
                }, (error) => {
                    console.error(error);
                    TipUtil.error('请求出错，请检查您的网络是否正常');
                });
            },
            // 删除一条记录
            deleteOne(row){
                this.$confirm('确定删除?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    RestUtil.post('oa/bill/deleteById', {id: row.billId}, {enableLoading: true}).then((result) => {
                        if(result == 1){
                            TipUtil.success('删除成功!');
                            this.submit();
                        }else {
                            TipUtil.error('删除失败!');
                        }
                    }, () => {
                        TipUtil.error('删除失败!');
                    });
                }).catch(() => {
                    TipUtil.info('已取消删除');
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
            formatTravelTool(row, column, cellValue){
                return CodeUtil.getName(2, cellValue);
            },
            // 是否显示审批按钮
            ifShowApproveButton(row){
                if(row['stopFlag'] != 1){
                    let nextApproveList = row['nextApproveList'];
                    let userId = user.get('userId');
                    if(typeof nextApproveList === 'string'){
                        return nextApproveList.indexOf(',' + userId + ',') > -1;
                    }
                }
                return false;
            },
            // 是否显示查看
            ifShowDetailButton(row){
                if(PermissionUtil.hasAuth('010601')){
                    return true;
                }
                return row.applyId == user.get('userId');
            },
            ifShowDeleteButton(row){
                if(PermissionUtil.hasAuth('010602')){
                    return true;
                }
                return row.applyId == user.get('userId');
            },
            // 是否显示驳回处置按钮
            ifShowHandleRejectButton(row){
                if(row.passFlag == 2){
                    return true;
                }
                return false;
            },
            handleRejectBill(row){
                this.$refs.rejectDialog.open(row, 'oa/bill/deliver');
            },
            openAddLeave(){
                this.$refs.addLeave.open({billType: this.formData.billType}, 'oa/bill/deliver', 'add');
            },
            showBill(row){
                this.$refs.detailsLeave.open(row);
            },
            showApproveList(row){
                console.log(row);
                this.$refs.tbRef.open(row);
            },
            approveBill(row){
                this.$refs.approveLeave.open(row, 'oa/bill/deliver');
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
