<!--打印预览：出差申请-->
<template>
    <el-dialog title="出差申请预览"
               :visible.sync="visible"
               width="90%"
    >
        <div ref="tableContainer">
            <table border="1" cellspacing="0" align="center"
                   style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;background:#efefef;color:#333;font-size:13px;border-collapse:collapse;width:100%">
                <caption align="top"><h2 style="font-size:18px;">出差审批单</h2></caption>
                <tr height="65">
                    <th width="35">申请单位（部门）</th>
                    <th width="35" colspan="2">XX部门</th>
                </tr>
                <tr height="65">
                    <th width="35">出差事由</th>
                    <th width="30" colspan="2"></th>
                </tr>
                <tr height="65">
                    <th width="35">出差人员及人数</th>
                    <th width="35" colspan="2" style="position: relative;">
                        <span style="color: red;font-weight: bold">张三、李四</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">共计_2_人</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35">出差目的地</th>
                    <th width="30" colspan="2"></th>
                </tr>
                <tr height="65">
                    <th width="35">预计出差时间</th>
                    <th width="30" colspan="2">自2021年1月4日 至 2021年1月8日</th>
                </tr>
                <tr height="95">
                    <th width="35" style="position: relative;">
                        <span style="position: absolute;top: 5px;left: 20px;">申请人签字：</span>
                        <i>张三</i>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月1日</em>
                    </th>
                    <th width="35" style="position: relative;">
                        <span style="position: absolute;top: 5px;left: 20px;">单位（部门）审批：</span>
                        <i>张三</i>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月1日</em>
                    </th>
                    <th width="35" style="position: relative;">
                        <span style="position: absolute;top: 5px;left: 20px;">市局（公司）领导审批：</span>
                        <i>张三</i>
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
        name: "printBusinessTripMain",
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