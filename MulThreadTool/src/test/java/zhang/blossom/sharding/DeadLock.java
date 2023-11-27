package zhang.blossom.sharding;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

/**
 * @author: 张锦标
 * @date: 2023/9/11 11:37
 * DeadLock类
 */
public class DeadLock {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        new Thread(()->{
            synchronized (lock1){
                System.out.println("this is lock 1 be used");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2){
                    System.out.println("this is lock 2 be used");
                }
            }
        }).start();

        new Thread(()->{
            synchronized (lock2){
                System.out.println("this is lock 2 be used");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1){
                    System.out.println("this is lock 1 be used");
                }
            }
        }).start();
    }
}
