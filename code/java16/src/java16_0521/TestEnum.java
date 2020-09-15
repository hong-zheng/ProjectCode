package java16_0521;

import java.util.Arrays;

// MALE, FEMALE 都可以理解成 Sex 类型的对象.
// enum 可以理解成是一种特殊的 类. 这个类中没有属性, 也没有方法(只有一些内置的属性方法).
enum Sex {
    MALE,
    FEMALE,
    UNKNOWN,
}

public class TestEnum {
    public static final int Male = 0;
    public static final int Female = 1;
    public static final int Unknown = 2;

    public static void main(String[] args) {

        Sex s = Sex.MALE;
        System.out.println(s);

        // 不能写成
        // Sex s2 = new Sex();

        // Sex.values() 得到一个数组. 数组中的元素就是
        // 当前这个枚举中所有可能的选项.
        System.out.println(Arrays.toString(Sex.values()));
    }
}
