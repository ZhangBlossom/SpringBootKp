package blossom.project.designmode.proxy.client;

import blossom.project.designmode.proxy.myjdkproxy.MyClassLoader;
import blossom.project.designmode.proxy.myjdkproxy.MyInvocationHandler;
import blossom.project.designmode.proxy.myjdkproxy.MyProxy;

import java.lang.reflect.Method;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 14:21
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyInvocationHandlerImpl类
 */
public class MyInvocationHandlerImpl implements MyInvocationHandler {


    //被代理的实际类
    private MyService target;


    public MyInvocationHandlerImpl(){}
    public MyInvocationHandlerImpl(MyService target){
        this.target = target;
    }

    /**
     * 获得代理对象
     * @param target
     * @return
     */
    public MyService getProxyInstance(MyService target){
        this.target = target;
        Class<? extends MyService> clazz = target.getClass();
        return (MyService) MyProxy.newProxyInstance(new MyClassLoader(),
                clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("-----前置代理增强-----");
        Object result = method.invoke(this.target, args);
        System.out.println("返回的结果为："+result);
        System.out.println("-----后置代理增强-----");
        return result;
    }

}
