
import moment from 'moment';

// 格式化时间，传入时间戳
const format = (timeStamp, formatStr) => {
    return moment(timeStamp).format(formatStr);
};

export default {
    format
}