
import moment from 'moment';

// 格式化时间，传入时间戳
const format = (timeStamp, formatStr) => {
    return moment(timeStamp).format(formatStr);
};

// 字符串转日期
const date = (str) => {
    return new Date(str);
};

export default {
    format,
    date
}