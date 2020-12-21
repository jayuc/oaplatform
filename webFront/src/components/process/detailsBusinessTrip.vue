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
                        {{formData.peopleNumber}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="目的地：" prop="address" :label-width="formLabelWidth">
                        {{formData.address}}
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
                <div class="process-status">进度如下：</div>
            </el-row>
            <el-row>
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
    import OrgUtil from '@/utils/OrgUtil';
    import ElRow from "element-ui/packages/row/src/row";
    import ElStep from "../../../node_modules/element-ui/packages/steps/src/step.vue";

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
                ]
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
