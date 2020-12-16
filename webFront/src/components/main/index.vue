<template>
  <div style="height: 100%;">
    <el-container style="border: 1px solid #eee;height: 100%;">

      <el-header style="text-align: right; font-size: 12px">
        <div style="font-size: 24px;margin-left: 10px;float: left;">{{mainTitle}}</div>
          <router-link to="/">
              <i class="el-icon-circle-close" title="退出"
                 style="margin-right: 15px;color: #ffffff;cursor: pointer;font-size: 16px;"></i>
          </router-link>
          <span>{{userName}}</span>
      </el-header>

      <el-container>

        <el-aside width="200px" style="">
          <el-menu :default-openeds="['1']">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-edit"></i>流程</template>
              <router-link to="/main/leave">
                  <el-menu-item>年休假</el-menu-item>
              </router-link>
              <router-link to="/main/errand">
                  <el-menu-item>出差</el-menu-item>
              </router-link>
            </el-submenu>
              <el-submenu index="2">
                  <template slot="title"><i class="el-icon-notebook-2"></i>组织机构</template>
                  <router-link to="/main/userManage">
                      <el-menu-item>用户管理</el-menu-item>
                  </router-link>
              </el-submenu>
            <el-submenu index="3">
                <template slot="title"><i class="el-icon-setting"></i>系统管理</template>
                <router-link to="/main/errand">
                    <el-menu-item>修改密码</el-menu-item>
                </router-link>
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
              mainTitle: Config.get('mainTitle'),
              userName: User.get('userName')
          };
      },
      methods: {

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
