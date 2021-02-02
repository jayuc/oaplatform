<template>
    <el-dialog title="修改密码"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="原密码：" prop="originPassword" :label-width="formLabelWidth">
                        <el-input type="password" v-model="formData.originPassword" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="新密码：" prop="newPassword" :label-width="formLabelWidth">
                        <el-input type="password" v-model="formData.newPassword" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <div class="password-tip">新密码需包含字母、数字、特殊字符 至少8位</div>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="确认新密码：" prop="againPassword" :label-width="formLabelWidth">
                        <el-input type="password" v-model="formData.againPassword" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <div class="password-tip">新密码需包含字母、数字、特殊字符 至少8位</div>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button :disabled="submitBtnDisabled" type="primary" @click="submit()">保 存</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import Md5Util from '@/utils/Md5Util';
    import user from '@/user';
    import TipUtil from '@/utils/TipUtil';
    import RestUtil from "../../utils/RestUtil";
    import ElCol from "element-ui/packages/col/src/col";
    const pwdReg = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*])[\da-zA-Z~!@#$%^&*]{8,}$/;

    export default {
        components: {ElCol},
        name: 'password-setting',
        data(){
            let validatePwd = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if(!pwdReg.test(this.formData.newPassword)) {
                        callback(new Error('需包含字母、数字、特殊字符 至少8位'));
                    }
                    callback();
                }
            };
            let validateCheckPwd = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.formData.newPassword) {
                    callback(new Error('两次输入密码不一致!'));
                } else if(!pwdReg.test(this.formData.againPassword)) {
                    callback(new Error('需包含字母、数字、特殊字符 至少8位'));
                } else {
                    callback();
                }
            };
            return {
                visible: false,
                submitBtnDisabled: false,
                formLabelWidth: '120px',
                loginName: user.get('loginName'),
                formData: {},
                rules: {
                    originPassword: [
                        { required: true, message: '请输入原密码', trigger: 'blur' }
                    ],
                    newPassword: [
                        { required: true, message: '请输入新密码', trigger: 'blur' },
                        { validator: validatePwd, trigger: 'blur' }
                    ],
                    againPassword: [
                        { required: true, message: '请再次新密码', trigger: 'blur' },
                        { validator: validateCheckPwd, trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            submit(){
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        if(this.formData.againPassword != this.formData.newPassword){
                            TipUtil.error('两次密码应该相同');
                            return;
                        }
                        RestUtil.post('user/updatePassword', {
                            originPassword: Md5Util.encode(this.formData.originPassword),
                            password: Md5Util.encode(this.formData.newPassword),
                            loginName: this.loginName
                        }, {
                            enableLoading: true,       // 启动请求期间的正在加载
                            loadingStartFun: () => {   // 请求开始前执行
                                this.submitBtnDisabled = true;
                            },
                            loadingEndFun: () => {     // 请求开始后执行
                                this.submitBtnDisabled = false;
                            }
                        }).then((result) => {
                            let error = result.error;
                            if(error){
                                TipUtil.error(error);
                            }else {
                                this.formData.originPassword = '';
                                this.formData.newPassword = '';
                                this.formData.againPassword = '';
                                TipUtil.success('密码修改成功');
                                this.close();
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            open(){
                this.visible = true;
            },
            close(){
                this.visible = false;
            }
        }
    }
</script>

<style scoped>
    .password-tip{
        color: red;
        float: left;
        margin-left: 10px;
        line-height: 42px;
    }
</style>
