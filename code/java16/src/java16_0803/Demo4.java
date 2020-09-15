package java16_0803;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建一个线程, 计算 1-1000 的和

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int ret = 0;
                for (int i = 1; i <= 1000; i++) {
                    ret += i;
                }
                return ret;
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread t = new Thread(futureTask);
        t.start();

        // get 方法阻塞的. 直到线程结束, 才会返回.
        Integer result = futureTask.get();
        System.out.println(result);
    }
}
