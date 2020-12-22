<template>
    <div>
        <div style="height: 80px"></div>
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="6" :offset="1">
                    <el-form-item label="用户名：" :label-width="formLabelWidth" style="text-align: left">
                        {{loginName}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6" :offset="1">
                    <el-form-item label="原密码：" prop="originPassword" :label-width="formLabelWidth">
                        <el-input type="password" v-model="formData.originPassword" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6" :offset="1">
                    <el-form-item label="新密码：" prop="newPassword" :label-width="formLabelWidth">
                        <el-input type="password" v-model="formData.newPassword" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="6" :offset="1">
                    <el-form-item label="确认新密码：" prop="againPassword" :label-width="formLabelWidth">
                        <el-input type="password" v-model="formData.againPassword" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <el-form>
            <el-row>
                <el-col :span="6" :offset="1">
                    <el-button @click="submit"
                               type="primary"
                               style="width: 46%">保 存</el-button>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>

    import Md5Util from '@/utils/Md5Util';
    import user from '@/user';
    import TipUtil from '@/utils/TipUtil';
    import RestUtil from "../../utils/RestUtil";

    export default {
        name: 'password-setting',
        data(){
            return {
                formLabelWidth: '120px',
                loginName: user.get('loginName'),
                formData: {},
                rules: {
                    originPassword: [
                        { required: true, message: '请输入原密码', trigger: 'blur' }
                    ],
                    newPassword: [
                        { required: true, message: '请输入新密码', trigger: 'blur' }
                    ],
                    againPassword: [
                        { required: true, message: '请输入确认新密码', trigger: 'blur' }
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
                        }).then((result) => {
                            let error = result.error;
                            if(error){
                                TipUtil.error(error);
                            }else {
                                this.formData.originPassword = '';
                                this.formData.newPassword = '';
                                this.formData.againPassword = '';
                                TipUtil.success('密码修改成功');
                            }
                        });
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>
