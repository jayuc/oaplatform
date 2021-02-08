<template>

    <el-container style="height: 100%; border: 1px solid #eee">

        <el-header height="104px">
            <div class="form-search-top-div"></div>
            <div class="form-search-container">
                <span class="form-search-title">查询条件</span>
                <el-form :inline="true" :model="formData" class="demo-form-inline">
                    <yu-auth code="040201" style="margin-left: 10px;">
                        <el-form-item>
                            <el-button type="primary" @click="submit" :disabled="searchBtnStatus">查 询</el-button>
                        </el-form-item>
                    </yu-auth>
                    <yu-auth code="040202" style="margin-left: 10px;">
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
                    <el-table-column
                            prop="stepName"
                            label="步骤名称"
                            align="center"
                            minWidth="100">
                    </el-table-column>
                    <el-table-column
                            prop="stepType"
                            label="步骤类型，1：审批 2：条件 3：结束 4：备案"
                            align="center"
                            minWidth="100">
                    </el-table-column>
                    <el-table-column
                            prop="successTo"
                            label="条件成功去向"
                            align="center"
                            minWidth="100">
                    </el-table-column>
                    <el-table-column
                            prop="failTo"
                            label="条件失败去向"
                            align="center"
                            minWidth="100">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="创建时间"
                            align="center"
                            minWidth="100">
                    </el-table-column>
                    <el-table-column
                            prop="operation"
                            align="center"
                            fixed="right"
                            minWidth="180"
                            label="操作">
                        <template slot-scope="scope">
                            <el-button @click="details(scope.row)"
                                       icon="el-icon-document"
                                       title="查看详情"
                                       type="success" size="small"></el-button>
                            <yu-auth name="rowButton" code="040203">
                                <el-button @click="update(scope.row)"
                                           icon="el-icon-edit"
                                           title="编辑"
                                           type="warning" size="small"></el-button>
                            </yu-auth>
                            <yu-auth name="rowButton" code="040204">
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

        <detail-dialog ref="detailDialog" />

        <add-dialog ref="addDialog" @complete="submit" />

    </el-container>

</template>

<script>
    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';
    import OrgUtil from '@/utils/OrgUtil';
    import CodeUtil from '@/utils/CodeUtil';
    import DetailDialog from './details.vue';
    import AddDialog from './add.vue';

    export default {
        name: 'oaProcessStep-main',
        components: {
            DetailDialog,
            AddDialog
        },
        data(){
            return {
                tableContainerStyle: '',
                tableHeight: 500,
                formData: {
                    pageNumber: 1,
                    pageSize: 15,
                },
                searchBtnStatus: false,
                total: 0,
                tableData: [],
                operaTime: []
            }
        },
        methods: {
            // 查询
            submit(){
                if(this.operaTime.length > 1){
                    this.formData.beginTime = this.operaTime[0];
                    this.formData.endTime = this.operaTime[1];
                }
                RestUtil.get('oa:oa/process/step/select', this.formData, {
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
            // 查看详情
            details(row){
                this.$refs.detailDialog
                        .open(row);
            },
            // 新增
            add(){
                this.$refs.addDialog
                        .open(null, 'oa:oa/process/step/add', 'add');
            },
            // 编辑
            update(row){
                this.$refs.addDialog
                        .open(row, 'oa:oa/process/step/update', 'edit');
            },
            // 删除一条记录
            deleteOne(row){
                this.$confirm('确定删除 ' + row.goodsName + ' ?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    RestUtil.post('oa:oa/process/step/delete', {id: row.stepId}, {enableLoading: true}).then((result) => {
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
            formatStatus(row, column, cellValue){
                if(cellValue){
                    return CodeUtil.getName(29, cellValue);
                }
                return '';
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
            formatTime(row, column, cellValue){
                if(cellValue){
                    return cellValue.split(' ')[0];
                }
                return '';
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
