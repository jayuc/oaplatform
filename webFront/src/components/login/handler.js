
import User from '@/user';

// 登录成功后执行
const afterLoginSuccess = (userInfo) => {

    // 用户信息赋值
    for(let index in userInfo){
        User.set(index, userInfo[index]);
    }

};

export default {
    afterLoginSuccess
}