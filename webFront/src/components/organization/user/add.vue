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
                        <yu-org-radio @change="changeOrg" :initValue="formData.orgId" ref="orgSelect"></yu-org-radio>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="性别：" :label-width="formLabelWidth">
                        <yu-code-radio @change="changeSex"
                                       :initValue="formData.sex"
                                       :code="4"
                                       ref="sexSelect"></yu-code-radio>
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
                        <el-input :disabled="loginNameDisabled" v-model="formData.loginName" autocomplete="off"></el-input>
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
                        <el-button type="primary"
                                   title="添加角色"
                                   size="mini"
                                   icon="el-icon-plus"
                                   @click="addRole"
                                   circle></el-button>
                        <el-tag
                            v-for="tag in tags"
                            :key="tag.roleId"
                            style="margin-left: 10px;"
                            closable
                            @close="removeRole(tag.roleId)"
                            type="success">
                            {{tag.roleName}}
                        </el-tag>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button :disabled="submitBtnDisabled" type="primary" @click="submit()">提 交</el-button>
        </div>

        <add-role ref="addRole" @select-row="selectRole" />

    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import TipUtil from "@/utils/TipUtil";
    import Md5Util from '@/utils/Md5Util';
    import innerConfig from '@/config/innerConfig';
    import AddRole from './addRole.vue';

    export default {
        components: {
            AddRole
        },
        name: 'user-add',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
                submitBtnDisabled: false,
                loginNameDisabled: false,
                titleMap: {
                    add: '新增用户',
                    edit: '编辑用户'
                },
                dialogTile: '',
                url: '',
                titleType: '',
                tags: [],
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
                            let regular = /^[a-z_]+$/;
                            if(value){
                                if(!regular.test(value)){
                                    callback(new Error('登录名需是小写字母和下划线组成'));
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
                        if(this.titleType == 'add'){
                            this.formData.password = Md5Util.encode(innerConfig.defaultPassword)
                        }
                        this.formData.roleJson = JSON.stringify(this.tags);
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
                    yesChief: 2,
                    userName: null,
                    userCode: null,
                    orgId: null,
                    sex: null,
                    position: null,
                    loginName: null,
                    age: null,
                    tel: null,
                    mobileTel: null
                };
                // 表单复位
                if(this.$refs['formData']){
                    this.$refs['formData'].resetFields();
                }
                // 机构复位
                if(this.$refs['orgSelect']){
                    this.$refs['orgSelect'].reset();
                }
            },
            open(data, url, titleType){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.url = url;
                this.titleType = titleType;
                this.dialogTile = this.titleMap[titleType];
                // 性别赋值
                if(this.$refs['sexSelect']){
                    this.$refs['sexSelect'].setInitValue(this.formData.sex);
                }
                if(this.$refs['positionIdSelect']){
                    this.$refs['positionIdSelect'].setInitValue(this.formData.positionId);
                }
                // 机构赋值
                if(this.$refs['orgSelect']){
                    this.$refs['orgSelect'].setInitValue(this.formData.orgId);
                }
                // 当编辑时禁止修改用户名
                if(this.titleType == 'edit'){
                    this.loginNameDisabled = true;
                    RestUtil.get('user/getRoleById', {userId: data.userId}).then((list) => {
                        this.tags = list;
                    });
                }else if(this.titleType == 'add'){
                    this.loginNameDisabled = false;
                    this.tags = [];
                }
            },
            close(){
                this.visible = false;
            },
            changeSex(cellValue){
                this.formData.sex = cellValue;
            },
            changePositionId(cellValue){
                this.formData.positionId = cellValue;
            },
            changeOrg(orgId, orgCode){
                this.formData.orgId = orgId;
                this.formData.orgCode = orgCode;
            },
            addRole(){
                this.$refs['addRole'].open(this.tags);
            },
            selectRole(roles){
                this.$refs['addRole'].close();
                this.tags = roles;
            },
            removeRole(roleId){
                let candidateTags = [];
                for(let i=0; i<this.tags.length; i++){
                    let tag = this.tags[i];
                    if(tag.roleId != roleId){
                        candidateTags.push(tag);
                    }
                }
                this.tags = candidateTags;
            }
        }
    }
</script>

<style scoped>

</style>
