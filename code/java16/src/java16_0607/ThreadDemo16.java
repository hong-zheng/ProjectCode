package java16_0607;

public class ThreadDemo16 {
    static class Test {
        private Object o = new Object();

        public void method() {
            synchronized (o) {
                System.out.println("hehe");
            }
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.method();
    }
}
