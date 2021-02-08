<template>
    <el-dialog title="流程步骤详情"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="步骤名称：" :label-width="formLabelWidth">
                        {{formData.stepName}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="步骤类型，1：审批 2：条件 3：结束 4：备案：" :label-width="formLabelWidth">
                        {{formData.stepType}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="条件成功去向：" :label-width="formLabelWidth">
                        {{formData.successTo}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="条件失败去向：" :label-width="formLabelWidth">
                        {{formData.failTo}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="创建时间：" :label-width="formLabelWidth">
                        {{formData.createTime}}
                    </el-form-item>
                </el-col>
            <el-row>
                <div style="height: 20px;"></div>
            </el-row>
        </el-form>
    </el-dialog>
</template>

<script>

    import OrgUtil from '@/utils/OrgUtil';
    import CodeUtil from '@/utils/CodeUtil';

    export default {
        components: {

        },
        name: 'oaProcessStep-details',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
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
            formatTime(cellValue){
                if(cellValue){
                    return cellValue.split(' ')[0];
                }
                return '';
            },
            formatStatus(cellValue){
                if(cellValue){
                    return CodeUtil.getName(29, cellValue);
                }
                return '';
            },
        }
    }
</script>

<style scoped>

</style>
