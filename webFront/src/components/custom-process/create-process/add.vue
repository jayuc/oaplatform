<template>
    <el-dialog :title="dialogTile"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="角色名称：" prop="roleName" :label-width="formLabelWidth">
                        <el-input v-model="formData.roleName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="备注：" :label-width="formLabelWidth">
                        <el-input type="textarea" :rows="2" v-model.number="formData.mark" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-divider content-position="left">权限分配</el-divider>
            <el-row>
                <el-col :span="10" :offset="3">
                    <div class="el-transfer-panel" style="width: 240px;">
                        <div class="el-transfer-panel__header">
                            功能列表
                        </div>
                        <div style="height: 300px;overflow: auto;">
                            <el-tree :data="menuFunctionData"
                                     show-checkbox
                                     :props="defaultProps"
                                     nodeKey="functionCode"
                                     ref="functionTree"
                                     @check="checkNode"></el-tree>
                        </div>
                    </div>
                </el-col>
                <el-col :span="8" :offset="1">
                    <div class="el-transfer-panel function-selected-body" style="width: 240px;">
                        <div class="el-transfer-panel__header">
                            已选功能
                        </div>
                        <div style="height: 300px;overflow: auto;">
                            <el-tag style="width: 212px;margin: 5px 0px 0px 5px;"
                                    size="small"
                                    v-for="item in functionSelectedList"
                                    v-bind:key="item.functionId"
                                    @close="tagClick(item.functionCode)"
                                    closable>{{item.functionName}}</el-tag>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button :disabled="submitBtnDisabled" type="primary" @click="submit()">提 交</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import TipUtil from "@/utils/TipUtil";
    import ElRow from "element-ui/packages/row/src/row";

    export default {
        components: {
            ElRow
        },
        name: 'role-add-add',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
                submitBtnDisabled: false,
                functionListChecked: false,
                titleMap: {
                    add: '新增角色',
                    edit: '编辑角色'
                },
                dialogTile: '',
                url: '',
                titleType: '',
                rules: {
                    roleName: [
                        { required: true, message: '请输入角色名称', trigger: 'blur' }
                    ]
                },
                menuFunctionData: [],
                defaultProps: {
                    label: 'functionName',
                    value: 'functionCode'
                },
                functionSelectedList: []
            }
        },
        methods: {
            submit(){
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        this.formData.functionListJson = JSON.stringify(this.functionSelectedList);
                        RestUtil.post(this.url, this.formData, {
                            enableLoading: true,       // 启动请求期间的正在加载
                            loadingStartFun: () => {   // 请求开始前执行
                                this.submitBtnDisabled = true;
                            },
                            loadingEndFun: () => {     // 请求开始后执行
                                this.submitBtnDisabled = false;
                            }
                        }).then((result) => {
                            if(result == 1){
                                TipUtil.success('操作成功!');
                            }else {
                                TipUtil.error('操作失败!');
                            }
                            this.$emit('complete');
                            this.close();
                        }, (error) => {
                            console.error(error);
                            TipUtil.error('请求出错，请检查您的网络是否正常');
                        });
                    } else {
                        return false;
                    }
                });
            },
            initFormData(){
                this.formData = {
                    roleName: null,
                    mark: null,
                    enableFlag: 1
                };
            },
            open(data, url, titleType){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.url = url;
                this.titleType = titleType;
                this.dialogTile = this.titleMap[titleType];
                // 清空数据
                this.functionSelectedList = [];
                if(this.$refs['functionTree']){
                    this.$refs['functionTree'].setCheckedKeys([]);
                }
                RestUtil.get('role/getMenuFunctionTree').then((list) => {
                    this.menuFunctionData = list;
                    if(titleType == 'edit'){
                        RestUtil.get('role/getPermission', {roleId: data.roleId}).then((list) => {
                            let farr = [];
                            if(list instanceof Array){
                                for(let i=0; i<list.length; i++){
                                    farr.push(list[i].functionCode);
                                }
                                this.$refs['functionTree'].setCheckedKeys(farr);
                                this.functionSelectedList = list;
                            }
                        });
                    }
                });
            },
            close(){
                this.visible = false;
            },
            checkNode(node, obj){
                let nodes = obj.checkedNodes;
                this.functionSelectedList = [];
                for (let i=0; i<nodes.length; i++){
                    let item = nodes[i];
                    if(item.functionCode.length == 6){
                        this.functionSelectedList.push(item);
                    }
                }
            },
            tagClick(functionCode){
                this.$refs['functionTree'].setChecked(functionCode, false);
                let candidateSelected = [];
                for (let i=0; i<this.functionSelectedList.length; i++){
                    let item = this.functionSelectedList[i];
                    if(item.functionCode != functionCode){
                        candidateSelected.push(item);
                    }
                }
                this.functionSelectedList = candidateSelected;
            }
        }
    }
</script>

<style scoped>

</style>
