
// 元素操作工具类

import $ from 'jquery';

// 获取元素高度
const height = (element, value) => {
    if(typeof value === 'number' && value > 0){
        $(element).height(value);
        return;
    }
    return $(element).height();
};

export default {
    height
}