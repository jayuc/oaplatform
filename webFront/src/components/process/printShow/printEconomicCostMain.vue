<!--打印预览：经济业务支出前申请-->
<template>
    <el-dialog title="经济业务支出前申请预览"
               :visible.sync="visible"
               width="90%"
    >
        <div ref="tableContainer">
            <table border="1" cellspacing="0" align="center"
                   style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;background:#efefef;color:#333;font-size:13px;border-collapse:collapse;width:100%">
                <caption align="top"><h2 style="font-size:18px;">合肥市局（公司）机关经济业务支出事前审批表</h2></caption>
                <tr height="65">
                    <th width="35">申请部门</th>
                    <th width="35">{{formatOrg(item.applyOrgId)}}</th>
                    <th width="35">申请人</th>
                    <th width="35" colspan="2">{{item.applyName}}</th>
                </tr>
                <tr height="65">
                    <th width="35">申请支出事项简述</th>
                    <th width="30" colspan="3">{{item.content}}</th>
                </tr>
                <tr height="65">
                    <th width="35">预计支出金额（元）</th>
                    <th width="35">{{item.amount}}</th>
                    <th width="35">有无年度预算</th>
                    <th width="35">{{formatDays(item.days)}}</th>
                </tr>
                <tr height="65">
                    <th width="35">部门负责人意见</th>
                    <th width="35" colspan="4"></th>
                </tr>
                <tr height="65">
                    <th width="35">市局（公司）分管领导意见</th>
                    <th width="35" colspan="4"></th>
                </tr>
                <tr height="65">
                    <th width="35">市局（公司）局长（经理）意见</th>
                    <th width="35" colspan="4"></th>
                </tr>
            </table>

            <table border="1" cellspacing="0" align="center"
                   style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;background:#efefef;color:#333;font-size:13px;border-collapse:collapse;width:100%">
                <caption align="top"><h2 style="font-size:18px;">直属单位（物流中心）经济业务支出事前审批表</h2></caption>
                <tr height="65">
                    <th width="35">申请部门</th>
                    <th width="35">{{formatOrg(item.applyOrgId)}}</th>
                    <th width="35">申请人</th>
                    <th width="35">{{item.applyName}}</th>
                </tr>
                <tr height="65">
                    <th width="35">事项简述</th>
                    <th width="30" colspan="3">{{item.content}}</th>
                </tr>
                <tr height="65">
                    <th width="35">预计支出金额（元）</th>
                    <th width="35">{{item.amount}}</th>
                    <th width="35">有无年度预算</th>
                    <th width="35">{{formatDays(item.days)}}</th>
                </tr>
                <tr height="65">
                    <th width="35">内设部门负责人意见</th>
                    <th width="35" colspan="5"></th>
                </tr>
                <tr height="65">
                    <th width="35">直属单位（物流中心）业务分管领导意见</th>
                    <th width="35" colspan="5"></th>
                </tr>
                <tr height="65">
                    <th width="35">直属单位（物流中心）主要负责人意见</th>
                    <th width="35" colspan="5"></th>
                </tr>
                <tr height="65">
                    <th width="35">市局（公司）分管领导意见</th>
                    <th width="35" colspan="5"></th>
                </tr>
                <tr height="65">
                    <th width="35">市局（公司）局长（经理）意见</th>
                    <th width="35" colspan="5"></th>
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
    import OrgUtil from '@/utils/OrgUtil';
    export default {
        name: "printEconomicCostMain",
        data() {
            return {
                visible: false,
                item: {}
            }
        },
        methods: {
            open(row) {
                this.item = row;
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
            },
            formatOrg(applyOrgId){
                let org = OrgUtil.getOrgById(applyOrgId);
                if(org && org.attribute){
                    return org.attribute.shortOrgName;
                }
                return '';
            },
            formatDays(type){
                if(type == 1){
                    return '有';
                }else if(type == 2){
                    return '无';
                }
                return '';
            },
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