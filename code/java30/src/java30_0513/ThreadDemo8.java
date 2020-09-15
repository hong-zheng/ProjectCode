package java30_0513;

public class ThreadDemo8 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("我正忙着转账呢");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // e.printStackTrace();
                        break;  // t.interrupt() 会让 t 这个线程内部触发一个异常. 当捕捉到这个异常的时候, 就可以让循环结束了.
                    }
                }
                System.out.println("转账被终止");
            }
        };
        t.start();

        Thread.sleep(5000);
        System.out.println("对方是内鬼, 赶紧停止交易!!!!");
        t.interrupt();
    }
}
