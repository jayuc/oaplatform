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
                    <th width="15%">申请人单位（部门）</th>
                    <td width="40%">{{formatOrg(item.applyOrgId)}}</td>
                    <th width="15%">申请人</th>
                    <td colspan="24">{{item.applyName}}</td>
                </tr>
                <tr height="65">
                    <th colspan="1">工龄（年）</th>
                    <td colspan="1">{{item.workAge}}</td>
                    <th colspan="1">休假标准</th>
                    <td colspan="1">{{handleHolidayType(item.holidayType)}}</td>
                </tr>
                <tr height="65">
                    <th colspan="1">休假时间</th>
                    <td colspan="1">{{timeCg(item.startTime)}} ~ {{timeCg(item.endTime)}}</td>
                    <th colspan="1">休假天数</th>
                    <td colspan="1">{{item.days}}天</td>
                </tr>
                <tr height="65" v-if="l5">
                    <th colspan="1">部门负责人意见</th>
                    <td colspan="23" style="position: relative;">
                        <span style="font-weight: bold">{{handleContent(l5map)}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(l5map)}}</em>
                    </td>
                </tr>
                <tr height="65" v-if="l4">
                    <th colspan="1">单位（部门）分管领导意见</th>
                    <td colspan="23" style="position: relative;">
                        <span style="font-weight: bold">{{handleContent(l4map)}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(l4map)}}</em>
                    </td>
                </tr>
                <tr height="65" v-if="l3">
                    <th colspan="1">单位（部门）负责人意见</th>
                    <td colspan="23" style="position: relative;">
                        <span style="font-weight: bold">{{handleContent(l3map)}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(l3map)}}</em>
                    </td>
                </tr>
                <tr height="65" v-if="l2">
                    <th colspan="1">市局（公司）分管领导意见</th>
                    <td colspan="23" style="position: relative;">
                        <span style="font-weight: bold">{{handleContent(l2map)}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(l2map)}}</em>
                    </td>
                </tr>
                <tr height="65" v-if="l1">
                    <th colspan="1">市局（公司）主要领导意见</th>
                    <td colspan="23" style="position: relative;">
                        <span style="font-weight: bold">{{handleContent(l1map)}}</span>
                        <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(l1map)}}</em>
                    </td>
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
                l5: false,
                l4: false,
                l3: false,
                l2: false,
                l1: false,
                l5map: {},
                l4map: {},
                l3map: {},
                l2map: {},
                l1map: {}
            }
        },
        methods: {
            open(row) {
                this.item = row;
                this.visible = true;
                RestUtil.get('oa/bill/opera/listAll',
                    {billCode: row.billCode}).then((list) => {
                    if(list instanceof Array){
                        let stepMap = {};
                        for (let i=0; i<list.length; i++){
                            let item = list[i];
                            let step = item.billStep;
                            if(step != '00' && !stepMap[step]){
                                if(item.stepOrgLevel == 1){
                                    this.l1 = true;
                                    this.l1map = item;
                                }else if(item.stepOrgLevel == 2){
                                    this.l2 = true;
                                    this.l2map = item;
                                }else if(item.stepOrgLevel == 3){
                                    this.l3 = true;
                                    this.l3map = item;
                                }else if(item.stepOrgLevel == 4){
                                    this.l4 = true;
                                    this.l4map = item;
                                }else if(item.stepOrgLevel == 5){
                                    this.l5 = true;
                                    this.l5map = item;
                                }
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
                    return obj.operaName + '　' + obj.createTime;
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