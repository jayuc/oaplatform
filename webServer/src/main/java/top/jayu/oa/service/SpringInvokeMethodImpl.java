package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import top.jayu.oa.iter.SpringInvokeMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Service
public class SpringInvokeMethodImpl implements SpringInvokeMethod {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Object invokeMethod(String serviceName, String methodName, Object[] params) throws Exception {
        Object service = applicationContext.getBean(serviceName);
        Class<? extends Object>[] paramClass = null;
        if (params != null) {
            int paramsLength = params.length;
            paramClass = new Class[paramsLength];
            for (int i = 0; i < paramsLength; i++) {
                paramClass[i] = params[i].getClass();
            }
        }
        // 找到方法
        Method method = ReflectionUtils.findMethod(service.getClass(), methodName, paramClass);
        // 执行方法
        return ReflectionUtils.invokeMethod(method, service, params);
    }

    @Override
    public Object invokeProperty(Object object, String methodName) throws Exception {
        Field declaredField = object.getClass().getDeclaredField(methodName);
        declaredField.setAccessible(true);
        return declaredField.get(object);
    }

}
