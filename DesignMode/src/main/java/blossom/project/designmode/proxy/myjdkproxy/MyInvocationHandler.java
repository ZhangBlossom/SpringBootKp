package blossom.project.designmode.proxy.myjdkproxy;

import java.lang.reflect.Method;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:31
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyInvocationHandler类
 */
public interface MyInvocationHandler {

    Object invoke(Object proxy, Method method, Object[]args) throws Throwable;

}
