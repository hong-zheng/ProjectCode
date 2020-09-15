package java30_0513;

public class ThreadDemo7 {
    // 这个变量用来表示当前线程是否是退出的状态.
    private static boolean isQuit = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isQuit) {
                    System.out.println("我正忙着转账呢!!!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("转账被终止.");
            }
        };
        t.start();

        Thread.sleep(5000);
        System.out.println("对方是内鬼!!! 赶紧终止交易!!!!");
        isQuit = true;
    }
}
