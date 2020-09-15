package java30_0513;

public class ThreadDemo6 {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("hehe");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        // t1.run();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("haha");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();
        // t2.run();
    }
}
