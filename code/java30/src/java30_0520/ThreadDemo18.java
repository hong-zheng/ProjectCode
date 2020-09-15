package java30_0520;

public class ThreadDemo18 {
    // 在这个代码中, 必须要保证, WaitTask 和 NotifyTask 对应的实例中, 持有的 locker 对象是同一个对象.
    static class WaitTask implements Runnable {
        private Object locker;

        public WaitTask(Object locker) {
            this.locker = locker;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (locker) {
                    System.out.println("wait 开始");
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("wait 结束");
                }
            }
        }
    }

    static class NotifyTask implements Runnable {
        private Object locker;

        public NotifyTask(Object locker) {
            this.locker = locker;
        }

        @Override
        public void run() {
            synchronized (locker) {
                System.out.println("notify 开始");
                locker.notify();
                System.out.println("notify 结束");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object locker = new Object();
        Object locker2 = new Object();
        Thread t1 = new Thread(new WaitTask(locker));
        Thread t2 = new Thread(new NotifyTask(locker2));
        t1.start();
        // 为了大概率让 t1 先执行, t2 后执行, 手动加一个 sleep
        Thread.sleep(5000);
        t2.start();
    }
}
