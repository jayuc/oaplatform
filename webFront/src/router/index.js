import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/main';
import Login from '@/components/login';
import LeaveBill from '@/components/process/leave';
import UserManage from '@/components/organization/UserManage';
import OrgManage from '@/components/organization/orgManage';
import BusinessTrip from '@/components/process/businessTripMain';
import PasswordSetting from '@/components/setting/passwordSetting';
import EconomicCost from '@/components/process/economicCostMain';
import TrainMain from '@/components/process/trainMain';
import TravelToolSet from '@/components/process/travelToolSetMain';
import GoAbroad from '@/components/process/goAbroadMain';
import LookDoc from '@/components/process/lookDocMain';
import Introduce from '@/components/process/introduceMain';
import Certificate from '@/components/process/certificateMain';
import Meeting from '@/components/process/meetingMain';
import MainMain from '@/components/main/main';
import RoleManage from '@/components/organization/role-manage';
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
            {path: 'main', component: MainMain},   // 首页
            {path: 'leave', component: LeaveBill},   // 请假
            {path: 'errand', component: BusinessTrip},  // 出差
            {path: 'cost', component: EconomicCost},  // 经济支出
            {path: 'train', component: TrainMain},  // 培训
            {path: 'travelToolSet', component: TravelToolSet},  // 交通工具调整
            {path: 'goAbroad', component: GoAbroad},  // 因私出国
            {path: 'lookDoc', component: LookDoc},  // 调阅会计档案
            {path: 'introduce', component: Introduce},  // 介绍信
            {path: 'certificate', component: Certificate},  // 使用证件
            {path: 'meeting', component: Meeting},  // 会议室
            {path: 'userManage', component: UserManage},  // 用户管理
            {path: 'orgManage', component: OrgManage},  // 机构管理
            {path: 'roleManage', component: RoleManage},  // 角色管理
            {path: 'doing', component: Doing},  // 正在开发
            {path: 'setPassword', component: PasswordSetting},  // 修改密码
        ]
    }
  ]
})
