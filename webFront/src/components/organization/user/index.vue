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
                        <el-form-item label="用户名：">
                            <el-input v-model="formData.userName" placeholder="请输入用户名"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="submit('')" :disabled="searchBtnStatus">查 询</el-button>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="success" @click="add" >新 增</el-button>
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
                              width="160">
                        </el-table-column>
                        <el-table-column
                              prop="userName"
                              label="姓名"
                              align="center"
                              width="120">
                        </el-table-column>
                        <el-table-column
                              prop="sex"
                              label="性别"
                              align="center"
                              :formatter="formatSex"
                              width="60">
                        </el-table-column>
                        <el-table-column
                              prop="orgId"
                              label="所属机构"
                              align="center"
                              :formatter="formatOrg"
                              width="210">
                        </el-table-column>
                        <el-table-column
                              prop="position"
                              label="岗位"
                              align="center"
                              width="240">
                        </el-table-column>
                        <el-table-column
                              prop="loginName"
                              label="登录名"
                              align="center"
                              width="100">
                        </el-table-column>
                        <el-table-column
                                prop="yesChief"
                                label="职级"
                                align="center"
                                :formatter="formatChief"
                                width="100">
                        </el-table-column>
                        <el-table-column
                              prop="operation"
                              align="center"
                              label="操作">
                            <template slot-scope="scope">
                                <el-button @click="details(scope.row)"
                                           icon="el-icon-document"
                                           title="查看详情"
                                           type="success" size="small"></el-button>
                                <el-button @click="update(scope.row)"
                                           icon="el-icon-edit"
                                           title="编辑"
                                           type="warning" size="small"></el-button>
                                <el-button @click="resetPassword(scope.row)"
                                           icon="el-icon-refresh-right"
                                           title="重置密码"
                                           type="warning" size="small"></el-button>
                                <el-button @click="deleteOne(scope.row)"
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

        </el-container>

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
    import Md5Util from '@/utils/Md5Util';
    import DetailDialog from './details.vue';
    import AddDialog from './add.vue';

    export default {
        name: 'user-setting',
        components: {
            DetailDialog,
            AddDialog
        },
        data(){
            return {
                tableContainerStyle: '',
                orgTreeContainerStyle: '',
                tableHeight: 500,
                formData: {
                    userName: '',
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
            // 查询
            submit(orgCodePriv){
                if(typeof orgCodePriv != 'undefined'){
                    this.formData.orgCodePriv = orgCodePriv;
                }
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
                }, (error) => {
                    console.error(error);
                    TipUtil.error('请求出错，请检查您的网络是否正常');
                });
            },
            // 查看详情
            details(row){
                this.$refs.detailDialog.open(row);
            },
            // 新增
            add(){
                this.$refs.addDialog.open(null, 'user/add', 'add');
            },
            // 编辑
            update(row){
                this.$refs.addDialog.open(row, 'user/update', 'edit');
            },
            // 删除一条记录
            deleteOne(row){
                this.$confirm('确定删除 ' + row.userName + ' ?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    RestUtil.post('user/deleteOne', {id: row.userId}, {enableLoading: true}).then((result) => {
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
            // 重置密码
            resetPassword(row){
                this.$confirm('确定为 ' + row.userName + ' 重置密码?', '警告', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    RestUtil.post('user/resetPassword', {
                            userId: row.userId,
                            password: Md5Util.encode('hfyc@1234')
                        },
                        {enableLoading: true}).then((result) => {
                        if(result == 1){
                            TipUtil.success('重置密码成功!');
                            this.submit();
                        }else {
                            TipUtil.error('重置密码失败!');
                        }
                    }, () => {
                        TipUtil.error('重置密码失败!');
                    });
                }).catch(() => {
                    TipUtil.info('已取消重置密码');
                });
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
            formatSex(row, column, cellValue){
                if(cellValue){
                    return CodeUtil.getName(4, cellValue);
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
            formatChief(row, column, cellValue){
                if(cellValue == 1){
                    return '科级以上';
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
