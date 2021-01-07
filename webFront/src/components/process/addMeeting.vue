<template>
    <el-dialog :title="title"
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
                    <el-form-item label="参会人数：" prop="peopleNumber" :label-width="formLabelWidth">
                        <el-input style="width: 200px;" v-model.number="formData.peopleNumber" autocomplete="off"></el-input>
                        人
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="使用时间：" prop="useTime" :label-width="formLabelWidth">
                        <el-date-picker
                                v-model="formData.useTime"
                                type="date"
                                style="width: 210px"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="" prop="sTime">
                    <el-time-select
                            placeholder="开始时间"
                            v-model="formData.sTime"
                            style="width: 155px"
                            :picker-options="{
                                  start: '08:30',
                                  step: '00:30',
                                  end: '18:30'
                                }">
                    </el-time-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="" prop="eTime">
                    <el-time-select
                            placeholder="结束时间"
                            v-model="formData.eTime"
                            style="width: 150px"
                            :picker-options="{
                                  start: '08:30',
                                  step: '00:30',
                                  end: '18:30',
                                  minTime: formData.sTime
                                }">
                    </el-time-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="会场类型：" prop="holidayType" :label-width="formLabelWidth">
                        <yu-code-radio @change="holidayTypeChange" :code="6" ref="holidayTypeSelect"></yu-code-radio>
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
    import YuCodeRadio from "../public/yu-code-radio.vue";
    import user from '@/user';
    import OrgUtil from '@/utils/OrgUtil';
    import Config from '@/config';
    import TipUtil from "@/utils/TipUtil";
    import ElCol from "element-ui/packages/col/src/col";
    import DateUtil from '@/utils/DateUtil';

    export default {
        components: {
            ElCol,
            YuCodeRadio},
        name: 'process-add-meeting',
        data(){
            return {
                visible: false,
                submitBtnDisabled: false,
                formLabelWidth: '110px',
                formData: {},
                titleMap: {
                    add: '市局机关会议室申请',
                    edit: '市局机关会议室编辑'
                },
                title: '',
                url: '',
                rules: {
                    peopleNumber: [
                        { required: true, type: 'number', message: '人数必须为数字值', trigger: 'blur'}
                    ],
                    holidayType: [
                        { required: true, message: '请选择会场类型', trigger: 'blur' }
                    ],
                    useTime: [
                        { required: true, message: '请选择使用日期', trigger: 'blur' }
                    ],
                    sTime: [
                        { required: true, message: '请选择开始时间', trigger: 'blur' }
                    ],
                    eTime: [
                        { required: true, message: '请选择结束时间', trigger: 'blur' }
                    ],
                    firmType: [
                        { required: true, message: '请选择业务域', trigger: 'blur' }
                    ],
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
                    applyOrgYesOffice: user.get('yesOffice'),
                    applyId: user.get('userId'),
                    applyOrgId: user.get('orgId'),
                    applyOrgCodePriv: user.get('orgCodePriv'),
                    userName: user.get('userName'),
                    firmType: user.get('firmType'),
                    orgName: OrgUtil.getShortNameById(user.get('orgId'))
                };
                this.fileList = [];
                this.fileArr = [];
                // 休假标准下拉框复位
                if(this.$refs.holidayTypeSelect){
                    this.$refs.holidayTypeSelect.reset();
                }
            },
            open(data, url, titleType){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.url = url;
                this.title = this.titleMap[titleType];
                // 表单复位
                if(this.$refs['formData']){
                    this.$refs['formData'].resetFields();
                }
            },
            close(){
                this.visible = false;
            },
            holidayTypeChange(key){
                this.formData.holidayType = key;
            },
            firmTypeChange(key){
                this.formData.firmType = key;
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
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        this.formData.billCode = new Date().getTime();
                        this.formData.passFlag = passFlag;
                        let ut = DateUtil.format(this.formData.useTime, 'YYYY-MM-DD');
                        this.formData.startTime = DateUtil.date(ut + ' ' + this.formData.sTime);
                        this.formData.endTime = DateUtil.date(ut + ' ' + this.formData.eTime);
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
