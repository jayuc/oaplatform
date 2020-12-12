/**
 * Created by yujie on 2019/8/6 14:48
 */

import md5 from 'js-md5';

// 加密
function encode(str) {
  if(typeof str === 'string' && str.length > 0){
      return md5(str);
  }
  return str;
}

// 解密
function decode() {

}

export default {
  encode,
  decode
}
