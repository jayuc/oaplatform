<template>
    <el-dialog title="审批"
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
                    <el-form-item label="工龄：" :label-width="formLabelWidth">
                        {{formData.workAge}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="休假标准：" :label-width="formLabelWidth">
                        {{formatHolidayType(formData.holidayType)}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="开始日期：" :label-width="formLabelWidth">
                        {{formData.startTime}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="结束日期：" :label-width="formLabelWidth">
                        {{formData.endTime}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="天数：" :label-width="formLabelWidth">
                        {{formData.days}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">

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
            <el-button @click="close">不同意</el-button>
            <el-button type="primary" :disabled="submitBtnDisabled" @click="submit">同 意</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import CodeUtil from '@/utils/CodeUtil';
    import OrgUtil from '@/utils/OrgUtil';
    import handler from './handler';

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
            formatHolidayType(cellValue){
                return CodeUtil.getName(3, cellValue);
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
            submit(){
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        let param = {};
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
