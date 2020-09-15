package java16_0611;

public class ThreadDemo22 {
    // 先创建一个表示单例的类
    // 我们就要求 Singleton 这个类只能有一个实例.
    // 单身狗. single dog
    // 饿汉模式的单例实现, "饿" 指的是, 只要类被加载, 实例就会立刻创建. (实例创建时机比较早)
    static class Singleton {
        // 把 构造方法 变成私有, 此时在该类外部就无法 new 在这个类的实例了.
        private Singleton() { }

        // 再来创建一个 static 的成员, 表示 Singleton 类的唯一的实例.
        // static 和 类相关, 和 实例无关~ 类在内存中只有一份, static 成员也就只有一份.
        private static Singleton instance = new Singleton();

        public static Singleton getInstance() {
            return instance;
        }
    }

    public static void main(String[] args) {
        // Singleton s3 = new Singleton();
        // 此处的 getInstance 就是获取该类实例的唯一方式. 不应该使用其他方式来创建实例了.
        Singleton s = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s == s2);
    }
}
