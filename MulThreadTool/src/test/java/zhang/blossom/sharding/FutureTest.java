package zhang.blossom.sharding;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: 张锦标
 * @date: 2023/9/25 16:07
 * CompletableFutureTest类
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask ft = new FutureTask(()->{
            System.out.println("hello");
            Thread.sleep(1000);
            return 1;
        });
        FutureTask ft1 = new FutureTask(()->{
            System.out.println("hello1");
            Thread.sleep(2000);
            return 2;
        });
        FutureTask ft2 = new FutureTask(()->{
            System.out.println("hello2");
            Thread.sleep(3000);
            return 3;
        });
        new Thread(ft).start();
        new Thread(ft1).start();
        new Thread(ft2).start();
        //必须等待最久的这个执行完毕其他的才能执行
        //由于这个最久 其他的其实早都执行完毕了
        //此时只能阻塞而不能提前做预处理 纯浪费cpu
        System.out.println(ft2.get());
        System.out.println(ft1.get());
        System.out.println(ft.get());
        System.out.println("------");
        CompletableFuture<Object> cf1 = new CompletableFuture<>();
    }
}
