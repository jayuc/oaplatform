
import { Message } from 'element-ui';

// 普通提示
const info = (message) => {
    Message.info(message);
};

// 警告
const warning = (message) => {
    Message.warning(message);
};

// 成功
const success = (message) => {
    Message.success(message);
};

// 错误
const error = (message) => {
    Message.error(message);
};

export default {
    info,
    warning,
    success,
    error
}