
import User from '@/user';

let permissionMap = {};

const setMap = (map) => {
    permissionMap = map;
};

const hasAuth = (functionCode) => {
    let loginName = User.get('loginName');
    if(loginName.toUpperCase() == 'ADMIN'){
        return true;
    }
    if(permissionMap[functionCode]){
        return true;
    }
    return false;
};

export default {
    setMap,
    hasAuth
}