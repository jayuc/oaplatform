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
                    <el-form-item label="工龄：" prop="workAge" :label-width="formLabelWidth">
                        <el-input v-model.number="formData.workAge" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="休假标准：" :label-width="formLabelWidth">
                        <yu-code-radio @change="holidayTypeChange" :code="3" ref="holidayTypeSelect"></yu-code-radio>
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
                    <el-form-item label="结束日期：" prop="endTime" :label-width="formLabelWidth">
                        <el-date-picker
                                v-model="formData.endTime"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="天数：" prop="days" :label-width="formLabelWidth">
                        <el-input v-model.number="formData.days" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">

                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button :disabled="submitBtnDisabled" type="primary" @click="submit">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import YuCodeRadio from "../public/yu-code-radio.vue";
    import user from '@/user';
    import OrgUtil from '@/utils/OrgUtil';

    export default {
        components: {YuCodeRadio},
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
                titleMap: {
                    add: '新 增',
                    edit: '编 辑'
                },
                title: '',
                url: '',
                rules: {
                    workAge: [
                        { required: true, message: '请输入工龄', trigger: 'blur' },
                        { type: 'number', message: '工龄必须为数字值'}
                    ],
                    days: [
                        { required: true, message: '请输入天数', trigger: 'blur' },
                        { type: 'number', message: '天数必须为数字值'}
                    ],
                    startTime: [
                        { required: true, message: '请选择开始日期', trigger: 'blur' },
                        { validator: validateStartTime, trigger: 'change' }
                    ],
                    endTime: [
                        { required: true, message: '请选择结束日期', trigger: 'blur' },
                        { validator: validateEndTime, trigger: 'change' }
                    ]
                }
            };
        },
        methods: {
            initFormData(){
                this.formData = {
                    billType: 1,
                    currentStep: '00',
                    applyId: user.get('userId'),
                    applyOrgId: user.get('orgId'),
                    userName: user.get('userName'),
                    orgName: OrgUtil.getShortNameById(user.get('orgId'))
                };
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
            },
            close(){
                this.visible = false;
            },
            holidayTypeChange(key){
                this.formData.holidayType = key;
            },
            submit(){
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        this.formData.billCode = new Date().getTime();
                        RestUtil.post(this.url, this.formData, {
                            enableLoading: true,       // 启动请求期间的正在加载
                            loadingStartFun: () => {   // 请求开始前执行
                                this.submitBtnDisabled = true;
                            },
                            loadingEndFun: () => {     // 请求开始后执行
                                this.submitBtnDisabled = false;
                            }
                        }).then(() => {
                            this.$emit('complete');
                            this.close();
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
