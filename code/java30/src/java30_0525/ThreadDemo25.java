package java30_0525;

public class ThreadDemo25 {
    // 饿汉模式的实现
    static class Singleton {
        private static Singleton instance = new Singleton();

        private Singleton() {}

        public static Singleton getInstance() {
            return instance;
        }
    }
}
