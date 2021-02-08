<template>
    <el-dialog :title="dialogTile"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="步骤名称：" prop="stepName" :label-width="formLabelWidth">
                        <el-input v-model="formData.stepName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="步骤类型，1：审批 2：条件 3：结束 4：备案：" prop="stepType" :label-width="formLabelWidth">
                        <el-input v-model="formData.stepType" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="条件成功去向：" prop="successTo" :label-width="formLabelWidth">
                        <el-input v-model="formData.successTo" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="条件失败去向：" prop="failTo" :label-width="formLabelWidth">
                        <el-input v-model="formData.failTo" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="创建时间：" prop="createTime" :label-width="formLabelWidth">
                        <el-input v-model="formData.createTime" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
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

    export default {
        components: {

        },
        name: 'oaProcessStep-add',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
                submitBtnDisabled: false,
                titleMap: {
                    add: '新增流程步骤',
                    edit: '编辑流程步骤'
                },
                dialogTile: '',
                url: '',
                titleType: '',
                rules: {

                }
            }
        },
        methods: {
            submit(){
                this.$refs['formData']
                    .validate((valid) => {
                    if (valid) {
                        if(this.titleType == 'add'){
                            console.log('add');
                        }
                        if(this.titleType == 'edit'){
                            console.log('edit');
                        }
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
                    stepName: null,
                    stepType: null,
                    successTo: null,
                    failTo: null,
                    createTime: null,
                };
                // 表单复位
                if(this.$refs['formData']){
                    this.$refs['formData'].resetFields();
                }
            },
            open(data, url, titleType){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.url = url;
                this.titleType = titleType;
                this.dialogTile = this.titleMap[titleType];
                // 赋值
                let propArr = [];
                for(let i=0; i<propArr.length; i++){
                    let prop = propArr[i];
                    if(this.$refs
                                    [prop + 'Select']){
                        this.$refs
                                [prop + 'Select'].setInitValue(this.formData[prop]);
                    }
                }
            },
            close(){
                this.visible = false;
            },
            changeStatus(cellValue){
                this.formData.status = cellValue;
            },
            changeOrg(orgId, orgCode){
                this.formData.orgId = orgId;
                this.formData.orgCode = orgCode;
            }
        }
    }
</script>

<style scoped>

</style>
