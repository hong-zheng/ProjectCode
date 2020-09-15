package java30_0523;

import java.util.concurrent.PriorityBlockingQueue;

public class ThreadDemo23 {
    // 创建一个类来描述往定时器中存放的任务
    static class Task implements Comparable<Task> {
        // command 表示该任务具体要干啥
        private Runnable command;
        // time 表示啥时候可以执行这个任务
        private long time;

        // 参数的 time 表示多少 ms 之后执行任务.
        // 成员的 time 是一个绝对的时间.
        public Task(Runnable command, long time) {
            this.command = command;
            this.time = time + System.currentTimeMillis();
        }

        public void run() {
            command.run();
        }

        @Override
        public int compareTo(Task o) {
            // 时间越靠前的, 优先级就越高
            return (int) (this.time - o.time);
        }
    }

    static class Timer {
        // 只是一把锁, 用于辅助解决扫描线程的忙等问题
        Object mailBox = new Object();

        // 优先队列中包含了都有哪些任务需要执行
        private PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        // 还需要有一个专门的线程来扫描队首元素
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    try {
                        // 进行 take 操作, 就相当于已经把元素从队列中删除了.
                        // 如果 task 时间还没到, 需要把 task 再塞回队列
                        Task task = queue.take();
                        long curTime = System.currentTimeMillis();
                        if (task.time > curTime) {
                            // 执行任务的时机还没成熟
                            queue.put(task);
                            // 发现时间还没到, 就借助 wait 让线程能够耐心等待.
                            synchronized (mailBox) {
                                // wait 加上参数之后, 表示, 即使没有 notify, 时间到了, 也会自动被唤醒.
                                mailBox.wait(task.time - curTime);
                            }
                        } else {
                            // 执行任务的时机到了.
                            task.run();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public Timer() {
            Worker worker = new Worker();
            worker.start();
        }

        // 还要提供一个用于注册任务的核心接口
        // command 表示要执行的操作是啥. after 表示多长时间之后来执行(相对时间).
        public void schedule(Runnable command, long after) {
            Task task = new Task(command, after);
            queue.put(task);
            // 新的任务可能要比原来的任务更早去执行. 每次注册新任务都唤醒一下扫描线程.
            synchronized (mailBox) {
                mailBox.notify();
            }
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("闹钟响了");
                timer.schedule(this, 3000);
            }
        };
        System.out.println("开始计时");
        timer.schedule(runnable, 3000);
    }
}
