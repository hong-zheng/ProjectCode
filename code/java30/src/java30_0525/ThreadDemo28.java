package java30_0525;

import java.util.concurrent.PriorityBlockingQueue;

public class ThreadDemo28 {
    // Task 类用来描述定时器中的任务
    // 由于 Task 需要被放到优先队列中. 因此需要指定 Task 之间的大小关系.
    // 按照时间来确定. 时间小的排在前面
    static class Task implements Comparable<Task> {
        public Runnable command = null;
        public long time;

        // time 表示多少毫秒后执行 command 任务
        public Task(Runnable command, long time) {
            this.command = command;
            this.time = System.currentTimeMillis() + time;
        }

        public void run() {
            command.run();
        }

        @Override
        public int compareTo(Task o) {
            return (int) (this.time - o.time);
        }
    }

    static class Timer {
        private PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        // 用于控制扫描线程不要忙等
        Object mailBox = new Object();
        // 需要有一个专门的扫描线程检查队列中的 Task 是否能执行了.
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    try {
                        Task task = queue.take();
                        long curTime = System.currentTimeMillis();
                        if (task.time > curTime) {
                            // 执行任务的时间还没到.
                            queue.put(task);
                            synchronized (mailBox) {
                                mailBox.wait(task.time - curTime);
                            }
                        } else {
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

        // 把任务注册给定时器.
        public void schedule(Runnable command, long after) {
            Task task = new Task(command, after);
            queue.put(task);
            synchronized (mailBox) {
                mailBox.notify();
            }
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("时间到了, 执行任务");
                timer.schedule(this, 3000);
            }
        }, 3000);
    }
}
