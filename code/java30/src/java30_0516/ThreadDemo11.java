package java30_0516;

public class ThreadDemo11 {
    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    // 循环内部啥都不干~
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // start 之前先获取一次状态
        System.out.println(t.getState());

        t.start();

        while (t.isAlive()) {
            System.out.println(t.getState());
        }

        System.out.println(t.getState());
    }
}
