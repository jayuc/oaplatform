<template>
    <el-dialog :title="dialogTile"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="姓名：" prop="userName" :label-width="formLabelWidth">
                        <el-input v-model="formData.userName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="编号：" prop="userCode" :label-width="formLabelWidth">
                        <el-input v-model="formData.userCode" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="所属部门：" prop="orgId" :label-width="formLabelWidth">
                        <yu-org-radio @change="changeOrg" ref="orgSelect"></yu-org-radio>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="性别：" :label-width="formLabelWidth">
                        <yu-code-radio @change="changeSex" :code="4" ref="sexSelect"></yu-code-radio>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="岗位：" :label-width="formLabelWidth">
                        <el-input v-model="formData.position" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="科级以上：" :label-width="formLabelWidth">
                        <el-radio v-model="formData.yesChief" :label="1">是</el-radio>
                        <el-radio v-model="formData.yesChief" :label="2">否</el-radio>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="登录名：" prop="loginName" :label-width="formLabelWidth">
                        <el-input v-model="formData.loginName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="年龄：" prop="age" :label-width="formLabelWidth">
                        <el-input v-model.number="formData.age" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="联系方式：" prop="tel" :label-width="formLabelWidth">
                        <el-input v-model="formData.tel" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="手机号码：" prop="mobileTel" :label-width="formLabelWidth">
                        <el-input v-model="formData.mobileTel" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="角色：" :label-width="formLabelWidth">

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
    import YuCodeRadio from "../../public/yu-code-radio.vue";
    import YuOrgRadio from "../../public/yu-org-radio.vue";

    export default {
        components: {
            YuCodeRadio,
            YuOrgRadio
        },
        name: 'user-add',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {
                    yesChief: 2
                },
                submitBtnDisabled: false,
                titleMap: {
                    add: '新增用户',
                    edit: '编辑用户'
                },
                dialogTile: '',
                url: '',
                rules: {
                    userName: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    orgId: [
                        { required: true, message: '请选择所属机构', trigger: 'blur' }
                    ],
                    userCode: [
                        { required: true, message: '请输入编号', trigger: 'blur' },
                        { validator: (rule, value, callback) => {
                            let regular = /^\d{10,16}$/;
                            if(value){
                                if(!regular.test(value)){
                                    callback(new Error('编号需是10到16位的数字'));
                                }
                                callback();
                            }else {
                                callback(new Error('请输入编号'));
                            }
                        }, trigger: 'change' }
                    ],
                    loginName: [
                        { required: true, message: '请输入登录名', trigger: 'blur' },
                        { validator: (rule, value, callback) => {
                            let regular = /^[a-z]+$/;
                            if(value){
                                if(!regular.test(value)){
                                    callback(new Error('登录名需是小写字母组成'));
                                }
                                callback();
                            }else {
                                callback(new Error('请输入登录名'));
                            }
                        }, trigger: 'change' }
                    ],
                    age: [
                        { type: 'number', message: '年龄必须为数字值', trigger: 'change' }
                    ],
                    tel: [
                        { validator: (rule, value, callback) => {
                            let regular = /\d{3}-\d{8}|\d{4}-\d{7}/;
                            if(value){
                                if(!regular.test(value)){
                                    callback(new Error('请输入正确的联系方式'));
                                }
                            }
                            callback();
                        }, trigger: 'change' }
                    ],
                    mobileTel: [
                        { validator: (rule, value, callback) => {
                            let regular = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
                            if(value){
                                if(!regular.test(value)){
                                    callback(new Error('请输入正确的手机号码'));
                                }
                            }
                            callback();
                        }, trigger: 'change' }
                    ]
                }
            }
        },
        methods: {
            submit(){
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
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
            open(data, url, titleType){
                this.visible = true;
                Object.assign(this.formData, data);
                this.url = url;
                this.dialogTile = this.titleMap[titleType];
                // 表单复位
                if(this.$refs['formData']){
                    this.$refs['formData'].resetFields();
                }
                // 机构复位
                if(this.$refs['orgSelect']){
                    this.$refs['orgSelect'].reset();
                }
            },
            close(){
                this.visible = false;
            },
            changeSex(cellValue){
                this.formData.sex = cellValue;
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
