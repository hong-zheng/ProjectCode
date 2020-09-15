package java30_0801;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo2 {
    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {
        // 使用方式:
        // 1. 获取值
        int ret = num.get();
        System.out.println(ret);
        // 2. 修改值
        num.set(20);
        System.out.println(num);
        // 3. 进行自增/自减
//        int a1 = num.getAndIncrement(); // n++
//        System.out.println(a1);
        int a2 = num.incrementAndGet(); // ++n
        System.out.println(a2);
    }
}
