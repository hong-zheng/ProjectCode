package java30_0801;

public class Demo1 {
    private static int i = 0;

    public static void add1() {
        synchronized (Demo1.class) {
            for (int n = 0; n < 100000; n++) {
                i++;
            }
        }
    }

    public static void add2() {
        for (int n = 0; n < 100000; n++) {
            synchronized (Demo1.class) {
                i++;
            }
        }
    }

    public static void main(String[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("hello");
        stringBuffer.append("hello");
        stringBuffer.append("hello");
        stringBuffer.append("hello");
        stringBuffer.append("hello");
    }
}
