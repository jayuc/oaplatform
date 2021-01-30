
import RestUtil from '@/utils/RestUtil';
import PermissionUtil from '@/utils/PermissionUtil';

let menuTree = [];

// 加载菜单权限
const loadMenu = (userInfo, callback) => {
    let param = {
        currentLoginName: userInfo['loginName'],
        currentUserId: userInfo['userId']
    };
    RestUtil.get('menu/getMenuTree', param).then((result) => {
        if(result){
            menuTree = result['menu'];
            PermissionUtil.setMap(result['permission']);
            if(typeof callback === 'function'){
                callback(menuTree);
            }
        }
    });
};

const getTree = () => {
    return menuTree;
};

export default {
    getTree,
    loadMenu
}