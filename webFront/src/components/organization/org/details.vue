<template>
    <el-dialog title="机构详情"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData" ref="formData">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="机构名称：" :label-width="formLabelWidth">
                        {{formData.orgName}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="机构简称：" prop="firmType" :label-width="formLabelWidth">
                        {{formData.shortOrgName}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="机构编号：" :label-width="formLabelWidth">
                        {{formData.orgCode}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="机构权限：" :label-width="formLabelWidth">
                        {{formData.orgCodePriv}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="父级机构：" :label-width="formLabelWidth">
                        {{formatOrg(formData.parentId)}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="联系方式：" :label-width="formLabelWidth">
                        {{formData.tel}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="地址：" :label-width="formLabelWidth">
                        {{formData.address}}
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

    export default {
        components: {

        },
        name: 'org-details',
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
                        return org.attribute.orgName;
                    }
                }
                return '';
            }
        }
    }
</script>

<style scoped>

</style>
