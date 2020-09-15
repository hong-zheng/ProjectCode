package java30_0520;

import java.security.Signature;

public class ThreadDemo20 {
    static class Singleton {
        // 当程序把类加载的时候, 并没有立刻就申请内存空间
        private static Singleton instance = null;

        private Singleton() {}

        // 获取实例
        public static Singleton getInstance() {
            if (instance == null) {
                instance = new Singleton();
            }
            return instance;
        }
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println(singleton == singleton2);
    }
}
