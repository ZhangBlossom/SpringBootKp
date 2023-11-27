package zhang.blossom.sharding;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: 张锦标
 * @date: 2023/9/25 16:47
 * CompletionServiceTest类
 */
public class CompletionServiceTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(es);
        completionService.submit(()->{
            System.out.println("this is first task");
            Thread.sleep(3000);
            return "1000";
        });
        completionService.submit(()->{
            System.out.println("this is second task");
            Thread.sleep(5000);
            return "3000";
        });
        completionService.submit(()->{
            System.out.println("this is third task");
            Thread.sleep(8000);
            return "9000";
        });
        for(int i = 0;i<3;i++){
            String money = completionService.take().get();
            System.out.println(money);
        }
        es.shutdown();
    }
}
