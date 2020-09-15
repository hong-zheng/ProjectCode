package java30_0801;

import java.util.concurrent.Semaphore;

public class Demo4 {
    public static void main(String[] args) {
        // 初始值表示可用资源的个数
        Semaphore semaphore = new Semaphore(4);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    long threadId = Thread.currentThread().getId();
                    System.out.println("申请资源: " + threadId);
                    // P 操作, 申请资源, 计数器 - 1
                    semaphore.acquire();
                    System.out.println("我获取到资源了: " + threadId);
                    Thread.sleep(1000);
                    System.out.println("释放资源: " + threadId);
                    // V 操作, 释放资源, 计数器 + 1
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }
}
