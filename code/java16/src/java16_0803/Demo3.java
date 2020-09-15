package java16_0803;

public class Demo3 {
    private static int i = 0;
    public static void main(String[] args) {
        // 假设这个代码是多线程的
        synchronized (Demo3.class) {
            for (int c = 0; c < 10000; c++) {
                i++;
            }
        }

        for (int c = 0; c < 10000; c++) {
            synchronized (Demo3.class) {
                i++;
            }
        }
    }
}
