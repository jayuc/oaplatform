import Vue from 'vue';
import App from './App.vue';
import './plugins/element.js';
import './main.css';
import router from './router';
import yuAuth from '@/components/public/yu-auth';
import yuCodeRadio from '@/components/public/yu-code-radio';
import yuOrgRadio from '@/components/public/yu-org-radio';

Vue.config.productionTip = false;

// 注册数据字典组件
Vue.component('yu-code-radio', yuCodeRadio);
// 注册权限组件
Vue.component('yu-auth', yuAuth);
// 注册机构组件
Vue.component('yu-org-radio', yuOrgRadio);

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
