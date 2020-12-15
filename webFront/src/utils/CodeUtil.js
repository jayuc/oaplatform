
import Config from '@/config';

const $codes = Config.get('$code');

// 获取数据字典名
const getName = (codeType, code) => {
    let codes = $codes[codeType];
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
    getName
}