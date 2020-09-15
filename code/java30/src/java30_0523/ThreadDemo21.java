package java30_0523;

public class ThreadDemo21 {
    static class Singleton {
        private volatile static Singleton instance = null;

        private Singleton() { }

        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }
}
