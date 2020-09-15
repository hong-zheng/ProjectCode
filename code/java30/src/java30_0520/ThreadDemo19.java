package java30_0520;

public class ThreadDemo19 {
    // Single Dog 单身狗
    // Singleton 单例
    static class Singleton {
        private static Singleton instance = new Singleton();
        // 当把这个类的构造方法设为 私有 的时候, 此时这个类就无法在类外部被实例化了.
        private Singleton() {}

        public static Singleton getInstance() {
            return instance;
        }
    }

    public static void main(String[] args) {
        // 具体用法
        // 要想获取到 Singleton 实例, 就必须通过 getInstance 获取.
        // 不能 new (构造方法是private的, new 的时候会直接编译出错)
        Singleton singleton = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        Singleton singleton3 = Singleton.getInstance();
        System.out.println(singleton == singleton2);
        System.out.println(singleton == singleton3);
    }
}
