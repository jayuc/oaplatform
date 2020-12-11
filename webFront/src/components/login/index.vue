<template>
  <div :style="backgroudStyle">
      <div style="height: 320px;"></div>
      <div style="color: #ffffff;font-size: 30px;">合肥烟草专卖局OA系统</div>
      <div style="height: 50px;"></div>
      <div style="width: 340px;margin: 0 auto;">
          <el-form :model="ruleForm" label-width="100px" class="loginForm">
              <el-form-item label="用户名　：" >
                  <el-input type="loginName" v-model="ruleForm.loginName"></el-input>
              </el-form-item>
              <el-form-item label="确认密码：" style="color: #ffffff;">
                  <el-input type="password" v-model="ruleForm.password"></el-input>
              </el-form-item>
              <el-form-item>
                  <el-button type="success" style="width: 324px;margin-left: -80px;margin-top: 20px;" @click="submit">登 录</el-button>
              </el-form-item>
          </el-form>
      </div>
  </div>
</template>

<script>

  import RestUtil from '@/utils/RestUtil';
  import Md5Util from '@/utils/Md5Util';

  export default {
      components: {

      },
      name: 'alogin',
      data() {
          return {
              backgroudStyle: {
                  height: '100%',
                  background: '#409EFF',
//                  backgroundImage: "url(" + require("../../assets/login/dd.jpg") + ")",
//                  backgroundPosition: "center center"
              },
              ruleForm: {
                  loginName: '',
                  password: ''
              }
          }
      },
      methods: {
          submit(){
              let formData = {
                  loginName: this.ruleForm.loginName,
                  password: Md5Util.encode(this.ruleForm.password)
              };
              console.log(formData);
              RestUtil.get('/', {});
          }
      }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    .loginForm .el-form-item__label{
        color: #ffffff;
    }
</style>
