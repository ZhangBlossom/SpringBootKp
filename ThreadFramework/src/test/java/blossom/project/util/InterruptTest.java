package blossom.project.util;

/**
 * @author: 张锦标
 * @date: 2023/9/7 19:32
 * InterruptTest类
 */
public class InterruptTest {
    public static void main(String[] args) {
        Object lock = new Object();
        DeadLockTest t = new DeadLockTest();
        Thread a = new Thread(() -> {
            System.out.println("this is thread a");
            System.out.println("hello this is thread a wake");
        });
        a.start();

        Thread b = new Thread(() -> {
            System.out.println("this is thread b");
            while (true) {
                System.out.println("this is deamon thread");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        b.setDaemon(true);
        b.start();

        System.out.println("主线程over");

        InheritableThreadLocal threadLocal = new InheritableThreadLocal();
    }
}
