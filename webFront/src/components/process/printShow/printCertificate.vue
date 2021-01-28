<!--打印预览：调阅会计档案申请-->
<template>
    <el-dialog title="调市局（公司）证件（复印件）使用申请预览"
               :visible.sync="visible"
               width="90%"
    >
        <div ref="tableContainer">
            <div id="tableContainer" style="position: relative;height: 600px;">
                <img id="image" style="display: none"
                     :src="waterUrl"/>
                <table border="1" cellspacing="0" align="center"
                       style="font-family:微软雅黑;text-align:center;word-wrap:break-word;word-break:break-all;margin-top: 50px;
                       color:#333;font-size:13px;border-collapse:collapse;width:100%;position: absolute;left: 0;top: 0;z-index: 100;">
                    <caption align="top" style="position: relative;">
                        <h2 style="font-size:18px;">合肥市烟草专卖局（公司）<br/>市局（公司）证件（复印件）使用申请表</h2>
                        <span style="position: absolute;right: 40px;top: 50px;"></span>
                    </caption>
                    <tr height="65">
                        <th width="15%">申请人单位（部门）</th>
                        <td width="35%">{{formatOrg(item.applyOrgId)}}</td>
                        <th width="15%">申请人</th>
                        <td colspan="24">{{item.applyName}}</td>
                    </tr>
                    <tr height="65">
                        <th colspan="1">使用证件</th>
                        <td colspan="1">{{item.address}}</td>
                        <th colspan="1">使用用途</th>
                        <td colspan="1">{{item.content}}</td>
                    </tr>
                    <tr height="65">
                        <th colspan="1">是否需复印</th>
                        <td colspan="1">{{formatYesFlag(item.holidayType)}}</td>
                        <th colspan="1">是否需外借</th>
                        <td colspan="1">{{formatYesFlag(item.days)}}</td>
                    </tr>
                    <tr height="65">
                        <th colspan="1">日期</th>
                        <td colspan="23">{{formatTime(item.createTime)}}</td>
                    </tr>
                    <tr height="65">
                        <th colspan="1">备 注</th>
                        <th colspan="23">{{item.mark}}</th>
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
                    <tr height="65" v-if="l35">
                        <th colspan="1">市局办公室主任意见</th>
                        <td colspan="23" style="position: relative;">
                            <span style="font-weight: bold">{{handleContent(l35map)}}</span>
                            <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(l35map)}}</em>
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
    import RestUtil from '@/utils/RestUtil';
    import Config from '@/config';
    export default {
        name: "printLookDocMain",
        data() {
            return {
                waterUrl: Config.get('waterUrl'),
                visible: false,
                item: {},
                l5: false,
                l4: false,
                l3: false,
                l35: false,
                l2: false,
                l1: false,
                l5map: {},
                l4map: {},
                l3map: {},
                l35map: {},
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
                                }else if(item.stepOrgLevel == 3.5){
                                    this.l35 = true;
                                    this.l35map = item;
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
                let img = newWindow.document.getElementById('image');
                let tableContainer = newWindow.document.getElementById('tableContainer');
                img.style.display = 'block';
                img.style.width = '100%';
                img.style.height = '1000px';
                tableContainer.style.height = '1000px';
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
            formatTime(time){
                if(time){
                    return time.split(' ')[0];
                }
                return '';
            },
            formatYesFlag(val){
                if(val === 1){
                    return '是';
                }else if(val === 2){
                    return '否';
                }
                return '';
            },
            timeCg(time) {
                return moment(time).format('YYYY年MM月DD日');
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