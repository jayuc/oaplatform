<template>
    <el-dialog
            title="设置分管领导"
            :visible.sync="visible"
            width="500px">
        <el-form :model="formData" ref="formData">
            <el-row>
                <el-col :span="24">
                    <el-form-item label="所在机构：" :label-width="formLabelWidth">
                        {{formData.shortOrgName}}
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="选择分管领导：" :label-width="formLabelWidth">
                        <el-cascader
                            v-model="formData.deputyId"
                            :options="treeData"
                            :props="property"
                            :key="cascaderId"
                            :show-all-levels="false"></el-cascader>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="submit">保 存</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';

    export default {
        name: 'org-set-deputy',
        data(){
            return {
                visible: false,
                formLabelWidth: '150px',
                formData: {
                    shortOrgName: '',
                    deputyId: ''
                },
                property: {
                    label: 'userName',
                    value: 'userId'
                },
                cascaderId: 1,
                treeData: []
            }
        },
        methods: {
            open(data){
                this.visible = true;
                Object.assign(this.formData, data);
                this.loadData(this.formData);
            },
            close(){
                this.visible = false;
            },
            loadData(row){
                this.treeData = [];
                this.cascaderId++;
                RestUtil.get('user/getDeputyCandidate', row).then((list) => {
                    let i = 1;
                    for (let index in list){
                        this.treeData.push({
                            userId: i,
                            userName: index,
                            children: list[index]
                        });
                        i++;
                    }
                });
            },
            submit(){
                let orgId = this.formData.orgId;
                let deputyId = this.formData.deputyId;
                if(deputyId instanceof Array){
                    deputyId = deputyId[1];
                }
                RestUtil.post('org/update', {orgId, deputyId}).then(() => {
                    this.$emit('complete');
                    this.close();
                });
            }
        }
    }
</script>

<style scoped>

</style>
