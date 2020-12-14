import Vue from 'vue';
import App from './App.vue';
import './plugins/element.js';
import './main.css';
import router from './router';
import yuCodeRadio from '@/components/public/yu-code-radio';

Vue.config.productionTip = false;

// 注册数据字典组件
Vue.component('yu-code-radio', yuCodeRadio);

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
