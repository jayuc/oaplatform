<template>
    <el-container>

        <el-header height="104px">
            <div class="form-search-top-div"></div>
            <div class="form-search-container">
                <span class="form-search-title">查询条件</span>
                <el-form :inline="true" :model="formData" class="demo-form-inline">
                    <el-form-item label="操作时间：">
                        <el-date-picker
                                v-model="operaTime"
                                type="datetimerange"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item style="margin-left: 20px;">
                        <el-button type="primary" @click="submit()" :disabled="searchBtnStatus">查 询</el-button>
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
                            prop="billCode"
                            label="工单编号"
                            align="center"
                            width="150">
                    </el-table-column>
                    <el-table-column
                            prop="billTypeName"
                            label="工单类型"
                            align="center"
                            width="300">
                    </el-table-column>
                    <el-table-column
                            prop="billStepName"
                            label="操作步骤"
                            align="center"
                            width="300">
                    </el-table-column>
                    <el-table-column
                            prop="operaName"
                            label="操作人"
                            align="center"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="content"
                            label="审批意见"
                            align="center"
                            width="400">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="操作时间"
                            align="center"
                            >
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
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';

    export default {
        name: 'role-manage',
        data(){
            return {
                tableContainerStyle: '',
                orgTreeContainerStyle: '',
                tableHeight: 500,
                formData: {
                    pageNumber: 1,
                    pageSize: 15
                },
                searchBtnStatus: false,
                total: 0,
                tableData: [],
                operaTime: []
            }
        },
        methods: {
            submit(){
                if(this.operaTime.length > 1){
                    this.formData.beginTime = this.operaTime[0];
                    this.formData.endTime = this.operaTime[1];
                }
                RestUtil.get('oa/bill/opera/list', this.formData, {
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
            update(row){
                console.log(row);
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
                // 表格容器高度
                this.tableContainerStyle = 'height:' + tableContainerHeight + 'px';
                // 表格高度
                this.tableHeight = tableContainerHeight - 34;
                // 机构导航容器高度
                this.orgTreeContainerStyle = 'height:' + (pageHeight - 56) + 'px';
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
