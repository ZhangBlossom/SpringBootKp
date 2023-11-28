package blossom.project.designmode.proxy.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// 被代理的类（不需要实现接口）


// 方法拦截器
class ServiceMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Before method " + method.getName());
        Object result = proxy.invokeSuper(obj, args);
        System.out.println("After method " + method.getName());
        return result;
    }
}

// 使用示例
public class CglibProxy {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealService.class);
        enhancer.setCallback(new ServiceMethodInterceptor());

        RealService proxyService = (RealService) enhancer.create();
        proxyService.doSomething();
    }
}