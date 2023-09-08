package blossom.project.util;

/**
 * @author: 张锦标
 * @date: 2023/9/7 19:21
 * WaitNotifyTest类
 */
public class WaitNotifyTest {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread a = new Thread(() -> {
            System.out.println("this is thread a");
            synchronized (lock) {
                try {
                    lock.wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("hello this is thread a wake");
        });
        a.start();
        Thread.sleep(2000);
        Thread b = new Thread(() -> {
            System.out.println("this is thread b");
            synchronized (lock) {
                try {
                    lock.notify();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("this is thread b notify a");
        });
        b.start();
    }
}
