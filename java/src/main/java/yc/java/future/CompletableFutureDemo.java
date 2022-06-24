package yc.java.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author: gyc
 * @date: 2022/06/24 13:48
 * @description:
 */
public class CompletableFutureDemo {
    // ❶ 自定义线程池
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("ThreadPoolTest-task-%d").build();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS, AVAILABLE_PROCESSORS * 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(5), namedThreadFactory);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // ❷ 创建一个 CompletableFuture 对象
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        // ❸ 开启线程计算任务，并设置结果
        POOL_EXECUTOR.execute(() -> {
            try {
                // ❹ 休眠 3s，模拟任务进行
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----" + Thread.currentThread().getName() + "-----");
            // ❺ 设置计算结果到 Future
            completableFuture.complete("Job Done !");
        });
        // ❻ 等待计算结果
        String result = completableFuture.get();
        // ❼ 可避免调用线程一直阻塞
        // String result = completableFuture.get(1000, TimeUnit.MINUTES);
        System.out.println("-----" + Thread.currentThread().getName() + "-----");
        System.out.println("-----" + result + "-----");
    }
}
