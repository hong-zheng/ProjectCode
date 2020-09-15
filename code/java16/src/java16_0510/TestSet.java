package java16_0510;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestSet {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        // 1. add 插入元素, add 多个相同元素, 最终在 Set 中只有一份. (去重)
        set.add("java");
        set.add("java");
        set.add("java");
        set.add("c++");
        set.add("Python");
        set.add("JS");
        // 2. Set 典型应用, 判定某个元素是否在 Set 中存在.
        System.out.println(set.contains("java"));
        // 3. 删除元素
        set.remove("c++");
        System.out.println(set.contains("c++"));
        // 4. 打印所有元素
        System.out.println(set);
        // 5. 使用 for each 遍历
        for (String s : set) {
            System.out.println(s);
        }
        // 6. 使用迭代器来遍历集合类
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
