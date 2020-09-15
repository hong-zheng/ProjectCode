package java16_0604;

public class ThreadDemo2 {
    private static long count = 100_0000_0000L;

    public static void main(String[] args) {
        // serial();
        concurrency();
    }

    private static void serial() {
        long beg = System.currentTimeMillis();

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

    private static void concurrency() {
        long beg = System.currentTimeMillis();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a++;
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                int b = 0;
                for (long i = 0; i < count; i++) {
                    b++;
                }
            }
        };
        t1.start();
        t2.start();

        try {
            // 线程等待. 让主线程等待 t1 和 t2 执行结束, 然后再继续往下执行.
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // t1 t2 和 main 线程之间都是并发执行的.
        // 调用了 t1.start 和 t2.start 之后, 两个新线程正在紧锣密鼓的进行计算过程中,
        // 此时主线程仍然会继续执行, 下面的 end 就随之被计算了.
        // 正确的做法应该是要保证 t1 和 t2 都计算完毕, 再来计算这个 end 的时间戳.
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - beg) + " ms");
    }
}
