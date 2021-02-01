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

            <el-header height="104px">
                <div class="form-search-top-div"></div>
                <div class="form-search-container">
                    <span class="form-search-title">查询条件</span>
                    <el-form :inline="true" :model="formData" class="demo-form-inline">
                        <el-form-item>
                            <el-button type="primary" @click="submit()" :disabled="searchBtnStatus">查 询</el-button>
                        </el-form-item>
                        <yu-auth code="020101" style="margin-left: 10px;">
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
                              prop="orgCode"
                              label="机构编号"
                              align="center"
                              minWidth="120">
                        </el-table-column>
                        <el-table-column
                              prop="orgName"
                              label="机构名称"
                              align="center"
                              minWidth="180">
                        </el-table-column>
                        <el-table-column
                                prop="shortOrgName"
                                label="机构简称"
                                align="center"
                                minWidth="160">
                        </el-table-column>
                        <el-table-column
                                prop="leaderName"
                                label="负责人"
                                align="center"
                                minWidth="80">
                        </el-table-column>
                        <el-table-column
                                prop="deputyName"
                                label="分管领导"
                                align="center"
                                minWidth="80">
                        </el-table-column>
                        <el-table-column
                              prop="operation"
                              fixed="right"
                              align="center"
                              minWidth="120"
                              label="操作">
                            <template slot-scope="scope">
                                <yu-auth name="rowButton" code="020102">
                                    <el-button @click="update(scope.row)"
                                               icon="el-icon-edit"
                                               title="编辑"
                                               type="warning" size="small"></el-button>
                                </yu-auth>
                                <yu-auth name="rowButton" code="020103">
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

        </el-container>

        <org-set-leader ref="orgSetLeader" @complete="submit" />

        <org-set-deputy ref="orgSetDeputy" @complete="submit" />

        <detail-dialog ref="detailDialog" />

        <add-dialog ref="addDialog" @complete="submit" />

        <edit-dialog ref="editDialog" @complete="submit" />

    </el-container>

</template>

<script>
    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';
    import OrgSetLeader from '../orgSetLeader.vue';
    import OrgSetDeputy from '../orgSetDeputy.vue';
    import DetailDialog from './details.vue';
    import EditDialog from './edit.vue';
    import AddDialog from './add.vue';

    export default {
        name: 'org-manage',
        components: {
            OrgSetLeader,
            OrgSetDeputy,
            DetailDialog,
            AddDialog,
            EditDialog
        },
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
                }else {
                    let orgs = Config.get('$org');
                    if(orgs instanceof Array && orgs.length > 0){
                        let attr = orgs[0].attribute;
                        if(attr){
                            this.formData.orgCodePriv = attr.orgCodePriv;
                        }
                    }
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
            // 查看详情
            details(row){
                this.$refs.detailDialog.open(row);
            },
            // 新增
            add(){
                this.$refs.addDialog.open(null, 'org/add', 'add');
            },
            // 编辑
            update(row){
                this.$refs.editDialog.open(row, 'org/update', 'edit');
            },
            // 删除一条记录
            deleteOne(row){
                this.$confirm('确定删除 ' + row.orgName + ' ?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    RestUtil.post('org/delete', {orgId: row.orgId}, {enableLoading: true}).then((result) => {
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
            ifShowLeader(){
                return true;
            },
            ifShowDeputy(){
                return true;
            },
            doSetLeader(row){
                this.$refs.orgSetLeader.open(row);
            },
            doSetDeputy(row){
                this.$refs.orgSetDeputy.open(row);
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
