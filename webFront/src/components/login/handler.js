
import User from '@/user';
import RestUtil from '@/utils/RestUtil';
import Config from '@/config';

// 加载数据字典
const initCode = () => {
    RestUtil.get('code/list').then((list) => {
        // console.log(list);
        let map = {};
        for(let i=0;i<list.length;i++){
            let li = list[i];
            let code = li.code;
            if(map[code]){
                map[code].push(li);
            }else {
                map[code] = [li];
            }
        }
        Config.set('$code', map);
    });
};

// 加载机构树
const initOrg = () => {
    RestUtil.get('org/orgTree').then((tree) => {
        Config.set('$org', tree);
    });
};

// 登录成功后执行
const afterLoginSuccess = (userInfo) => {

    // 用户信息赋值
    for(let index in userInfo){
        User.set(index, userInfo[index]);
    }

    // 加载数据字典
    initCode();

    // 加载机构树
    initOrg();

};

export default {
    afterLoginSuccess
}