
import $ from 'jquery';
import Config from '@/config';
import User from '@/user';
import LoadingUtil from '@/utils/LoadingUtil';

const restRoot = Config.restRoot;

// 综合方式请求
function ajax(_options, _data, _opt) {

  if (typeof _options == 'object'){   //不使用promise处理
    doAjax(_options);
  }else if(typeof _options == 'string'){ //使用promise处理
    return doAjaxByPromise(_options, _data, _opt);
  }

}

//get方式
function get(_options, _data, _opt) {
  return typeAjax(_options, _data, _opt, 'get');
}

//post方式
function post(_options, _data, _opt) {
  return typeAjax(_options, _data, _opt, 'post');
}

//根据类型请求
function typeAjax(_options, _data, _opt, type) {
  if(typeof _options == 'object'){
    let options = {};
    $.extend(options, _options);
    options.type = type;
    doAjax(options);
  }else if(typeof _options == 'string'){
    let options = {};
    $.extend(options, _opt);
    options.type = type;
    return doAjaxByPromise(_options, _data, options);
  }
}

/**
 * 使用promise方式处理
 * @param _url rest接口
 * @param _data ajax参数
 * @param _options 方法选项  enableLoading: 是否开启正在加载
 *                          loadingStartFun: 正在加载开始时执行方法
 *                          loadingEndFun: 正在加载结束时执行方法
 * @returns {Promise<any>}
 */
function doAjaxByPromise(_url, _data, _options) {
  let options = {
    url: _url,
    data: {},
    type: 'get'
  };
  if(typeof _data == 'object'){
    options.data = _data;
  }
  handleOptions(options, _options);
  let loadingInstance;
  if(options.enableLoading){
      loadingInstance = LoadingUtil.start();
  }
  if(typeof options.loadingStartFun === 'function'){
      options.loadingStartFun();
  }
  return new Promise((resolve, reject) => {
    $.ajax({
      url: handleUrl(options),
      type: options.type,
      data: options.data,
      timeout: 1000000,
      success: function (data) {
        resolve(data);
      },
      error: function (errmsg) {
        reject(errmsg);
      },
        complete: function () {
            if(typeof options.loadingEndFun === 'function'){
                options.loadingEndFun();
            }
            if(loadingInstance){
                loadingInstance.close();
            }
        }
    });
  });
}

/**
 * 使用原生ajax方式
 * @param _options
 */
function doAjax(_options) {
  if(!_options.url){
    throw new Error("url不能为空");
  }
  let options = {
    url: '',
    data: {},
    type: 'get',
    success: function () {
    },
    error: function () {
    }
  };
  handleOptions(options, _options);
  $.ajax({
    url: handleUrl(options),
    type: options.type,
    data: options.data,
    timeout: 1000000,
    success: options.success,
    error: (err) => {
      options.error(err);
    }
  });
}

// 处理options
// 处理公用部分参数
// userId,orgId,roleId,employId,token这个几个字段为专用字段，表示用户信息
function handleOptions(options, _options){
    $.extend(options, _options);
    options.data.currentUserId = User.get("userId");
    options.data.currentUserName = User.get("userName");
    options.data.currentOrgId = User.get("orgId");
    options.data.currentOrgCode = User.get("orgCode");
    options.data.currentRoleId = User.get("roleId");
    options.data.currentEmployId = User.get("employId");
    options.data.currentOrgCodePriv = User.get("orgCodePriv");
    options.data.currentLoginName = User.get("loginName");
    options.data.token = "";
}

//处理url
function handleUrl(options) {
  return restRoot + options.url;
}

// 在服务挂机时，强制刷新页面
function forceRefresh(status) {
  //console.log(status);
  if(status === 'error'){
    window.location.reload();
  }
}

export default {
  ajax,
  post,
  get,
  forceRefresh,
}
