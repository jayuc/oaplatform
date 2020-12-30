<template>
    <el-dialog title="市局（公司）证件（复印件）使用申请审批"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="申请人：" :label-width="formLabelWidth">
                        {{formData.applyName}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="所属部门：" :label-width="formLabelWidth">
                        {{formatOrg(formData.applyOrgId)}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="业务域：" prop="firmType" :label-width="formLabelWidth">
                        {{formatFirmType(formData.firmType)}}
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
                        {{formData.address}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="使用用途：" :label-width="formLabelWidth">
                        {{formData.content}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="是否复印：" :label-width="formLabelWidth">
                        {{formatYesFlag(formData.holidayType)}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="是否外借：" :label-width="formLabelWidth">
                        {{formatYesFlag(formData.days)}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="备注：" prop="content" :label-width="formLabelWidth">
                        {{formData.mark}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="附件：" :label-width="formLabelWidth">
                        <div v-for="(item, i) in fileList" :key="i">
                            {{i+1}}. {{item.name}}
                            <a :href="item.url" download style="float: right">下载</a>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="审批意见：" :label-width="formLabelWidth">
                        <el-input v-model="formData.approveOpinion" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="submit(2)">不同意</el-button>
            <el-button type="primary" :disabled="submitBtnDisabled" @click="submit(1)">同 意</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import OrgUtil from '@/utils/OrgUtil';
    import handler from './handler';
    import CodeUtil from '@/utils/CodeUtil';
    import TipUtil from "@/utils/TipUtil";

    export default {
        name: 'process-approve-leave',
        data(){
            return {
                visible: false,
                submitBtnDisabled: false,
                formLabelWidth: '110px',
                formData: {},
                url: '',
                fileList: []
            }
        },
        methods: {
            initFormData(){
                this.formData = {
                    approveOpinion: ''
                };
            },
            open(data, url){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.fileList = handler.handleFileList(this.formData);
                this.url = url;
            },
            close(){
                this.visible = false;
            },
            formatOrg(cellValue){
                if(cellValue){
                    let org = OrgUtil.getOrgById(cellValue);
                    if(org && org.attribute){
                        return org.attribute.shortOrgName;
                    }
                }
                return '';
            },
            formatFirmType(cellValue){
                return CodeUtil.getName(5, cellValue);
            },
            formatYesFlag(cellValue){
                if(cellValue == 1){
                    return '是';
                }else if(cellValue == 2){
                    return '否';
                }
                return '';
            },
            submit(passFlag){
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        let param = {};
                        this.formData.passFlag = passFlag;
                        Object.assign(param, this.formData);
                        delete param.createTime;
                        delete param.endTime;
                        delete param.startTime;
                        delete param.updateTime;
                        RestUtil.post(this.url, param, {
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
