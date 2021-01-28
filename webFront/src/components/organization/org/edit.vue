<template>
    <el-dialog :title="dialogTile"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="机构名称：" prop="orgName" :label-width="formLabelWidth">
                        <el-input v-model="formData.orgName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="机构简称：" prop="shortOrgName" :label-width="formLabelWidth">
                        <el-input v-model="formData.shortOrgName" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="父级机构：" :label-width="formLabelWidth">
                        <yu-org-radio @change="changeOrg" :initValue="formData.parentId" ref="orgSelect"></yu-org-radio>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="负责人：" :label-width="formLabelWidth">
                        <el-cascader
                                v-model="formData.leaderId"
                                :options="treeData"
                                :props="property"
                                :key="cascaderId"
                                :show-all-levels="false"></el-cascader>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="分管领导：" :label-width="formLabelWidth">
                        <el-cascader
                                v-model="formData.deputyId"
                                :options="treeData1"
                                :props="property"
                                :key="cascaderId1"
                                :show-all-levels="false"></el-cascader>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="联系方式：" prop="tel" :label-width="formLabelWidth">
                        <el-input v-model="formData.tel" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="地址：" :label-width="formLabelWidth">
                        <el-input v-model="formData.address" autocomplete="off"></el-input>
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
        name: 'org-add',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
                submitBtnDisabled: false,
                loginNameDisabled: false,
                titleMap: {
                    add: '新增机构',
                    edit: '编辑机构'
                },
                dialogTile: '',
                url: '',
                titleType: '',
                rules: {
                    orgName: [
                        { required: true, message: '请输入机构名称', trigger: 'blur' }
                    ],
                    shortOrgName: [
                        { required: true, message: '请输入机构简称', trigger: 'blur' }
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
                    ]
                },
                property: {
                    label: 'userName',
                    value: 'userId'
                },
                treeData: [],
                cascaderId: 1,
                cascaderId1: 1,
                treeData1: [],
            }
        },
        methods: {
            submit(){
                let leaderId = this.formData.leaderId;
                if(leaderId instanceof Array){
                    this.formData.leaderId = leaderId[1];
                }
                let deputyId = this.formData.deputyId;
                if(deputyId instanceof Array){
                    this.formData.deputyId = deputyId[1];
                }
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
            initFormData(){
                this.formData = {
                    orgName: null,
                    shortOrgName: null,
                    parentId: -1,
                    parentCode: null,
                    tel: null,
                    address: null,
                    leaderId: null,
                    deputyId: null
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
                // 机构赋值
                if(this.$refs['orgSelect']){
                    this.$refs['orgSelect'].setInitValue(this.formData.parentId);
                }
                this.loadLeaderData(this.formData);
                this.loadData(this.formData);
            },
            close(){
                this.visible = false;
            },
            changeOrg(orgId, orgCode, org){
                this.formData.parentId = orgId;
                this.formData.parentCode = org.attribute.orgCodePriv;
            },
            loadLeaderData(row){
                this.treeData = [];
                this.cascaderId++;
                RestUtil.get('user/getLeaderCandidate', row).then((list) => {
                    let i = 1;
                    for (let index in list){
                        this.treeData.push({
                            userId: i,
                            userName: index,
                            children: list[index]
                        });
                        i++;
                    }
                });
            },
            loadData(row){
                this.treeData1 = [];
                this.cascaderId1++;
                RestUtil.get('user/getDeputyCandidate', row).then((list) => {
                    let i = 1;
                    for (let index in list){
                        this.treeData1.push({
                            userId: i,
                            userName: index,
                            children: list[index]
                        });
                        i++;
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>
