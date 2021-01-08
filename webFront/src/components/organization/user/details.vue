<template>
    <el-dialog title="用户详情"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="姓名：" :label-width="formLabelWidth">
                        {{formData.userName}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="编号：" prop="firmType" :label-width="formLabelWidth">
                        {{formData.userCode}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="所属部门：" :label-width="formLabelWidth">
                        {{formatOrg(formData.orgId)}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="性别：" :label-width="formLabelWidth">
                        {{formatSex(formData.sex)}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="岗位：" :label-width="formLabelWidth">
                        {{formData.position}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="科级以上：" :label-width="formLabelWidth">
                        {{formatChief(formData.yesChief)}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="登录名：" :label-width="formLabelWidth">
                        {{formData.loginName}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="年龄：" :label-width="formLabelWidth">
                        {{formData.age}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="联系方式：" :label-width="formLabelWidth">
                        {{formData.tel}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="手机号码：" :label-width="formLabelWidth">
                        {{formData.mobileTel}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="角色：" :label-width="formLabelWidth">

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
    import ElRow from "element-ui/packages/row/src/row";

    export default {
        components: {
            ElRow
        },
        name: 'user-details',
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
            formatSex(cellValue){
                return CodeUtil.getName(4, cellValue);
            },
            formatChief(cellValue){
                if(cellValue == 1){
                    return '是';
                }else if(cellValue == 2){
                    return '否';
                }
                return '';
            },
        }
    }
</script>

<style scoped>

</style>
