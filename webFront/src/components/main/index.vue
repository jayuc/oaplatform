<template>
  <div style="height: 100%;">
    <el-container style="border: 1px solid #eee;height: 100%;">

      <el-header style="text-align: right; font-size: 12px">
        <div style="font-size: 24px;margin-left: 10px;float: left;">{{mainTitle}}</div>
          <router-link to="/">
              <i class="el-icon-circle-close" title="退出"
                 style="margin-right: 15px;color: #ffffff;cursor: pointer;font-size: 16px;"></i>
          </router-link>
          <span>王小虎</span>
      </el-header>

      <el-container>

        <el-aside width="200px" style="">
          <el-menu :default-openeds="['1', '3']">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-message"></i>流程</template>
              <el-menu-item-group>
                  <router-link to="/main/leave">
                      <el-menu-item index="1-1">请假</el-menu-item>
                  </router-link>
                  <router-link to="/main/errand">
                      <el-menu-item index="1-2">出差</el-menu-item>
                  </router-link>
              </el-menu-item-group>
              <el-menu-item-group title="分组2">
                <el-menu-item index="1-3">选项3</el-menu-item>
              </el-menu-item-group>
              <el-submenu index="1-4">
                <template slot="title">选项4</template>
                <el-menu-item index="1-4-1">选项4-1</el-menu-item>
              </el-submenu>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-menu"></i>导航二</template>
              <el-menu-item-group>
                <template slot="title">分组一</template>
                <el-menu-item index="2-1">选项1</el-menu-item>
                <el-menu-item index="2-2">选项2</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group title="分组2">
                <el-menu-item index="2-3">选项3</el-menu-item>
              </el-menu-item-group>
              <el-submenu index="2-4">
                <template slot="title">选项4</template>
                <el-menu-item index="2-4-1">选项4-1</el-menu-item>
              </el-submenu>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-main style="background-color: rgb(238, 241, 246);">
           <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>
    import Config from '@/config';

    export default {
      name: 'the-main',
      data() {
          return {
              mainTitle: Config.get('mainTitle')
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
