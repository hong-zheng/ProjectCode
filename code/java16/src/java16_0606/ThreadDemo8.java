package java16_0606;

public class ThreadDemo8 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                // 此处直接使用线程内部的标记位来判定.
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("被管我, 我在忙着转账呢");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println("转账被终止.");
            }
        };
        t.start();

        Thread.sleep(5000);
        System.out.println("对方是内鬼, 快终止交易!!!");
        t.interrupt();
    }
}
