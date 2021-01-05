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
                    <th width="35" colspan="6">{{formatOrg(item.applyOrgId)}}</th>
                </tr>
                <tr height="65">
                    <th width="35">申请人</th>
                    <th width="30">{{item.applyName}}</th>
                    <th width="35">工龄（年）</th>
                    <th width="20">{{item.workAge}}</th>
                    <th width="35">休假标准</th>
                    <th width="55">{{handleHolidayType(item.holidayType)}}</th>
                    <th width="35">休假时间</th>
                    <th width="65">{{timeCg(item.startTime)}} ~ {{timeCg(item.endTime)}}</th>
                </tr>
                <tr height="65" v-if="levelShowMap[5]">
                    <th width="35" colspan="3">部门负责人意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">{{handleContent(levelShowMap[5])}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(levelShowMap[5])}}</em>
                    </th>
                </tr>
                <tr height="65" v-if="levelShowMap[4]">
                    <th width="35" colspan="3">单位（部门）分管领导意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">{{handleContent(levelShowMap[4])}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(levelShowMap[4])}}</em>
                    </th>
                </tr>
                <tr height="65" v-if="levelShowMap[3]">
                    <th width="35" colspan="3">单位（部门）负责人意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">{{handleContent(levelShowMap[3])}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(levelShowMap[3])}}</em>
                    </th>
                </tr>
                <tr height="65" v-if="levelShowMap[2]">
                    <th width="35" colspan="3">市局（公司）分管领导意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">{{handleContent(levelShowMap[2])}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(levelShowMap[2])}}</em>
                    </th>
                </tr>
                <tr height="65" v-if="levelShowMap[1]">
                    <th width="35" colspan="3">市局（公司）主要领导意见</th>
                    <th width="35" colspan="5" style="position: relative;">
                        <span style="color: red;font-weight: bold">{{handleContent(levelShowMap[1])}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(levelShowMap[1])}}</em>
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
    import moment from 'moment';
    import OrgUtil from '@/utils/OrgUtil';
    import CodeUtil from '@/utils/CodeUtil';
    import RestUtil from '@/utils/RestUtil';
    export default {
        name: "printLeave",
        data() {
            return {
                visible: false,
                item: {},
                levelShowMap: {
                    3: {}
                }
            }
        },
        methods: {
            open(row) {
                this.item = row;
                this.visible = true;
                RestUtil.get('oa/bill/opera/listAll', {billCode: row.billCode}).then((list) => {
                    if(list instanceof Array){
                        let stepMap = {};
                        for (let i=0; i<list.length; i++){
                            let item = list[i];
                            let step = item.billStep;
                            if(step != '00' && !stepMap[step]){
                                let t = {};
                                t.content = item.content;
                                t.approveTime = item.createTime;
                                this.levelShowMap[item.stepOrgLevel] = t;
                            }
                            stepMap[step] = step;
                        }
                    }
                });
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
            },
            handleHolidayType(type){
                return CodeUtil.getName(3, type);
            },
            handleContent(obj){
                if(obj){
                    return obj.content;
                }
                return '';
            },
            handleApproveTime(obj){
                if(obj){
                    return obj.approveTime;
                }
                return '';
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