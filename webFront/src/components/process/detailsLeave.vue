<template>
    <el-dialog title="详情"
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
            <el-row v-if="showReject">
                <el-col :span="12">
                    <el-form-item label="工单状态：" :label-width="formLabelWidth">
                        <span style="color: red;">已驳回</span>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="驳回人：" :label-width="formLabelWidth">
                        {{formData.approveName}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-if="showReject">
                <el-col :span="24">
                    <el-form-item label="驳回原因：" :label-width="formLabelWidth">
                        {{formData.approveContent}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-if="!showReject">
                <div class="process-status">进度如下：</div>
            </el-row>
            <el-row v-if="!showReject">
                <div class="process-body">
                    <el-steps :active="stepActive" finish-status="success">
                        <el-step v-for="item in stepList"
                                 :key="item.key"
                                 :description="item.desc"
                                 :title="item.title" />
                    </el-steps>
                </div>
            </el-row>
        </el-form>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import CodeUtil from '@/utils/CodeUtil';
    import OrgUtil from '@/utils/OrgUtil';
    import ElRow from "element-ui/packages/row/src/row";
    import ElStep from "../../../node_modules/element-ui/packages/steps/src/step.vue";
    import handler from './handler';

    export default {
        components: {
            ElStep,
            ElRow},
        name: 'process-details-leave',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
                stepActive: 1,
                stepList: [
                    {key: 1, title: '步骤 1：', desc: '工单申请'}
                ],
                fileList: [],
                showReject: false,      // 是否显示驳回
            }
        },
        methods: {
            initFormData(){
                this.formData = {};
            },
            open(data){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.fileList = handler.handleFileList(this.formData);
                if(this.formData.passFlag != 2){
                    this.showReject = false;
                    let param = {};
                    Object.assign(param, this.formData);
                    delete param.createTime;
                    delete param.endTime;
                    delete param.startTime;
                    delete param.updateTime;
                    RestUtil.post('oa/bill/simulateDeliver', param).then((result) => {
                        let data = result.result;
                        if(data){
                            this.stepActive = data.total;
                            this.stepList = data.rows;
                        }
                    });
                }else {
                    this.showReject = true;
                }
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
        }
    }
</script>

<style scoped>
    .process-status{
        color: #F56C6C;
        padding-bottom: 8px;
        font-size: 16px;
        margin-left: 15px;
    }
    .process-body{
        margin-left: 20px;
        margin-top: 12px;
    }
</style>
