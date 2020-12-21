
import { Loading } from 'element-ui';

// 开始正在加载
const start = (_options) => {
    let options = {
        fullscreen: true,
        background: 'rgba(0, 0, 0, 0.8)',
        spinner: 'el-icon-loading',
        text: '加载中'
    };
    Object.assign(options, _options);
    return Loading.service(options);
};

export default {
    start
}