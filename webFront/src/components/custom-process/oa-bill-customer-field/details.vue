<template>
    <el-dialog title="自定义流程字段详情"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="动态表单类别，可与表单类别进行动态匹配：" :label-width="formLabelWidth">
                        {{formData.billType}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="字段名称：" :label-width="formLabelWidth">
                        {{formData.feildName}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="字段类型，数据字典：8：" :label-width="formLabelWidth">
                        {{formData.feildType}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="字段值类型，：" :label-width="formLabelWidth">
                        {{formData.feildValueType}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="字段值类型的值：" :label-width="formLabelWidth">
                        {{formData.feildValueValue}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="审批时间：" :label-width="formLabelWidth">
                        {{formData.createTime}}
                    </el-form-item>
                </el-col>
            </el-row>
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
        name: 'oaBillCustomerField-details',
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
