<template>
    <el-dialog
            title="设置负责人"
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
                    <el-form-item label="选择负责人：" :label-width="formLabelWidth">
                        <el-cascader
                            v-model="formData.leaderId"
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
        name: 'org-set-leader',
        data(){
            return {
                visible: false,
                formLabelWidth: '150px',
                formData: {
                    shortOrgName: '',
                    leaderId: ''
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
                RestUtil.get('user/getLeaderCandidate', row).then((list) => {
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
                let leaderId = this.formData.leaderId;
                if(leaderId instanceof Array){
                    leaderId = leaderId[1];
                }
                RestUtil.post('org/update', {orgId, leaderId}).then(() => {
                    this.$emit('complete');
                    this.close();
                });
            }
        }
    }
</script>

<style scoped>

</style>
