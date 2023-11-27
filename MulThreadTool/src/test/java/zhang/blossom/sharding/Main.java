package zhang.blossom.sharding;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static final Lock lock = new ReentrantLock();
    private static final Condition c1 = lock.newCondition();
    private static final Condition c2 = lock.newCondition();
    private static final Condition c3 = lock.newCondition();
    private static int state = 1;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    while (state != 1) {
                        c1.await();
                    }
                    System.out.println("Thread 1");
                    state = 2;
                    c2.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    while (state != 2) {
                        c2.await();
                    }
                    System.out.println("Thread 2");
                    state = 3;
                    c3.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 5; i++) {
                    while (state != 3) {
                        c3.await();
                    }
                    System.out.println("Thread 3");
                    state = 1;
                    c1.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}

class Main2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1");
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();  // 等待 t1 线程执行完毕
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2");
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.join();  // 等待 t2 线程执行完毕
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 3");
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
