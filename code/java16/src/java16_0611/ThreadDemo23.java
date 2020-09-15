package java16_0611;

public class ThreadDemo23 {
    // 使用懒汉模式来实现. Singleton 类被加载的时候, 不会立刻实例化.
    // 等到第一次使用这个实例的时候再实例化.
    static class Singleton {
        private Singleton() {}

        private volatile static Singleton instance = null;

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
