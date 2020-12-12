import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/main';
import Login from '@/components/login';
import LeaveBill from '@/components/process/leave';
import DoingBill from '@/components/process/doing';
import UserManage from '@/components/setting/UserManage';

Vue.use(Router);

export default new Router({
  routes: [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/main/',
        name: 'Main',
        component: Main,
        children: [
            {path: 'leave', component: LeaveBill},   // 请假
            {path: 'errand', component: DoingBill},  // 出差
            {path: 'userManage', component: UserManage},  // 用户管理
        ]
    }
  ]
})
