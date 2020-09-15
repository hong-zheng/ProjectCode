package java30_0725;

class B {
    public int num = 0;
}

class C {
    public int count = 0;
    public B b;
}

public class Test3 {
    public static void main(String[] args) {
        // 1. 创建个对象 C 出来.
        C c = new C();
        c.count = 10;
        c.b = new B();
        c.b.num = 100;
        // 2. 调用 copy 方法对 c 进行拷贝
        // 如果是实现方式1, 此时 c2 和 c 俩指向的是同一个对象. (没拷贝)
        // 此时 c2 中的属性 和 c 中的属性值都是一模一样的.
//        C c2 = copy1(c);

        // 如果是实现方式2, 此时 c2 和 c 不再指向同一个对象了.
        // 但是 c 和 c2 中的属性都是一致的
        // 虽然拷贝了, 但是 c2 就是 c 的浅拷贝. 如果修改了 c , c2 也会有影响.
        // C c2 = copy2(c);

        // 如果是实现方式 3, 此时把 c 和 b 都进行了拷贝, 此时 c 和 c2 才彻底脱钩
        // 两者终于没有关联了. 此时 c2 就是 c 的深拷贝.
        C c2 = copy3(c);
        System.out.println(c.count);
        System.out.println(c2.count);

        System.out.println(c.b.num);
        System.out.println(c2.b.num);

        System.out.println("进行修改: ");
        c.count = 20;
        System.out.println(c.count);
        System.out.println(c2.count);

        c.b.num = 200;
        System.out.println(c.b.num);
        // 针对 c 进行修改, 就影响到了 c2, c 和 c2 持有的 b 的引用指向的是同一个对象.
        System.out.println(c2.b.num);
    }

    // 实现方式 1
    private static C copy1(C c) {
        return c;
    }

    // 实现方式 2
    private static C copy2(C c) {
        C result = new C();
        result.count = c.count;
        result.b = c.b;
        return result;
    }

    // 实现方式 3
    private static C copy3(C c) {
        C result = new C();
        result.count = c.count;
        // 把 C 类内部持有的 B 也进行了拷贝.
        result.b = new B();
        result.b.num = c.b.num;
        return result;
    }
}
