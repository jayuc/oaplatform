<!--打印预览：调阅会计档案申请-->
<template>
    <el-dialog title="调阅会计档案申请预览"
               :visible.sync="visible"
               width="90%"
    >
        <div ref="tableContainer">
            <table border="1" cellspacing="0" align="center"
                   style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;background:#efefef;color:#333;font-size:13px;border-collapse:collapse;width:100%">
                <caption align="top"><h2 style="font-size:18px;">安徽省烟草公司合肥市公司<br/>调阅会计档案审批表</h2></caption>
                <tr height="65">
                    <th width="35">调阅单位（部门）</th>
                    <th width="35">XX部门</th>
                    <th width="35">日期</th>
                    <th width="35">XX部门</th>
                </tr>
                <tr height="120">
                    <th width="35">调阅内容</th>
                    <th width="30" colspan="3"></th>
                </tr>
                <tr height="65">
                    <th width="35">是否需复印</th>
                    <th width="35"></th>
                    <th width="35">是否需外借</th>
                    <th width="35"></th>
                </tr>
                <tr height="65">
                    <th width="35">调阅人</th>
                    <th width="35"></th>
                    <th width="35">调阅单位（部门）负责人意见</th>
                    <th width="35"></th>
                </tr>
                <tr height="65">
                    <th width="35">财务部门负责人意见</th>
                    <th width="30" colspan="3"></th>
                </tr>
                <tr height="65">
                    <th width="35">备 注</th>
                    <th width="30" colspan="3"></th>
                </tr>
            </table>
        </div>

        <div slot="footer" class="dialog-footer">
            <el-button @click="visible=false">取 消</el-button>
            <el-button type="primary" @click="print">打 印</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        name: "printLookDocMain",
        data() {
            return {
                visible: false,
                info: {}
            }
        },
        methods: {
            open(row) {
                this.info = row;
                this.visible = true;
            },
            //打印表格
            print() {
                let tableStr = this.$refs.tableContainer.innerHTML;
                let newWindow = window.open("", "打印", 'width=1500px,height=1000px');
                newWindow.document.write(tableStr);
                newWindow.document.close();
                newWindow.print();
                newWindow.close();
                this.visible = false;
            }
        }
    }
</script>

<style scoped>
    table {
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }
</style>