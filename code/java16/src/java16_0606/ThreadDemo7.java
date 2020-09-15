package java16_0606;

public class ThreadDemo7 {
    private static boolean isQuit = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isQuit) {
                    System.out.println("别烦我, 我在忙着转账呢");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("转账操作被终止.");
            }
        };
        t.start();

        Thread.sleep(5000);
        // 老板来电话了: 对方是内鬼, 赶快终止交易!!!!
        System.out.println("对方是内鬼, 赶快终止交易!!!");
        isQuit = true;
    }
}
