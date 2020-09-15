package java30_0513;

public class ThreadDemo1 {
    static class MyThread extends Thread {
        @Override
        public void run() {
            // 线程的入口方法. 当线程启动之后, 就会执行到 run 方法中的逻辑
            // 这个方法不需要手动调用, 由 JVM 来自动执行的.
            while (true) {
                System.out.println("我是新线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // 向上转型.
        Thread t = new MyThread();
        // 调用 start 方法, 就会在 操作系统 中创建一个线程.
        // 内核中就有了一个 PCB 对象, 被加入到双向链表中.
        // 当线程创建完毕之后, run 方法就会被自动调用到.
        // 当 run 方法执行完毕, 线程就被自动销毁了.
        t.start();
        while (true) {
            System.out.println("我是主线程");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
