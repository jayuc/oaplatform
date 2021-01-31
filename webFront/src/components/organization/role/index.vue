<template>
    <el-container>

        <el-header height="104px">
            <div class="form-search-top-div"></div>
            <div class="form-search-container">
                <span class="form-search-title">查询条件</span>
                <el-form :inline="true" :model="formData" class="demo-form-inline">
                    <el-form-item>
                        <el-button type="primary" @click="submit()" :disabled="searchBtnStatus">查 询</el-button>
                    </el-form-item>
                    <yu-auth code="020301" style="margin-left: 10px;">
                        <el-form-item>
                            <el-button type="success" @click="add" >新 增</el-button>
                        </el-form-item>
                    </yu-auth>
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
                    <!--<el-table-column-->
                            <!--type="selection"-->
                            <!--align="center"-->
                            <!--width="36">-->
                    <!--</el-table-column>-->
                    <el-table-column
                            prop="roleName"
                            label="角色名称"
                            align="center"
                            minWidth="200">
                    </el-table-column>
                    <el-table-column
                            prop="mark"
                            label="备注"
                            align="center"
                            minWidth="200">
                    </el-table-column>
                    <el-table-column
                            prop="operation"
                            align="center"
                            minWidth="160"
                            fixed="right"
                            label="操作">
                        <template slot-scope="scope">
                            <yu-auth name="rowButton" code="020302">
                                <el-button @click="update(scope.row)"
                                           icon="el-icon-edit"
                                           title="编辑"
                                           type="warning" size="small"></el-button>
                            </yu-auth>
                            <yu-auth name="rowButton" code="020303">
                                <el-button @click="deleteOne(scope.row)"
                                           icon="el-icon-delete"
                                           title="删除"
                                           type="danger" size="small"></el-button>
                            </yu-auth>
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

        <add-dialog ref="addDialog" @complete="submit" />

    </el-container>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';
    import AddDialog from './add.vue';

    export default {
        name: 'role-manage',
        components: {
            AddDialog
        },
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
                tableData: []
            }
        },
        methods: {
            submit(){
                RestUtil.get('role/list', this.formData, {
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
            // 新增
            add(){
                this.$refs.addDialog.open(null, 'role/add', 'add');
            },
            update(row){
                this.$refs.addDialog.open(row, 'role/update', 'edit');
            },
            deleteOne(row){
                this.$confirm('确定删除 ' + row.roleName + ' ?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    RestUtil.post('role/delete', {roleId: row.roleId}, {enableLoading: true}).then((result) => {
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
