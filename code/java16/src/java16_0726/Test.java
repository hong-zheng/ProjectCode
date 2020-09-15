package java16_0726;

public class Test {
    // t2 就是成员变量, t2 这个引用就是在堆上.
    Test t2 = new Test();
    // t3 是静态变量, 在方法区中
    static Test t3 = new Test();

    public static void main(String[] args) {
        // 此时 t 是局部变量, t 这个引用就是在栈上.
        Test t = new Test();
    }
}
