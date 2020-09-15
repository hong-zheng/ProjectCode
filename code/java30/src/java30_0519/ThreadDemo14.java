package java30_0519;

import java.util.Scanner;

public class ThreadDemo14 {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                synchronized (ThreadDemo14.class) {
                    System.out.println("请输入一个整数: ");
                    int num = scanner.nextInt();
                    System.out.println("t1: " + num);
                }
            }
        };
        t1.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    synchronized (ThreadDemo14.class) {
                        System.out.println("t2");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        t2.start();
    }
}
