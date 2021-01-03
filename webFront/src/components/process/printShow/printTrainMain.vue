<!--打印预览：培训申请-->
<template>
    <el-dialog title="培训申请预览"
               :visible.sync="visible"
               width="90%"
    >
        <div ref="tableContainer">
            <table border="1" cellspacing="0" align="center"
                   style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;background:#efefef;color:#333;font-size:13px;border-collapse:collapse;width:100%">
                <caption align="top"><h2 style="font-size:18px;">合肥市烟草专卖局（公司）<br/>培 训 申 请 表</h2></caption>
                <tr height="65">
                    <th width="35">申请单位（部门）</th>
                    <th width="35" colspan="3" style="position: relative;">
                        <span>{{formatOrg(item.applyOrgId)}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">申请人：{{item.applyName}}</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35">培训内容</th>
                    <th width="30" colspan="3">{{item.content}}</th>
                </tr>
                <tr height="65">
                    <th width="35">培训对象</th>
                    <th width="30" colspan="3">{{item.peopleList}}</th>
                </tr>
                <tr height="65">
                    <th width="35">培训时间</th>
                    <th width="30">{{timeCg(item.startTime)}} 至 {{timeCg(item.endTime)}}</th>
                    <th width="35">计划课时</th>
                    <th width="30">{{item.peopleNumber}}</th>
                </tr>
                <tr height="65">
                    <th width="35">培训师资</th>
                    <th width="30">{{item.address}}</th>
                    <th width="35">预计费用（含所有费用）</th>
                    <th width="30">{{item.amount}}元</th>
                </tr>
                <tr height="65">
                    <th width="35">单位（部门）负责人意见</th>
                    <th width="30" colspan="3" style="position: relative;">
                        <span style="color: red;font-weight: bold">同意</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">2020年1月4日</em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35">教育培训办公室意见</th>
                    <th width="30" colspan="3" style="position: relative;">
                        <span style="color: red;font-weight: bold"></span>
                        <em style="position: absolute;bottom: 5px;right: 20px;"></em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35">分管领导意见</th>
                    <th width="30" colspan="3" style="position: relative;">
                        <span style="color: red;font-weight: bold"></span>
                        <em style="position: absolute;bottom: 5px;right: 20px;"></em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35">局长（经理）意见</th>
                    <th width="30" colspan="3" style="position: relative;">
                        <span style="color: red;font-weight: bold"></span>
                        <em style="position: absolute;bottom: 5px;right: 20px;"></em>
                    </th>
                </tr>
                <tr height="65">
                    <th width="35">备注</th>
                    <th width="30" colspan="3">{{item.mark}}</th>
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
    import moment from 'moment';
    import OrgUtil from '@/utils/OrgUtil';
    export default {
        name: "printTrainMain",
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
            timeCg(time) {
                return moment(time).format('YYYY年MM月DD日');
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