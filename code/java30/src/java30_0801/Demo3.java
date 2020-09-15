package java30_0801;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo3 {
    // 使用一个线程求 1-10000 的和. (结果是一个整数)
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Callable 和 Runnable 很像, 都是用来描述一个具体的 "任务"
        // Runnable 没有返回值.
        // Callable 带返回值.

        // 1. 创构建 Callable
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // 实现这个 call 方法, 来求想要的值.
                int sum = 0;
                for (int i = 1; i <= 10000; i++) {
                    sum += i;
                }
                return sum;
            }
        };

        // 2. 创建 FutureTask, 把刚才的 Callable 来包装一层.
        FutureTask<Integer> futureTask = new FutureTask<>(callable);

        // 3. 创建一个个线程, 该线程就会执行 FutureTask 中的指定的任务
        Thread t = new Thread(futureTask);
        t.start();

        // 4. 获取线程执行结果, get 方法会发生阻塞, 一直到线程中的 call 方法执行完毕, get 才会返回.
        Integer result = futureTask.get();
        System.out.println(result);
    }
}
