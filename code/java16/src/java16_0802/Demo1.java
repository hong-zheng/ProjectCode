package java16_0802;

class A {
    public int num = 0;
}

class B {
    public int count = 0;
    public A a;
}

public class Demo1 {
    public static void main(String[] args) {
        // 先构造一个对象
        B b = new B();
        b.count = 10;
        b.a = new A();
        b.a.num = 100;

        // 针对 b 对象进行拷贝
        // copy1 直接返回了 b, 意味着 b 和 b2 指向的是同一个对象.
        // 其实没发生拷贝
//        B b2 = copy1(b);
        // copy2 b2 指向的是一个新的 B 对象了. 此时就发生了拷贝.
        // 这次拷贝是浅拷贝. b 和 b2 两者持有的是同一个 a 对象.
//        B b2 = copy2(b);
        B b2 = copy3(b);
        System.out.println(b2.count);
        System.out.println(b2.a.num);
        System.out.println("修改内容");
        b.count = 20;
        b.a.num = 200;
        System.out.println(b2.count);
        System.out.println(b2.a.num);
    }

    private static B copy3(B b) {
        B result = new B();
        result.count = b.count;
        result.a = new A();
        result.a.num = b.a.num;
        return result;
    }

    private static B copy2(B b) {
        B result = new B();
        result.count = b.count;
        result.a = b.a;
        return result;
    }

    private static B copy1(B b) {
        return b;
    }
}
