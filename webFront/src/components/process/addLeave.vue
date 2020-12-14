<template>
    <el-dialog title="新 增"
               :visible.sync="visible"
               width="700px"
    >
        <el-form :model="formData">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="申请人：" :label-width="formLabelWidth">
                        {{formData.employName}}
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="所属部门：" :label-width="formLabelWidth">
                        办公室
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="工龄：" :label-width="formLabelWidth">
                        <el-input v-model="formData.workAge" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="休假标准：" :label-width="formLabelWidth">
                        <el-input v-model="formData.holidayType" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="开始日期：" :label-width="formLabelWidth">
                        <el-date-picker
                                v-model="formData.startTime"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="结束日期：" :label-width="formLabelWidth">
                        <el-date-picker
                                v-model="formData.endTime"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="天数：" :label-width="formLabelWidth">
                        <el-input v-model="formData.days" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">

                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="add">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>

    export default {
        name: 'process-add-leave',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {}
            };
        },
        methods: {
            open(data){
                this.visible = true;
            },
            close(){
                this.visible = false;
            },
            add(){

            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
