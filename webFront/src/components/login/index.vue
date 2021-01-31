<template>
  <div :style="backgroudStyle">
      <div style="height: 25%;"></div>
      <div>
          <img  style="width: 100px"
                :src="logoUrl"/>
      </div>
      <div style="height: 26px;"></div>
      <div style="color: #ffffff;font-size: 30px;">{{loginTitle}}</div>
      <div style="height: 40px;"></div>
      <div style="width: 340px;margin: 0 auto;">
          <el-form :model="ruleForm" label-width="100px" class="loginForm" @keyup.enter.native="submit">
              <el-form-item label="用户名：" >
                  <el-input type="loginName" v-model="ruleForm.loginName"></el-input>
              </el-form-item>
              <el-form-item label="密码：" style="color: #ffffff;">
                  <el-input type="password" v-model="ruleForm.password" show-password></el-input>
              </el-form-item>
              <el-form-item>
                  <el-button type="success" :disabled="loading"
                             style="width: 324px;margin-left: -80px;margin-top: 20px;" @click="submit">登 录</el-button>
              </el-form-item>
          </el-form>
      </div>
      <div style="height: 100px;"></div>
      <div style="display: none;">技术支持：安徽天安芯科物联网技术有限公司</div>
  </div>
</template>

<script>

  import RestUtil from '@/utils/RestUtil';
  import Md5Util from '@/utils/Md5Util';
  import TipUtil from '@/utils/TipUtil';
  import StringUtil from '@/utils/StringUtil';
  import Config from '@/config';
  import handler from './handler';
  import logoUrl from '../../assets/logo.png';
  import MenuUtil from '@/utils/MenuUtil';

  export default {
      components: {

      },
      name: 'alogin',
      data() {
          return {
              logoUrl,
              loginTitle: Config.get('loginTitle'),
              backgroudStyle: {
                  height: '100%',
                  background: '#409EFF',
//                  backgroundImage: "url(" + require("../../assets/login/dd.jpg") + ")",
//                  backgroundPosition: "center center"
              },
              ruleForm: {
                  loginName: '',
                  password: ''
              },
              loading: false
          }
      },
      methods: {
          submit(){
              let formData = {
                  loginName: this.ruleForm.loginName,
                  password: Md5Util.encode(this.ruleForm.password)
              };
              RestUtil.post('user/login', formData, {
                  enableLoading: true,       // 启动请求期间的正在加载
                  loadingStartFun: () => {   // 请求开始前执行
                      this.loading = true;
                  },
              }).then((result) => {
//                  console.log(result);
                  if(!StringUtil.isBlank(result.error)){
                      TipUtil.error(result.error);
                      return;
                  }
                  // 登录成功后处理
                  handler.afterLoginSuccess(result.properties.user);
                  // 处理权限
                  MenuUtil.loadMenu(result.properties.user, () => {
                      this.loading = false;
                      this.$router.push("/main");
                  });
              }, (error) => {
                  console.error(error);
                  this.loading = false;
                  if(error.responseJSON && error.responseJSON.errors instanceof Array){
                      let errorArr = error.responseJSON.errors;
                      let errorStr = '';
                      for(let i=0;i<errorArr.length; i++){
                          errorStr += errorArr[i].defaultMessage + "；";
                      }
                      TipUtil.error(errorStr);
                  }else {
                      TipUtil.error('请求出错，请检查您的网络是否正常');
                  }
              });
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
