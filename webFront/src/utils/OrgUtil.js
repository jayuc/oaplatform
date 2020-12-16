
import Config from '@/config';

// 获取机构树
const getOrgTree = () => {
    return Config.get('$org');
};

// 机构 map
const orgMap = {};
// 循环数组
const loop = (list) => {
    if(list instanceof Array){
        for(let i=0; i<list.length; i++){
            let item = list[i];
            orgMap[item.orgId] = item;
            loop(item.children);
        }
    }
    Config.set('$orgMap', orgMap);
};

// 通过机构 id 获取机构
const getOrgById = (id) => {
    let orgMap = Config.get('$orgMap');
    if(orgMap){
        return orgMap[id];
    }
};

export default {
    getOrgTree,
    getOrgById,
    loop
}