package test;

class A {
    public A() {
        System.out.println("A.构造");
    }

    {
        System.out.println("A.{}");
    }

    static {
        System.out.println("A.static.{}");
    }
}

class B extends A {
    public B() {
        System.out.println("B.构造");
    }

    {
        System.out.println("B.{}");
    }

    static {
        System.out.println("B.static.{}");
    }
}

public class InitOrder {
    public static void main(String[] args) {
        System.out.println("开始");
        new B();
        System.out.println("结束");
    }
}
