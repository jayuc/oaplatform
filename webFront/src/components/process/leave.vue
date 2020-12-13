<template>
  <div style="height: 100%;" ref="pageTableContainer">
      <div style="height:20px;"></div>
      <div class="form-search-container">
          <span class="form-search-title">查询条件</span>
          <el-form :inline="true" :model="formData" class="demo-form-inline">
              <el-form-item label="结束：">
                  <el-radio v-model="stopFlag" label="0">全部</el-radio>
                  <el-radio v-model="stopFlag" label="1">已结束</el-radio>
                  <el-radio v-model="stopFlag" label="2">未结束</el-radio>
              </el-form-item>
              <el-form-item style="margin-left: 10px;">
                  <el-button type="primary" @click="submit" :disabled="searchBtnStatus">查 询</el-button>
              </el-form-item>
              <el-form-item style="margin-left: 10px;">
                  <el-button type="success" @click="addDialogVisible = true" :disabled="searchBtnStatus">新 增</el-button>
              </el-form-item>
          </el-form>
      </div>
      <div class="form-table-container" :style="tableContainerStyle">
          <el-table
                  ref="elTable"
                  :data="tableData"
                  border
                  :height="tableHeight"
                  header-row-class-name="form-table-header"
                  header-cell-class-name="form-table-header-cell"
                  style="width: 100%">
              <el-table-column
                      prop="employName"
                      label="申请人"
                      align="center"
                      width="180">
              </el-table-column>
              <el-table-column
                      prop="orgName"
                      label="申请人单位"
                      align="center"
                      width="180">
              </el-table-column>
              <el-table-column
                      prop="workAge"
                      align="center"
                      width="180"
                      label="工龄">
              </el-table-column>
              <el-table-column
                      prop="holidayType"
                      align="center"
                      width="180"
                      label="休假标准">
              </el-table-column>
              <el-table-column
                      prop="startTime"
                      align="center"
                      width="180"
                      :formatter="handleDate"
                      label="开始日期">
              </el-table-column>
              <el-table-column
                      prop="endTime"
                      align="center"
                      width="180"
                      :formatter="handleDate"
                      label="结束日期">
              </el-table-column>
              <el-table-column
                      prop="days"
                      align="center"
                      width="180"
                      label="天数">
              </el-table-column>
              <el-table-column
                      prop="operation"
                      align="center"
                      label="操作">
                  <template slot-scope="scope">
                      <el-button @click="showBill(scope.row)" type="text" size="small">查看</el-button>
                      <el-button v-if="cid != -1 && cid != 1" @click="approveBill(scope.row)" type="text" size="small">审批</el-button>
                  </template>
              </el-table-column>
          </el-table>
          <div style="height: 10px;"></div>
          <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="formData.pageNumber"
                  :page-sizes="[15, 30, 45, 60]"
                  :page-size="formData.pageSize"
                  layout="prev, pager, next, jumper, total, sizes"
                  :total="total">
          </el-pagination>
      </div>

      <el-dialog title="新增"
                 :visible.sync="addDialogVisible"
                 width="700px"
      >
          <el-form :model="addForm">
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="申请人：" :label-width="formLabelWidth">
                          {{userName}}
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="所属部门：" :label-width="formLabelWidth">
                          办公室
                      </el-form-item>
                  </el-col>
              </el-row>
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="工龄：" :label-width="formLabelWidth">
                          <el-input v-model="addForm.workAge" autocomplete="off"></el-input>
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="休假标准：" :label-width="formLabelWidth">
                          <el-input v-model="addForm.holidayType" autocomplete="off"></el-input>
                      </el-form-item>
                  </el-col>
              </el-row>
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="开始日期：" :label-width="formLabelWidth">
                          <el-date-picker
                                  v-model="addForm.startTime"
                                  type="date"
                                  placeholder="选择日期">
                          </el-date-picker>
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="结束日期：" :label-width="formLabelWidth">
                          <el-date-picker
                                  v-model="addForm.endTime"
                                  type="date"
                                  placeholder="选择日期">
                          </el-date-picker>
                      </el-form-item>
                  </el-col>
              </el-row>
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="天数：" :label-width="formLabelWidth">
                          <el-input v-model="addForm.days" autocomplete="off"></el-input>
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">

                  </el-col>
              </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="addDialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="add">确 定</el-button>
          </div>
      </el-dialog>

      <el-dialog title="审批"
                 :visible.sync="approveDialogVisible"
                 width="700px"
      >
          <el-form :model="addForm">
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="申请人：" :label-width="formLabelWidth">
                          {{approveForm.employName}}
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="所属部门：" :label-width="formLabelWidth">
                          {{approveForm.orgName}}
                      </el-form-item>
                  </el-col>
              </el-row>
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="工龄：" :label-width="formLabelWidth">
                          {{approveForm.workAge}}
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="休假标准：" :label-width="formLabelWidth">
                          {{approveForm.holidayType}}
                      </el-form-item>
                  </el-col>
              </el-row>
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="开始日期：" :label-width="formLabelWidth">
                          {{approveForm.startTime ? approveForm.startTime.split('T')[0] : ''}}
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="结束日期：" :label-width="formLabelWidth">
                          {{approveForm.endTime ? approveForm.endTime.split('T')[0] : ''}}
                      </el-form-item>
                  </el-col>
              </el-row>
              <el-row>
                  <el-col :span="12">
                      <el-form-item label="天数：" :label-width="formLabelWidth">
                          {{approveForm.days}}
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">

                  </el-col>
              </el-row>
              <el-row>
                  <el-col :span="24">
                      <el-form-item label="审批意见：" :label-width="formLabelWidth">
                          <el-input v-model="approveForm.content" autocomplete="off"></el-input>
                      </el-form-item>
                  </el-col>
              </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="approveDialogVisible = false">不同意</el-button>
              <el-button type="primary" @click="approve">同 意</el-button>
          </div>
      </el-dialog>

  </div>
