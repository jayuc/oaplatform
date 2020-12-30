<template>
    <el-dialog title="出差详情"
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
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="出差事由：" prop="content" :label-width="formLabelWidth">
                        {{formData.content}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="出差人员：" prop="peopleList" :label-width="formLabelWidth">
                        {{formData.peopleList}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="出差人数：" prop="peopleNumber" :label-width="formLabelWidth">
                        {{formatPeople(formData.peopleNumber)}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="目的地：" prop="address" :label-width="formLabelWidth">
                        {{formData.address}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="交通工具：" :label-width="formLabelWidth">
                        {{formatTravelTool(formData.travelTool)}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="开始日期：" :label-width="formLabelWidth">
                        {{formatTime(formData.startTime)}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="结束日期：" :label-width="formLabelWidth">
                        {{formatTime(formData.endTime)}}
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
            <el-row>
                <div style="height: 20px;"></div>
            </el-row>
        </el-form>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import OrgUtil from '@/utils/OrgUtil';
    import ElRow from "element-ui/packages/row/src/row";
    import ElStep from "../../../node_modules/element-ui/packages/steps/src/step.vue";
    import ElCol from "element-ui/packages/col/src/col";
    import handler from './handler';
    import CodeUtil from '@/utils/CodeUtil';
    import TipUtil from "@/utils/TipUtil";

    export default {
        components: {
            ElCol,
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
                if(this.formData.passFlag != 2) {
                    this.showReject = false;
                    let param = {};
                    Object.assign(param, this.formData);
                    delete param.createTime;
                    delete param.endTime;
                    delete param.startTime;
                    delete param.updateTime;
                    RestUtil.post('oa/bill/simulateDeliver', param).then((result) => {
                        let data = result.result;
                        if (data) {
                            this.stepActive = data.total;
                            this.stepList = data.rows;
                        }
                    }, (error) => {
                        console.error(error);
                        TipUtil.error('请求出错，请检查您的网络是否正常');
                    });
                }else {
                    this.showReject = true;
                }
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
            formatTravelTool(cellValue){
                return CodeUtil.getName(2, cellValue);
            },
            formatFirmType(cellValue){
                return CodeUtil.getName(5, cellValue);
            },
            formatPeople(cellValue){
                if(cellValue){
                    return cellValue + '人';
                }
                return '';
            },
            formatTime(cellValue){
                if(cellValue){
                    return cellValue.split(' ')[0];
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
