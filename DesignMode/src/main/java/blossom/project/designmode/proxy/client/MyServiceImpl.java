package blossom.project.designmode.proxy.client;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:44
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MyServiceImpl类
 */
public class MyServiceImpl implements MyService {
    @Override
    public void function() {
        System.out.println("被代理的那个类的方法执行。。。");
    }
}
