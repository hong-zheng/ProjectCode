package java16_0411;

import java.util.HashMap;
import java.util.Map;

public class TestMap {
    public static void main(String[] args) {
        // 1. 实例化一个 Map
        Map<String, String> map = new HashMap<>();
        // 2. isEmpty size
        System.out.println(map.isEmpty());
        System.out.println(map.size());
        // 3. put 插入若干个键值对. key => value, 反向不行
        map.put("及时雨", "宋江");
        map.put("玉麒麟", "卢俊义");
        map.put("智多星", "吴用");
        map.put("入云龙", "公孙胜");
        // map.put("行者", "李逵");
        // 4. 使用 get 根据 key 找一下 value
        System.out.println(map.get("及时雨"));
        System.out.println(map.get("行者"));
        // 找到 key, 返回对应 value; 如果没找到 key, 返回默认值.
        System.out.println(map.getOrDefault("行者", "武松"));

        // 5. 使用 contains 判定元素是否存在
        //    containsKey 比较高效的. containsValue 比较低效的.
        System.out.println("=====================");
        System.out.println(map.containsKey("及时雨"));
        System.out.println(map.containsValue("宋江"));    // 不推荐使用

        // 6. 循环遍历 Map. 此处的 Entry 表示 "条目" 一个一个的键值对
        //    对于 Map 来说, 保存的元素顺序和插入顺序无关.
        //    Map 内部对于元素顺序有自己的规则
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 7. clear 清空所有元素
        map.clear();
        System.out.println(map.isEmpty());
        System.out.println(map.size());
    }
}
