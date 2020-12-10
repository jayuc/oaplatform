<template>
  <div>
    <router-link to="/main">to main</router-link>
    <el-button type="primary" @click="select">点击</el-button>
  </div>
</template>

<script>

  import ElButton from "../../../node_modules/element-ui/packages/button/src/button.vue";
  import RestUtil from '@/utils/RestUtil';

  export default {
      components: {ElButton},
      name: 'login',
      methods: {
          select(){
              RestUtil.get('user/getUserById', {id: 1}).then((result) => {
                  console.log(result);
              });
          }
      }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
