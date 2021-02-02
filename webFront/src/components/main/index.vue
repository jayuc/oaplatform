<template>
  <div style="height: 100%;">
    <el-container style="border: 1px solid #eee;height: 100%;">

      <el-header style="text-align: right; font-size: 12px">
        <div style="font-size: 24px;margin-left: 10px;float: left;">{{mainTitle}}</div>
          <span style="cursor: pointer;position: relative;"
                @mouseleave="showOut = false"
                @mouseenter="showOut = true">
              <span style="font-size: 15px;margin-right: 4px;">{{userName}}</span>
              <span>
                  <i class="el-icon-arrow-down" style="font-size: 14px;"></i>
              </span>
              <div v-if="showOut" style="position: absolute;top: 0px;right: 0px;width: 100px;z-index: 100;">
                  <div style="height: 20px;"></div>
                  <el-card class="box-card"
                           body-style="padding: 10px 0px;line-height: 32px;font-size: 14px;text-align: left;">
                      <div class="card-item" @click="openDialog('userInfo')">个人资料</div>
                      <div class="card-item" @click="openDialog('passwordSetting')">修改密码</div>
                      <div class="card-item" @click="loginOut">退出</div>
                  </el-card>
              </div>
          </span>
      </el-header>

      <el-container>

        <el-aside width="180px">
          <el-menu :uniqueOpened="true"
                   :default-openeds="activeOpeneds"
                   @open="handleOpen"
                   @close="handleClose"
                   @select="selectItem"
                   :router="true"
                   :defaultActive="activeNode"
                   ref="global_menu"
                   style="overflow: hidden;"
          >
              <el-menu-item index="/main/main" style="font-weight: bold;">
                  <i class="el-icon-house"></i>
                  <span slot="title">首页</span>
              </el-menu-item>
              <el-submenu v-for="item in menuData" v-bind:key="item.functionId" :index="item.attribute.menuPath">
                  <template slot="title"><i :class="item.attribute.menuImage"></i>{{item.functionName}}</template>
                  <el-menu-item v-for="it in item.children"
                                v-bind:key="it.functionId"
                                :index="it.attribute.menuPath">
                      {{it.functionName}}
                  </el-menu-item>
              </el-submenu>
            <!--<el-submenu index="1">-->
              <!--<template slot="title" style="font-weight: bold;"><i class="el-icon-edit"></i>流程</template>-->
                <!--<el-menu-item index="/main/errand">出差申请</el-menu-item>-->
                <!--<el-menu-item index="/main/leave">年休假申请</el-menu-item>-->
                <!--<el-menu-item index="/main/cost">经济业务支出事前申请</el-menu-item>-->
                <!--<el-menu-item index="/main/train">培训申请</el-menu-item>-->
                <!--<el-menu-item v-if="userYesOffice == 1" index="/main/meeting">机关会议室申请</el-menu-item>-->
                <!--<el-menu-item index="/main/travelToolSet">出差交通工具调整申请</el-menu-item>-->
                <!--<el-menu-item v-if="false" index="/main/goAbroad">员工因私出国（境）申请</el-menu-item>-->
                <!--<el-menu-item index="/main/introduce">介绍信开具申请</el-menu-item>-->
                <!--<el-menu-item index="/main/certificate">证件（复印件）使用申请</el-menu-item>-->
                <!--<el-menu-item index="/main/lookDoc">调阅会计档案申请</el-menu-item>-->
            <!--</el-submenu>-->
              <!--<el-submenu index="2" v-if="userName == '超级管理员'">-->
                  <!--<template slot="title"><i class="el-icon-notebook-2"></i>组织机构</template>-->
                      <!--<el-menu-item index="/main/orgManage">机构管理</el-menu-item>-->
                      <!--<el-menu-item index="/main/userManage">用户管理</el-menu-item>-->
                      <!--<el-menu-item index="/main/roleManage">角色管理</el-menu-item>-->
              <!--</el-submenu>-->
            <!--<el-submenu index="3">-->
                <!--<template slot="title"><i class="el-icon-setting"></i>系统管理</template>-->
                    <!--<el-menu-item index="/main/setPassword">修改密码</el-menu-item>-->
                    <!--<el-menu-item v-if="userName == '超级管理员'" index="/main/billOperaLog">工单操作日志</el-menu-item>-->
            <!--</el-submenu>-->
          </el-menu>
        </el-aside>

        <el-main style="background-color: rgb(238, 241, 246);">
            <div style="background: #ffffff;height: 100%;" id="main-index-body">
                <router-view @openMenu="selectMenu"></router-view>
            </div>
        </el-main>

      </el-container>
    </el-container>

      <password-setting ref="passwordSetting" />
      <user-info ref="userInfo" />
  </div>
</template>

<script>
    import Config from '@/config';
    import User from '@/user';
    import MenuUtil from '@/utils/MenuUtil';
    import PasswordSetting from './passwordSetting.vue';
    import UserInfo from './user-details.vue';

    export default {
        components: {
            PasswordSetting,
            UserInfo
        },
        name: 'the-main',
        data() {
            return {
                activeOpeneds: [],
                activeNode: null,
                showOut: false,
                mainTitle: Config.get('mainTitle'),
                userName: User.get('userName'),
                userYesOffice: User.get('yesOffice'),
                menuData: []
            };
        },
        methods: {
            loginOut(){
                this.$router.push("/");
            },
            handleOpen(index, path) {
                console.log(index, path);
            },
            handleClose(index, path) {
                console.log(index, path);
            },
            selectItem(index, path){
                console.log(index, path);
            },
            selectMenu(index){
                this.activeNode = index;
            },
            openDialog(name){
                this.$refs[name].open();
            }
        },
        mounted(){
            this.menuData = MenuUtil.getTree();
            this.$router.push("/main/main");
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .el-header {
    background-color: #409EFF;
    color: #ffffff;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
  .card-item{
      padding-left: 12px;
  }
  .card-item:hover{
      background: rgb(217, 236, 255);
  }
</style>
