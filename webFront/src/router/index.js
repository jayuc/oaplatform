import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/main';
import Login from '@/components/login';
import LeaveBill from '@/components/process/leave';
import UserManage from '@/components/setting/UserManage';
import OrgManage from '@/components/setting/orgManage';
import BusinessTrip from '@/components/process/businessTripMain';
import Doing from '@/components/process/doing';

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
            {path: 'errand', component: BusinessTrip},  // 出差
            {path: 'userManage', component: UserManage},  // 用户管理
            {path: 'orgManage', component: OrgManage},  // 机构管理
            {path: 'doing', component: Doing},  // 机构管理
        ]
    }
  ]
})
