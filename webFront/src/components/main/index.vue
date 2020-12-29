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
                   style="overflow: hidden;"
          >
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-edit"></i>流程</template>
                  <el-menu-item index="/main/leave">年休假</el-menu-item>
                  <el-menu-item index="/main/errand">出差</el-menu-item>
                  <el-menu-item index="/main/cost">经济业务支出</el-menu-item>
                <el-menu-item index="/main/train">培训</el-menu-item>
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

    export default {
      name: 'the-main',
      data() {
          return {
              activeOpeneds: ['1'],
              mainTitle: Config.get('mainTitle'),
              userName: User.get('userName'),
          };
      },
      methods: {
          handleOpen(index, path) {
              this.activeOpeneds = path;
          },
          handleClose(index, path) {
              console.log(index, path);
          },
          selectItem(index, path){
              console.log(index, path);
          }
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
