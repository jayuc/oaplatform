<template>
  <div style="height: 100%;">
    <el-container style="border: 1px solid #eee;height: 100%;">

      <el-header style="text-align: right; font-size: 12px">
        <div style="font-size: 24px;margin-left: 10px;float: left;">{{mainTitle}}</div>
          <router-link to="/">
              <a style="color: #ffffff;margin-right: 20px;">退出</a>
          </router-link>
          <span>{{userName}}</span>
      </el-header>

      <el-container>

        <el-aside width="180px">
          <el-menu :uniqueOpened="true"
                   :default-openeds="activeOpeneds"
                   @open="handleOpen"
                   @close="handleClose"
                   @select="selectItem"
                   :router="true"
                   ref="global_menu"
                   style="overflow: hidden;"
          >
              <el-menu-item index="/main/main" style="font-weight: bold;">
                  <i class="el-icon-house"></i>
                  <span slot="title">首页</span>
              </el-menu-item>
            <el-submenu index="1">
              <template slot="title" style="font-weight: bold;"><i class="el-icon-edit"></i>流程</template>
                <el-menu-item index="/main/errand">出差申请</el-menu-item>
                <el-menu-item index="/main/leave">年休假申请</el-menu-item>
                <el-menu-item index="/main/cost">经济业务支出事前申请</el-menu-item>
                <el-menu-item index="/main/train">培训申请</el-menu-item>
                <el-menu-item style="padding-left: 10px;" v-if="userYesOffice == 1" index="/main/meeting">市局（公司）机关会议室申请</el-menu-item>
                <el-menu-item index="/main/travelToolSet">出差交通工具调整申请</el-menu-item>
                <el-menu-item v-if="false" index="/main/goAbroad">员工因私出国（境）申请</el-menu-item>
                <el-menu-item style="padding-left: 10px;" index="/main/introduce">市局（公司）介绍信开具申请</el-menu-item>
                <el-menu-item style="padding-left: 0px;" index="/main/certificate">市局（公司）证件（复印件）使用申请</el-menu-item>
                <el-menu-item index="/main/lookDoc">调阅会计档案申请</el-menu-item>
            </el-submenu>
              <el-submenu index="2" v-if="userName == '超级管理员'">
                  <template slot="title"><i class="el-icon-notebook-2"></i>组织机构</template>
                      <el-menu-item index="/main/orgManage">机构管理</el-menu-item>
                      <el-menu-item index="/main/userManage">用户管理</el-menu-item>
              </el-submenu>
            <el-submenu index="3">
                <template slot="title"><i class="el-icon-setting"></i>系统管理</template>
                    <el-menu-item index="/main/setPassword">修改密码</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-main style="background-color: rgb(238, 241, 246);">
            <div style="background: #ffffff;height: 100%;" id="main-index-body">
                <router-view></router-view>
            </div>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>
    import Config from '@/config';
    import User from '@/user';
    import ElSubmenu from "../../../node_modules/element-ui/packages/menu/src/submenu.vue";

    export default {
        components: {ElSubmenu},
        name: 'the-main',
        data() {
            return {
                activeOpeneds: [],
                mainTitle: Config.get('mainTitle'),
                userName: User.get('userName'),
                userYesOffice: User.get('yesOffice')
            };
        },
        methods: {
            handleOpen(index, path) {
                console.log(index, path);
            },
            handleClose(index, path) {
                console.log(index, path);
            },
            selectItem(index, path){
                console.log(index, path);
            }
        },
        mounted(){
            this.$router.push("/main/main");
            Config.set('global_menu', this.$refs.global_menu);
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
</style>
