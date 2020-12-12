
import { Loading } from 'element-ui';

// 开始正在加载
const start = (_options) => {
    let options = {
        fullscreen: true
    };
    Object.assign(options, _options);
    return Loading.service(options);
};

export default {
    start
}