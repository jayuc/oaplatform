/**
 * Created by yujie on 2019/4/17 11:34
 */

import $ from 'jquery';

// 根对象
const root = window.$rootScope ? window.$rootScope : {};

// rest 根路径
const restRoot = root.restRoot;

// get 方法
const get = (key) => {
  return root[key];
};

// set 方法
const set = (key, value) => {
  if(key){
    root[key] = value;
  }
};

let globalPageHeight = $(document).height();
// 页面高度
set('globalPageHeight', globalPageHeight);
// 内部页面高度
set('mainBodyHeight', globalPageHeight - 102);

export default {
  root,
  restRoot,
  get,
  set,
}
