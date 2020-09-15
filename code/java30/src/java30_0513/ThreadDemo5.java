package java30_0513;

public class ThreadDemo5 {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程的代码其实很多都是固定代码. 没必要一个字一个字敲出来.
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("我是一个新线程! 我还活着");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("我要死了");
            }
        };

        // 先在 start 之前来调用 get 方法获取到线程的相关属性
        System.out.println(t.getId());
        System.out.println(t.getName());
        System.out.println(t.getState());
        System.out.println(t.getPriority());
        System.out.println(t.isDaemon());
        System.out.println(t.isAlive());
        System.out.println(t.isInterrupted());

        t.start();

        while (t.isAlive()) {
            System.out.println("该线程还活着, 状态是: " + t.getState());
            Thread.sleep(500);
        }
    }

}
