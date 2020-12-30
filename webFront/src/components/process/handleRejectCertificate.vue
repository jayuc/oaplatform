<template>
    <el-dialog title="市局（公司）证件（复印件）使用申请驳回处置"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData" :rules="rules">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="申请人：" :label-width="formLabelWidth">
                        {{formData.userName}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="所属部门：" :label-width="formLabelWidth">
                        {{formData.orgName}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="业务域：" prop="firmType" :label-width="formLabelWidth">
                        <yu-code-radio :initValue="formData.firmType"
                                       @change="firmTypeChange"
                                       :code="5"
                                       ref="firmTypeSelect"></yu-code-radio>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="申请时间：" :label-width="formLabelWidth">
                        {{formData.createTime}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="使用证件：" prop="address" :label-width="formLabelWidth">
                        <el-input v-model="formData.address" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="使用用途：" prop="content" :label-width="formLabelWidth">
                        <el-input v-model="formData.content" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="是否复印：" prop="holidayType" :label-width="formLabelWidth">
                        <el-radio v-model="formData.holidayType" :label="1">是</el-radio>
                        <el-radio v-model="formData.holidayType" :label="2">否</el-radio>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="是否外借：" prop="days" :label-width="formLabelWidth">
                        <el-radio v-model="formData.days" :label="1">是</el-radio>
                        <el-radio v-model="formData.days" :label="2">否</el-radio>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="备注：" :label-width="formLabelWidth">
                        <el-input type="textarea" :rows="3" v-model.number="formData.mark" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-form-item label="附件上传：" :label-width="formLabelWidth">
                    <el-upload 	:action="uploadUrl"
                                  :on-remove="handleRemove"
                                  :before-remove="beforeRemove"
                                  :on-success="afterFileSuccess"
                                  :limit="3"
                                  :on-exceed="handleExceed"
                                  :file-list="fileList">
                        <el-button size="mini"
                                   type="success"
                                   plain
                        >
                            点击上传
                        </el-button>
                        <i class="el-icon-success success" v-show="fileokIcon"></i>
                        <i class="el-icon-error error" v-show="filenoIcon"></i>
                        <span style="margin-left: 20px;color: red;">最多上传3个文件</span>
                    </el-upload>
                </el-form-item>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button :disabled="submitBtnDisabled" type="primary" @click="submit(0)">提 交</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import user from '@/user';
    import OrgUtil from '@/utils/OrgUtil';
    import ElRow from "element-ui/packages/row/src/row";
    import Config from '@/config';
    import YuCodeRadio from "../public/yu-code-radio.vue";
    import TipUtil from "@/utils/TipUtil";

    export default {
        components: {ElRow, YuCodeRadio},
        name: 'process-reject-business-trip',
        data(){
            return {
                visible: false,
                submitBtnDisabled: false,
                formLabelWidth: '110px',
                formData: {},
                title: '',
                url: '',
                rules: {
                    content: [
                        { required: true, message: '请输入使用用途', trigger: 'blur' }
                    ],
                    holidayType: [
                        { required: true, message: '请选择是否复印', trigger: 'blur' }
                    ],
                    days: [
                        { required: true, message: '请选择是否外借', trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: '请输入使用证件', trigger: 'blur' }
                    ],
                    firmType: [
                        { required: true, message: '请选择业务域', trigger: 'blur' }
                    ]
                },
                uploadUrl: Config.get('restRoot') + 'upload/file/one',  // 图片上传地址
                fileokIcon: false,  // 文件 成功标识
                filenoIcon: false,  // 文件 失败标识
                fileList: [],
                fileArr: []
            };
        },
        methods: {
            initFormData(){
                this.formData = {
                    currentStep: '00',
                    applyId: user.get('userId'),
                    applyOrgId: user.get('orgId'),
                    applyOrgCodePriv: user.get('orgCodePriv'),
                    userName: user.get('userName'),
                    orgName: OrgUtil.getShortNameById(user.get('orgId')),
                    content: '',
                    holidayType: '',
                    days: '',
                    mark: '',
                    address: ''
                };
                this.fileList = [];
                this.fileArr = [];
            },
            open(data, url){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.url = url;
            },
            close(){
                this.visible = false;
            },
            afterFileSuccess(file){
                this.fileArr.push(file);
            },
            handleRemove(file) {
                for (let i=0; i<this.fileArr.length; i++){
                    if(this.fileArr[i] && this.fileArr[i].fileName == file.name){
                        this.fileArr[i] = null;
                    }
                }
            },
            handleExceed(files, fileList) {
                this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            beforeRemove(file) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            },
            firmTypeChange(key){
                this.formData.firmType = key;
            },
            submit(passFlag){
                let a = 1;
                let rootPath = Config.get('uploadUrl');
                for (let i=0; i<this.fileArr.length; i++){
                    let file = this.fileArr[i];
                    if(file){
                        if(a == 1){
                            this.formData.fileUrl1 = rootPath + file.path;
                        }
                        if(a == 2){
                            this.formData.fileUrl2 = rootPath + file.path;
                        }
                        if(a == 3){
                            this.formData.fileUrl3 = rootPath + file.path;
                        }
                    }
                    a++;
                }
                delete this.formData.createTime;
                delete this.formData.updateTime;
                delete this.formData.startTime;
                delete this.formData.endTime;
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        this.formData.billCode = new Date().getTime();
                        this.formData.passFlag = passFlag;
                        RestUtil.post(this.url, this.formData, {
                            enableLoading: true,       // 启动请求期间的正在加载
                            loadingStartFun: () => {   // 请求开始前执行
                                this.submitBtnDisabled = true;
                            },
                            loadingEndFun: () => {     // 请求开始后执行
                                this.submitBtnDisabled = false;
                            }
                        }).then((result) => {
                            if(result && result.error.length > 0){
                                TipUtil.error(result.error);
                                return;
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
            }
        }
    }
</script>

<style scoped>

</style>
