<!--打印预览：市局（公司）机关会议室申请-->
<template>
    <el-dialog title="市局（公司）机关会议室申请预览"
               :visible.sync="visible"
               width="90%"
    >
        <div ref="tableContainer">
            <table border="1" cellspacing="0" align="center"
                   style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;background:#efefef;color:#333;font-size:13px;border-collapse:collapse;width:100%">
                <caption align="top"><h2 style="font-size:18px;">部门使用会议室需求表</h2></caption>
                <tr height="65">
                    <th width="20">使用部门</th>
                    <th width="35" colspan="3">XX部门</th>
                </tr>
                <tr height="65">
                    <th width="20">使用时间</th>
                    <th width="35" colspan="3">XX部门</th>
                </tr>
                <tr height="65">
                    <th width="20">参会人数</th>
                    <th width="35" colspan="3">XX部门</th>
                </tr>
                <tr height="65">
                    <th width="20">选用会场类型</th>
                    <th width="30" colspan="3">2302会议室</th>
                </tr>
                <tr height="65">
                    <th width="20" rowspan="4">会场安排需求</th>
                    <th width="15" rowspan="2">席卡</th>
                    <th width="35" colspan="2" style="position: relative;">
                        <span style="position: absolute;top: 5px;left: 20px;">主席台就座人员名单及排序：</span>
                        <em>①②③④</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35" colspan="2" style="position: relative;">
                        <span style="position: absolute;top: 5px;left: 20px;">席卡放置及其它要求：</span>
                        <em></em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="15">会标</th>
                    <th width="35" colspan="2"></th>
                </tr>
                <tr height="65">
                    <th width="15">其它需求</th>
                    <th width="35" colspan="2"></th>
                </tr>
                <tr height="65">
                    <th width="20">需求部门联系人</th>
                    <th width="35">张三</th>
                    <th width="20">联系电话</th>
                    <th width="35">13611121235</th>
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
        name: "printMeetingMain",
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