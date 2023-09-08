package blossom.project.redis;

/**
 * @author: 张锦标
 * @date: 2023/9/7 19:09
 * ThreadTest类
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {

        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread a = new Thread(()->{
            System.out.println("this is a");
            synchronized (lock1){
                System.out.println("this is a get lock1");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2){
                    System.out.println("this is a get lock2");
                }
            }
        });


        Thread b = new Thread(()->{
            System.out.println("this is b");
            synchronized (lock2){
                System.out.println("this is b get lock2");
                synchronized (lock1){
                    System.out.println("this is b get lock1");
                }
            }
        });

        a.start();
        Thread.sleep(1000);
        b.start();
    }
}
