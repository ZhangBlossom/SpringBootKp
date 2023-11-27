package blossom.project.designmode.singleton;

import java.lang.reflect.Constructor;

public class Singleton {
    // 私有构造方法
    private Singleton() {
        // 防御反射破坏单例
        if (SingletonHolder.INSTANCE != null) {
            throw new IllegalStateException("Instance already exists!");
        }
    }

    // 静态内部类
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    // 获取实例的公共静态方法
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void show() {
        System.out.println("Singleton using static inner class");
    }
}

class ReflectionSingletonTest {
    public static void main(String[] args) {
        try {
            // 获取Singleton类的Class对象
            Class<Singleton> clazz = Singleton.class;

            // 获取私有构造方法
            Constructor<Singleton> constructor = clazz.getDeclaredConstructor();
            // 设置私有构造方法的可访问性
            constructor.setAccessible(true);

            // 创建Singleton的实例
            Singleton instance1 = constructor.newInstance();
            // 获取正常途径获得的实例
            Singleton instance2 = Singleton.getInstance();

            // 输出两个实例的哈希码，检查是否相同
            System.out.println(instance1.hashCode());
            System.out.println(instance2.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
