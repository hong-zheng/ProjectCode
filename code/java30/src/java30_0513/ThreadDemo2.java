package java30_0513;

public class ThreadDemo2 {
    private static long count = 100_0000_0000L;

    public static void main(String[] args) {
        // 完成一个简单的任务: 让代码分别针对 a 和 b 两个整数, 自增很多很多次
        // serial();
        concurency();
    }

    private static void concurency() {
        long beg = System.currentTimeMillis();  // 单位是 ms

        Thread t1 = new Thread() {
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a++;
                }
            }
        };
        t1.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                int b = 0;
                for (long i = 0; i < count; i++) {
                    b++;
                }
            }
        };
        t2.start();

        try {
            // 线程等待的逻辑.
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();  // 单位是 ms
        System.out.println("time: " + (end - beg) + " ms");
    }

    private static void serial() {
        // 如何衡量一段代码的执行时间?
        // 代码开始执行的时候记录一个时间戳.
        // 代码结束执行的时候, 再记录一个时间戳.
        // 两者相减就是这个代码的执行时间.
        long beg = System.currentTimeMillis();  // 单位是 ms

        int a = 0;
        for (long i = 0; i < count; i++) {
            a++;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b++;
        }

        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - beg) + " ms");
    }
}
