package top.jayu.oa.iter;

public interface SpringInvokeMethod {

    /**
     * 动态执行某个bean的某个方法
     */
    Object invokeMethod(String serviceName, String methodName, Object[] params) throws Exception;

    /**
     * 动态获取某个属性值
     */
    Object invokeProperty(Object object, String methodName) throws Exception;

}
