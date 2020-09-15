package java16_0409;

import java.util.Arrays;

public class TestString {
    public static void main(String[] args) {
//        String str1 = "hello worldwor";
//        String str2 = "wor";

        // 通过 contains 方法可以判定是否是包含关系
        // System.out.println(str1.contains(str2));

        // 通过 indexOf 方法不光能判定子串是否存在, 还可以查看子串所在的位置
        // 返回值表示 str2 在 str1 这个字符串中所在的下标位置.
        // 如果存在多组子串, 返回的是最左侧子串的下标
        // System.out.println(str1.indexOf(str2));
        // 从右往左找匹配的子串
        // System.out.println(str1.lastIndexOf(str2));

        // startsWith, endsWith
//        System.out.println(str1.startsWith("hello"));
//        System.out.println(str1.endsWith("hello"));

//        String result = str1.replaceAll("world", "java");
//        System.out.println(str1);
//        System.out.println(result);

        // 切分之后得到 3 个部分, 还是 4 个部分?
//        String str1 = "aaa,bbb,,ccc";
//        String[] result = str1.split(",");
//        System.out.println(Arrays.toString(result));
        // String str = "192.168.1.1";
        // . 在正则表达式中具有特定含义. 此处的切分并不是按照 "." 这样的文本切分的.
        // 如果想按照 . 的文本形式切分, 就需要进行转义.
        // 正则中需要通过 \. 来查找 文本 .
        // Java 代码中字符串中又不能直接写 \ , 需要 \\ 表示一个文本 \
        // | * + ... 都有类似的情况
        // Java13 版本中引入了 raw string 这样的语法特性, 从而就可以避免这样的双重转义.
//        String[] result = str.split("\\.");
//        System.out.println(Arrays.toString(result));

//        String str = "name=张三&age=18";
//        String[] result = str.split("&");
//        for (String tmp : result) {
//            // 预期 tmp2 数组中包含两个元素
//            String[] tmp2 = tmp.split("=");
//            System.out.println(tmp2[0] + ": " + tmp2[1]);
//        }

//        // 字符串截取子串 substring
//        String str = "hello world";
//        // 前闭后开区间. 子串要包含 6 位置的元素. 不包含 9 位置的元素
//        System.out.println(str.substring(6, 9));
//        // 表示从 6 开始, 一直到字符串最后, 都是要截取的部分
//        System.out.println(str.substring(6));
//        String str = " \n\t     abcd  \n   ";
//        System.out.println("[" + str + "]");
//        System.out.println("[" + str.trim() + "]");

        // String 是不可变对象. toUpperCase 和  toLowerCase 都没有修改 str 本身的内容. 而是生成了新字符串
//        String str = "Hello";
//        System.out.println(str.toUpperCase());
//        System.out.println(str.toLowerCase());

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            // 记得 str 是不可变对象. += 操作也是创建了新的对象.
            // StringBuilder 的 append 操作就是修改当前字符串, 把新内容追加在后面.
            str.append("abcd");
        }
        System.out.println(str);
    }
}
