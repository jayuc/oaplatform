<!--打印预览：市局（公司）机关会议室申请-->
<template>
    <el-dialog title="市局（公司）机关会议室申请预览"
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
                        <h2 style="font-size:18px;">合肥市烟草专卖局（公司）<br/>部门使用会议室申请表</h2>
                        <span style="position: absolute;right: 40px;top: 50px;">HFYC-JL/BG-07</span>
                    </caption>
                    <tr height="65">
                        <th width="15%">申请人单位（部门）</th>
                        <td width="35%">{{formatOrg(item.applyOrgId)}}</td>
                        <th width="15%">申请人</th>
                        <td colspan="24">{{item.applyName}}</td>
                    </tr>
                    <tr height="65">
                        <th colspan="1">使用时间</th>
                        <td colspan="23">{{formatTime(item)}}</td>
                    </tr>
                    <tr height="65">
                        <th colspan="1">参会人数</th>
                        <td colspan="1">{{item.peopleNumber}}</td>
                        <th colspan="1">选用会场类型</th>
                        <td colspan="1">{{handleHolidayType(item.holidayType)}}</td>
                    </tr>
                    <tr height="65" v-if="l35">
                        <th colspan="1">办公室（马明敏、苗倩）意见</th>
                        <td colspan="23" style="position: relative;">
                            <span style="font-weight: bold">{{handleContent(l35map)}}</span>
                            <em style="position: absolute;bottom: 5px;right: 20px;">{{handleApproveTime(l35map)}}</em>
                        </td>
                    </tr>
                    <!--<tr height="65">-->
                        <!--<th width="20" rowspan="4">会场安排需求</th>-->
                        <!--<th width="15" rowspan="2">席卡</th>-->
                        <!--<th width="35" colspan="2" style="position: relative;">-->
                            <!--<span style="position: absolute;top: 5px;left: 20px;">主席台就座人员名单及排序：</span>-->
                            <!--<em>①②③④</em>-->
                        <!--</th>-->
                    <!--</tr>-->
                    <!--<tr height="65">-->
                        <!--<th width="35" colspan="2" style="position: relative;">-->
                            <!--<span style="position: absolute;top: 5px;left: 20px;">席卡放置及其它要求：</span>-->
                            <!--<em></em>-->
                        <!--</th>-->
                    <!--</tr>-->
                    <!--<tr height="65">-->
                        <!--<th width="15">会标</th>-->
                        <!--<th width="35" colspan="2"></th>-->
                    <!--</tr>-->
                    <!--<tr height="65">-->
                        <!--<th width="15">其它需求</th>-->
                        <!--<th width="35" colspan="2"></th>-->
                    <!--</tr>-->
                    <!--<tr height="65">-->
                        <!--<th width="20">需求部门联系人</th>-->
                        <!--<th width="35">{{item.applyName}}</th>-->
                        <!--<th width="20">联系电话</th>-->
                        <!--<th width="35"></th>-->
                    <!--</tr>-->
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
    import OrgUtil from '@/utils/OrgUtil';
    import CodeUtil from '@/utils/CodeUtil';
    import RestUtil from '@/utils/RestUtil';
    import Config from '@/config';
    export default {
        name: "printMeetingMain",
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
                                }else if(item.stepOrgLevel == 0){
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
            formatTime(row){
                if(row.startTime && row.endTime){
                    let sTimes = row.startTime.split(' ');
                    let eTimes = row.endTime.split(' ');
                    if(sTimes.length > 1 && eTimes.length > 1){
                        return sTimes[0] + '　' + sTimes[1].substring(0,5) + ' - ' + eTimes[1].substring(0,5);
                    }
                }
                return '';
            },
            handleHolidayType(type){
                return CodeUtil.getName(6, type);
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