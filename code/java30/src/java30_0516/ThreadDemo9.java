package java30_0516;

public class ThreadDemo9 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("我是一个线程");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("我工作结束了");
            }
        };
        t.start();
        t.join();
        System.out.println("我是主线程");
    }
}
