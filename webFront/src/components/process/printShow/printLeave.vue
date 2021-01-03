<!--打印预览：年休假申请-->
<template>
    <el-dialog title="年休假申请预览"
               :visible.sync="visible"
               width="90%"
    >
        <div ref="tableContainer">
            <table border="1" cellspacing="0" align="center"
                   style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;background:#efefef;color:#333;font-size:13px;border-collapse:collapse;width:100%">
                <caption align="top"><h2 style="font-size:18px;">合肥市烟草专卖局（公司）<br/>年休假申请表</h2></caption>
                <tr height="65">
                    <th width="35" colspan="2">单位（部门）</th>
                    <th width="35" colspan="6">XX部门</th>
                </tr>
                <tr height="65">
                    <th width="35">申请人</th>
                    <th width="30">张三</th>
                    <th width="35">工龄</th>
                    <th width="20">3</th>
                    <th width="35">休假标准</th>
                    <th width="55">5天假</th>
                    <th width="35">休假时间</th>
                    <th width="65">2020-01-02~2020-01-07</th>
                </tr>
                <tr height="65">
                    <th width="35" colspan="3">部门负责人意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">同意</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月1日</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35" colspan="3">单位（部门）分管领导意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">同意</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月1日</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35" colspan="3">单位（部门）负责人意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">同意</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月1日</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35" colspan="3">市局（公司）分管领导意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">同意</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月1日</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35" colspan="3">市局（公司）主要领导意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">同意</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月1日</em>
                    </th>
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
        name: "printLeave",
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