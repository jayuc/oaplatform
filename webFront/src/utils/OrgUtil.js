
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

const getNameById = (id) => {
    let org = getOrgById(id);
    if(org){
        return org.orgName;
    }
    return '';
};

const getShortNameById = (id) => {
    let org = getOrgById(id);
    if(org){
        if(org.attribute){
            return org.attribute.shortOrgName;
        }
        return org.orgName;
    }
    return '';
};

export default {
    getOrgTree,
    getOrgById,
    loop,
    getNameById,
    getShortNameById
}