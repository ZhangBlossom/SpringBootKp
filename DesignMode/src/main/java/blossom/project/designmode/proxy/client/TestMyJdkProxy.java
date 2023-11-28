package blossom.project.designmode.proxy.client;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/28 12:42
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * TestMyJdkProxy类
 */
public class TestMyJdkProxy {
    public static void main(String[] args) {
        MyInvocationHandlerImpl myInvocationHandler = new MyInvocationHandlerImpl();
        MyService myService = myInvocationHandler.getProxyInstance(new MyServiceImpl());
        myService.function();
    }
}
