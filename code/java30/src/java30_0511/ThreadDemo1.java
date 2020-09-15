package java30_0511;

public class ThreadDemo1 {
    static class MyThread extends Thread {
        @Override
        public void run() {
            // run 方法中的代码就是线程运行时所需要执行的逻辑
            System.out.println("hello world");
        }
    }

    public static void main(String[] args) {
        Thread t = new MyThread();
        // t.run(); 这是错误的. 这样写并没有在系统中创建新线程.
        // 需要 start 方法来创建新线程.
        // 所谓的创建新线程, 本质上是在操作系统内核中, 创建一个 PCB 对象
        // 把这个对象加入到双向链表中. 接下来, 就可以由操作系统进行调度了.
        // 这些操作都是包含在 start 方法中的
        t.start();
    }
}
