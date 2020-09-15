package java16_0606;

public class ThreadDemo4 {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("我是一个新线程");
        }
    }

    public static void main(String[] args) {
        Thread t = new MyThread();
        // t.run();
        t.start();
    }
}
