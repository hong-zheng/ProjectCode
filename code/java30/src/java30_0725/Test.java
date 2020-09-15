package java30_0725;

class A {
    A ref = null;
}

public class Test {
    public static void main(String[] args) {
        A a1 = new A();
        A a2 = new A();
        a1.ref = a2;
        a2.ref = a1;
    }
}
