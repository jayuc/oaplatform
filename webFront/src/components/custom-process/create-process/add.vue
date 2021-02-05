<template>
    <el-dialog title="新增流程"
               :visible.sync="visible"
               width="1000px"
               top="0vh"
    >

        <el-container>

            <el-aside width="600px" style="background: #e6e6e6;height: 700px;"
                      >



            </el-aside>

            <el-main>
                <el-form :model="formData" ref="formData" :rules="rules">
                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="流程名称：" prop="processName" :label-width="formLabelWidth">
                                <el-input v-model="formData.processName" autocomplete="off"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <el-divider content-position="left">步骤</el-divider>
                <el-row>

                </el-row>
            </el-main>

        </el-container>

        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button :disabled="submitBtnDisabled" type="primary" @click="submit()">提 交</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import RestUtil from '@/utils/RestUtil';
    import TipUtil from "@/utils/TipUtil";
    import ElRow from "element-ui/packages/row/src/row";
    import ElContainer from "../../../../node_modules/element-ui/packages/container/src/main.vue";
    import ElAside from "../../../../node_modules/element-ui/packages/aside/src/main.vue";
    import ElMain from "../../../../node_modules/element-ui/packages/main/src/main.vue";
    import ElCol from "element-ui/packages/col/src/col";

    export default {
        components: {
            ElCol,
            ElMain,
            ElAside,
            ElContainer,
            ElRow
        },
        name: 'all-process-add',
        data(){
            return {
                visible: false,
                formLabelWidth: '110px',
                formData: {},
                submitBtnDisabled: false,
                rules: {
                    processName: [
                        { required: true, message: '请输入流程名称', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            submit(){
                this.$refs['formData'].validate((valid) => {
                    if (valid) {
                        this.formData.functionListJson = JSON.stringify(this.functionSelectedList);
                        RestUtil.post('', this.formData, {
                            enableLoading: true,       // 启动请求期间的正在加载
                            loadingStartFun: () => {   // 请求开始前执行
                                this.submitBtnDisabled = true;
                            },
                            loadingEndFun: () => {     // 请求开始后执行
                                this.submitBtnDisabled = false;
                            }
                        }).then((result) => {
                            if(result == 1){
                                TipUtil.success('操作成功!');
                            }else {
                                TipUtil.error('操作失败!');
                            }
                            this.$emit('complete');
                            this.close();
                        }, (error) => {
                            console.error(error);
                            TipUtil.error('请求出错，请检查您的网络是否正常');
                        });
                    } else {
                        return false;
                    }
                });
            },
            initFormData(){
                this.formData = {
                    roleName: null
                };
            },
            open(data){
                this.initFormData();
                this.visible = true;
                Object.assign(this.formData, data);
            },
            close(){
                this.visible = false;
            }
        }
    }
</script>

<style scoped>

</style>
