package java16_0726;

public class Test2 {
    // 递归求阶乘
    public static int factor(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factor(n - 1);
    }

    public static void main(String[] args) {
        int ret = factor(5);
        System.out.println(ret);
    }
}
