package java16_0411;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TestCollection {
    public static void main(String[] args) {
        // Collection 是接口. add, remove, contains 这些方法都是抽象方法.
        // 这些方法在执行的时候, 具体的行为取决于 collection 对应的真实对象的类型.


        // 1. 实例化一个 Collection 对象. Collection 是一个接口. 必须要 new 一个对应的类作为实例才可以
        Collection<String> collection = new ArrayList<>();
        // 2. 使用 size / isEmpty
        System.out.println(collection.isEmpty());
        System.out.println(collection.size());
        // 3. 使用 add 添加元素
        collection.add("我");
        collection.add("爱");
        collection.add("Java");

        // 4. 再次使用 isEmpty 和 size
        System.out.println("==========================");
        System.out.println(collection.isEmpty());
        System.out.println(collection.size());

        // 5. toArray 把集合转换成数组
        //    String 也是继承自 Object . array 看似是一个 Object 数组, 其实是 String 数组.
        System.out.println("==========================");
        Object[] array = collection.toArray();
        System.out.println(Arrays.toString(array));
        System.out.println(collection);

        // 6. for each 遍历 collection
        //    比较简单的遍历方式. 如果要想用 while 或者 普通 for, 需要搭配迭代器来使用.
        System.out.println("==========================");
        for (String s : collection) {
            System.out.println(s);
        }

        // 7. 判定元素是否存在. contains 内部会拿参数的对象和集合中保存的对象按值比较(而不是按身份).
        System.out.println("==========================");
        boolean ret = collection.contains("我");
        System.out.println(ret);

        // 8. 使用删除方法来删除指定元素
        System.out.println("==========================");
        collection.remove("爱");
        for (String s : collection) {
            System.out.println(s);
        }

        // 9. 使用 clear 来清空所有元素
        collection.clear();
        System.out.println(collection.isEmpty());
        System.out.println(collection.size());
    }
}
