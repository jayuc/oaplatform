<template>
    <el-dialog :title="title"
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
                        {{formData.orgName}}
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
                        <!--<el-input v-model="formData.holidayType" autocomplete="off"></el-input>-->
                        <yu-code-radio v-model="formData.holidayType"></yu-code-radio>
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
            <el-button :disabled="submitBtnDisabled" type="primary" @click="add">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import YuCodeRadio from "../public/yu-code-radio.vue";

    export default {
        components: {YuCodeRadio},
        name: 'process-add-leave',
        data(){
            return {
                visible: false,
                submitBtnDisabled: false,
                formLabelWidth: '110px',
                formData: {},
                titleMap: {
                    add: '新 增',
                    edit: '编 辑'
                },
                title: '',
                url: ''
            };
        },
        methods: {
            initFormData(){
                this.formData = {
                    type: 1,
                    currentStep: 'start'
                };
            },
            open(data, url, titleType){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
                this.url = url;
                this.title = this.titleMap[titleType];
            },
            close(){
                this.visible = false;
            },
            add(){
                this.formData.code = new Date().getTime();
                RestUtil.post(this.url, this.formData, {
                    enableLoading: true,       // 启动请求期间的正在加载
                    loadingStartFun: () => {   // 请求开始前执行
                        this.submitBtnDisabled = true;
                    },
                    loadingEndFun: () => {     // 请求开始后执行
                        this.submitBtnDisabled = false;
                    }
                }).then(() => {
                    this.$parent.submit();
                    this.close();
                });
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
