<template>

    <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside width="300px">
            <div class="org-tree-top-div">
                <span class="org-tree-title">机构导航</span>
            </div>
            <div class="org-tree-container" :style="orgTreeContainerStyle">
                <el-tree :data="orgTreeData"
                         :props="defaultProps"
                         node-key="orgId"
                         :default-expanded-keys="[-1]"
                         @node-click="handleNodeClick"
                ></el-tree>
            </div>
        </el-aside>

        <el-container>

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
                              prop="orgCode"
                              label="机构编号"
                              align="center"
                              width="140">
                        </el-table-column>
                        <el-table-column
                              prop="orgName"
                              label="机构名称"
                              align="center"
                              width="350">
                        </el-table-column>
                        <el-table-column
                                prop="shortOrgName"
                                label="机构简称"
                                align="center"
                                width="220">
                        </el-table-column>
                        <el-table-column
                                prop="leaderName"
                                label="负责人"
                                align="center"
                                width="100">
                        </el-table-column>
                        <el-table-column
                                prop="deputyName"
                                label="分管领导"
                                align="center"
                                width="100">
                        </el-table-column>
                        <el-table-column
                              prop="operation"
                              align="center"
                              label="操作">
                            <template slot-scope="scope">
                                <el-button v-if="ifShowLeader(scope.row)" type="text" size="small">设置负责人</el-button>
                                <el-button v-if="ifShowDeputy(scope.row)" type="text" size="small">设置分管领导</el-button>
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

        </el-container>

    </el-container>

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
                orgTreeContainerStyle: '',
                tableHeight: 500,
                formData: {
                    pageNumber: 1,
                    pageSize: 15,
                    orgId: 0,
                    orgCodePriv: ''
                },
                searchBtnStatus: false,
                total: 0,
                tableData: [],
                orgTreeData: [],
                defaultProps: {
                    children: 'children',
                    label: (data) => {
                        let names = data.orgName.split('-');
                        let name = names[names.length-1];
                        if(name.indexOf('（内部专卖管理') > -1){
                            return name.split('（')[0];
                        }
                        return name;
                    }
                }
            }
        },
        methods: {
            submit(orgCodePriv){
                if(typeof orgCodePriv != 'undefined'){
                    this.formData.orgCodePriv = orgCodePriv;
                }
                RestUtil.get('org/list', this.formData, {
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
            ifShowLeader(){
                return true;
            },
            ifShowDeputy(){
                return true;
            },
            // 机构树点击事件
            handleNodeClick(data){
                this.formData.pageNumber = 1;
                let attribute = data.attribute;
                if(attribute){
                    this.submit(attribute.orgCodePriv);
                }else {
                    this.submit('');
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
                let tableContainerHeight = pageHeight - 42;
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
            let org = Config.get('$org');
            if(org instanceof Array){
                this.orgTreeData = org;
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
