package java30_0519;

import java.util.Scanner;

public class ThreadDemo15 {
    static class Counter {
        volatile public int count = 0;
    }

    public static void main(String[] args) {
        Counter counter = new Counter();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (counter.count == 0) {

                }
                System.out.println("循环结束");
            }
        };
        t1.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入一个整数");
                counter.count = scanner.nextInt();
            }
        };
        t2.start();
    }
}
