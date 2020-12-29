<template>
    <el-dialog title="驳回处置"
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
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="出差事由：" prop="content" :label-width="formLabelWidth">
                        <el-input type="textarea" :rows="3" v-model.number="formData.content" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="出差人员：" prop="peopleList" :label-width="formLabelWidth">
                        <el-input v-model="formData.peopleList" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="出差人数：" prop="peopleNumber" :label-width="formLabelWidth">
                        <el-input v-model.number="formData.peopleNumber" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="目的地：" prop="address" :label-width="formLabelWidth">
                        <el-input v-model="formData.address" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="交通工具：" :label-width="formLabelWidth">
                        <yu-code-radio @change="travelToolChange"
                                       :initValue="formData.travelTool"
                                       :code="2"
                                       ref="travelToolSelect"></yu-code-radio>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="开始日期：" prop="startTime" :label-width="formLabelWidth">
                        <el-date-picker
                                v-model="formData.startTime"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="结束日期：" prop="endTime" style="width: 100%" :label-width="formLabelWidth">
                        <el-date-picker
                                v-model="formData.endTime"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
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
        name: 'process-add-leave',
        data(){
            // 开始日期校验
            let validateStartTime = (rule, value, callback) => {
                let endTime = this.formData.endTime;
                if(endTime && value > endTime){
                    callback(new Error('开始日期不能大于结束日期'));
                    return;
                }
                callback();
            };
            // 结束日期校验
            let validateEndTime = (rule, value, callback) => {
                let startTime = this.formData.startTime;
                if(startTime && value < startTime){
                    callback(new Error('结束日期不能小于开始日期'));
                    return;
                }
                callback();
            };
            return {
                visible: false,
                submitBtnDisabled: false,
                formLabelWidth: '110px',
                formData: {},
                title: '',
                url: '',
                rules: {
                    content: [
                        { required: true, message: '请输入出差事由', trigger: 'blur' }
                    ],
                    peopleList: [
                        { required: true, message: '请输入出差人员', trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: '请输入出差目的地', trigger: 'blur' }
                    ],
                    peopleNumber: [
                        { type: 'number', message: '人数必须为数字值'}
                    ],
                    startTime: [
                        { required: true, message: '请选择开始日期', trigger: 'blur' },
                        { validator: validateStartTime, trigger: 'change' }
                    ],
                    endTime: [
                        { required: true, message: '请选择结束日期', trigger: 'blur' },
                        { validator: validateEndTime, trigger: 'change' }
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
                    billType: 2,
                    currentStep: '00',
                    applyId: user.get('userId'),
                    applyOrgId: user.get('orgId'),
                    applyOrgCodePriv: user.get('orgCodePriv'),
                    userName: user.get('userName'),
                    orgName: OrgUtil.getShortNameById(user.get('orgId')),
                    content: '',
                    peopleList: '',
                    address: '',
                    peopleNumber: '',
                    startTime: '',
                    endTime: ''
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
            // 交通工具处理
            travelToolChange(key){
                this.formData.travelTool = key;
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
                if(typeof this.formData.startTime == 'string'){  // 当为字符串时，说明此时此值并没有被修改
                    this.formData.startTime = new Date(this.formData.startTime);
                }
                if(typeof this.formData.endTime == 'string'){    // 当为字符串时，说明此时此值并没有被修改
                    this.formData.endTime = new Date(this.formData.endTime);
                }
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