</template>

<script>
    import RestUtil from '@/utils/RestUtil';
    import TipUtil from '@/utils/TipUtil';
    import Config from '@/config';
    import user from '@/user';

    function stringToDate(str){

        var tempStrs = str.split(" ");

        var dateStrs = tempStrs[0].split("-");

        var year = parseInt(dateStrs[0], 10);

        var month = parseInt(dateStrs[1], 10) - 1;

        var day = parseInt(dateStrs[2], 10);

        var timeStrs = tempStrs[1].split(":");

        var hour = parseInt(timeStrs [0], 10);

        var minute = parseInt(timeStrs[1], 10);

        var second = parseInt(timeStrs[2], 10);

        var date = new Date(year, month, day, hour, minute, second);

        return date;

    }

    export default {
        name: 'user-setting',
        data(){
            return {
                tableContainerStyle: '',
                tableHeight: 500,
                formData: {
                    pageNumber: 1,
                    pageSize: 15,
                },
                addForm: {
                    type: 1,
                    currentStep: user.get('id') + 1
                },
                approveForm: {},
                formLabelWidth: '110px',
                stopFlag: '0',
                searchBtnStatus: false,
                addDialogVisible: false,
                approveDialogVisible: false,
                total: 0,
                userName: user.get('name'),
                cid: user.get('id'),
                tableData: []
            }
        },
        methods: {
            submit(){
                RestUtil.get('oa/bill/list', this.formData, {
                    enableLoading: true,       // 启动请求期间的正在加载
                    loadingStartFun: () => {   // 请求开始前执行
                        this.searchBtnStatus = true;
                    },
                    loadingEndFun: () => {     // 请求开始后执行
                        this.searchBtnStatus = false;
                    }
                }).then((result) => {
                    let data = result.result;
                    let error = result.error;
                    if(error){
                      TipUtil.error(error);
                      return;
                    }
                    if(data){
                      this.tableData = data.rows;
                      this.total = data.total;
                    }
                }, () => {

                });
            },
            add(){
                this.addDialogVisible = false;
                this.addForm.code = new Date().getTime();
                RestUtil.post('oa/bill/insert', this.addForm).then(() => {
                    this.submit();
                });
            },
            approve(){
                console.log(this.approveForm);
                let obj = {};
                Object.assign(obj, this.approveForm);
                obj.currentStep = parseInt(obj.currentStep) + 1;
                let s = obj.startTime;
                let a = s.split('T');
                console.log(obj.endTime);
                console.log(typeof s);
                obj.startTime = stringToDate(a[0] + ' 00:00:00');
                obj.endTime = stringToDate(obj.endTime.split('T')[0] + ' 00:00:00');
                obj.createTime = new Date();
                obj.updateTime = new Date();
                RestUtil.post('oa/bill/update', obj).then(() => {
                    this.submit();
                    this.approveDialogVisible = false;
                });
            },
            showBill(row){
                console.log(row);
            },
            approveBill(row){
                console.log(row);
                this.approveForm = row;
                this.approveDialogVisible = true;
            },
            handleDate(a, b, c){
                console.log(a);
                console.log(b);
                console.log(c);
                return c.split('T')[0];
            },
            handleSizeChange(currentPageSize) {
                this.formData.pageSize = currentPageSize;
                this.submit();
            },
            handleCurrentChange(currentPageNumber) {
                this.formData.pageNumber = currentPageNumber;
                this.submit();
            },
            afterCreated(){
                let pageHeight = Config.get('mainBodyHeight');
                let tableContainerHeight = pageHeight - 144;
                this.tableContainerStyle = 'height:' + tableContainerHeight + 'px';
                this.tableHeight = tableContainerHeight - 32;
            }
        },
        created(){
            this.afterCreated();
        },
        mounted(){
            this.submit();
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
