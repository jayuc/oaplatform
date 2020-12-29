
import User from '@/user';
import RestUtil from '@/utils/RestUtil';
import Config from '@/config';
import OrgUtil from '@/utils/OrgUtil';
import CodeUtil from '@/utils/CodeUtil';

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

        // 获取并设置业务域
        let firmType = getFirmType();
        User.set('firmType', firmType);

    });
};

// 加载机构树
const initOrg = () => {
    RestUtil.get('org/orgTree').then((tree) => {
        Config.set('$org', tree);
        // 生成机构 map
        OrgUtil.loop(tree);
    });
};

// 获取业务域
const getFirmType = () => {
    let firmArr = CodeUtil.getCodes(5);
    let orgName = User.get('orgName');
    if(orgName && firmArr){
        for (let i=0; i<firmArr.length; i++){
            let item = firmArr[i];
            if(orgName.indexOf(item.name) == 0){
                return item.codeNo;
            }
        }
    }
    return null;
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