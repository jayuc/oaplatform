<template>
    <el-dialog :title="dialogTile"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="动态表单类别，可与表单类别进行动态匹配：" prop="billType" :label-width="formLabelWidth">
                        <el-input v-model="formData.billType" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="字段名称：" prop="feildName" :label-width="formLabelWidth">
                        <el-input v-model="formData.feildName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="字段类型，数据字典：8：" prop="feildType" :label-width="formLabelWidth">
                        <el-input v-model="formData.feildType" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="字段值类型，：" prop="feildValueType" :label-width="formLabelWidth">
                        <el-input v-model="formData.feildValueType" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="字段值类型的值：" prop="feildValueValue" :label-width="formLabelWidth">
                        <el-input v-model="formData.feildValueValue" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="审批时间：" prop="createTime" :label-width="formLabelWidth">
                        <el-input v-model="formData.createTime" autocomplete="off"></el-input>
                    </el-form-item>
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

    export default {
        components: {

        },
        name: 'oaBillCustomerField-add',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
                submitBtnDisabled: false,
                titleMap: {
                    add: '新增自定义流程字段',
                    edit: '编辑自定义流程字段'
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
                    billType: null,
                    feildName: null,
                    feildType: null,
                    feildValueType: null,
                    feildValueValue: null,
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
