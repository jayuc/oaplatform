
import Config from '@/config';

const getCodes = (codeType) => {
    let codes = Config.get('$code');
    if(codes){
        return codes[codeType];
    }
    return null;
};

// 获取数据字典名
const getName = (codeType, code) => {
    let codes = getCodes(codeType);
    if(codes instanceof Array){
        for(let i=0;i<codes.length;i++){
            let item = codes[i];
            if(item.codeNo == code){
                return item.name;
            }
        }
    }
    return '';
};

export default {
    getName,
    getCodes
}