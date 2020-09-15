package java30_0525;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadDemo29 {
    static class MyThreadPool {
        private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        static class Worker extends Thread {
            // N 个 Worker 线程本质上访问的是同一个任务队列
            private BlockingQueue<Runnable> queue = null;

            public Worker(BlockingQueue<Runnable> queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                // try 必须在 while 之外. 为了后面 shutdown 方法触发 interrupt 操作时
                // 就能够结束 run 方法.
                // 如果 try 在 while 内部, 就必须在 catch 中加上 break 来显式结束循环.
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable command = queue.take();
                        command.run();
                    }
                } catch (InterruptedException e) {
                    // e.printStackTrace();
                }
            }
        }  // end class Worker

        private int maxWorkerCount = 10;
        private List<Worker> workers = new ArrayList<>();

        // 实现 execute 来注册任务到线程池中
        public void execute(Runnable command) throws InterruptedException {
            // 控制线程池中的线程数目不要超过上限.
            if (workers.size() < maxWorkerCount) {
                Worker worker = new Worker(queue);
                worker.start();
                workers.add(worker);
            }
            queue.put(command);
        }

        public void shutdown() throws InterruptedException {
            for (Worker worker : workers) {
                worker.interrupt();
            }
            // 加上 join 的意义是为了保证 shutdown 执行完毕时, 这些线程肯定也都结束了.
            for (Worker worker : workers) {
                worker.join();
            }
        }

    } // end class MyThreadPool

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool myThreadPool = new MyThreadPool();
        for (int i = 0; i < 100; i++) {
            myThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("我是要执行的任务");
                }
            });
        }
        Thread.sleep(3000);
        myThreadPool.shutdown();
    }
}
