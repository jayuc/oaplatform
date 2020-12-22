<template>
    <el-dialog
            title="设置科级干部"
            :visible.sync="visible"
            width="500px">
        <el-form :model="formData" ref="formData">
            <el-row>
                <el-col :span="24">
                    <el-form-item label="是否是科级干部：" :label-width="formLabelWidth">
                        <el-radio v-model="formData.yesChief" :label="1">是</el-radio>
                        <el-radio v-model="formData.yesChief" :label="0">否</el-radio>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="submit">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';

    export default {
        name: 'user-set-chief',
        data(){
            return {
                visible: false,
                formLabelWidth: '150px',
                formData: {
                    yesChief: 0
                }
            }
        },
        methods: {
            open(data){
                this.visible = true;
                Object.assign(this.formData, data);
            },
            close(){
                this.visible = false;
            },
            submit(){
                RestUtil.post('user/update', this.formData).then(() => {
                    this.$emit('complete');
                    this.close();
                });
            }
        }
    }
</script>

<style scoped>

</style>
