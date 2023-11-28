package blossom.project.designmode.proxy.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import sun.misc.ProxyGenerator;
// 接口
interface Service {
    void doSomething();
}

// 实现类
class RealService implements Service {
    public void doSomething() {
        System.out.println("Doing something in RealService");
    }
}

// 调用处理器
class ServiceInvocationHandler implements InvocationHandler {
  	//被代理的实际对象
    private Object target;
	
    public ServiceInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //代理前置处理
        System.out.println("Before method " + method.getName());
        //被代理对象的方法被调用
        Object result = method.invoke(target, args);
        //代理后置处理
        System.out.println("After method " + method.getName());
        return result;
    }
}

// 使用示例
public class JdkProxy {
    public static void main(String[] args) {
        RealService realService = new RealService();
        //创建代理对象 然后调用代理对象的方法即可
        Service proxyService = (Service) Proxy.newProxyInstance(
            RealService.class.getClassLoader(),
            new Class<?>[] {Service.class},
            new ServiceInvocationHandler(realService)
        );
      	
        proxyService.doSomething();

        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Service.class});

        // 保存到文件系统
        try (FileOutputStream out = new FileOutputStream("D://desktop//" + "$Proxy.class")) {
            out.write(classFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}