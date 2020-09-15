package java16_0510;

import java.util.*;

public class MapSetInterview {
    static class Test {
        public ArrayList<Integer> data;

        public Test() {
            data = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Test{" +
                    "data=" + data +
                    '}';
        }
    }

    public int singleNumber(int[] nums) {
        // 1. 统计每个数字出现的次数
        //    key 表示具体的数字. value 表示该数字在数组中出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : nums) {
            Integer count = map.get(x);
            if (count == null) {
                // x 在之前没有出现过, 就把新的键值对插入到 map 中
                map.put(x, 1);
            } else {
                map.put(x, count + 1);
            }
        }
        // System.out.println(map);
        // 2. 遍历 Map, 找到只出现一次的数字
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(1)) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public int singleNumber2(int[] nums) {
        // 使用按位异或的方式来解决
        int ret = 0;
        for (int x : nums) {
            ret ^= x;
        }
        return ret;
    }

    public int[] singleNumber3(int[] nums) {
        // 1. 先针对所有数字进行 ^ 操作
        int ret = 0;
        for (int x : nums) {
            ret ^= x;
        }
        // 2. 在 ret 中找一个不为 0 的 bit 位
        int bit = 0;
        for (; bit < 32; bit++) {
            if ((ret & (1 << bit)) > 0) {
                break;
            }
        }
        // 此时 bit 对应的位就是为 1
        // a 和 b 是最终要找的结果
        int a = 0;
        int b = 0;
        // 3. 根据 bit 对应的位分组进行 ^ 运算
        for (int x : nums) {
            if ((x & (1<<bit)) > 0) {
                a ^= x;
            } else {
                b ^= x;
            }
        }
        return new int[]{a, b};
    }

    private static Test copy(Test t) {
        // 浅拷贝
        Test t2 = new Test();
        t2.data = t.data;
        return t2;
    }

    private static Test deepCopy(Test t) {
        // 深拷贝
        Test t2 = new Test();
        t2.data.addAll(t.data);
        return t2;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        // 使用 Map 维护一个 旧节点 -> 新节点之间的映射关系.
        Map<Node, Node> map = new HashMap<>();
        for (Node cur = head; cur != null; cur = cur.next) {
            map.put(cur, new Node(cur.val));
        }
        // 下一步就可以维护 next 和 random 之间的指向了
        for (Node cur = head; cur != null; cur = cur.next) {
            // cur 是旧链表节点, newCur 是新链表的对应节点
            Node newCur = map.get(cur);
            newCur.next = map.get(cur.next);
            newCur.random = map.get(cur.random);
        }
        return map.get(head);
    }

    public int numJewelsInStones(String J, String S) {
        // J 是宝石, S 是石头
        // 遍历 S, 看 S 中的字符是否在 J 中出现. 根据出现情况来计数即可
        int count = 0;

        // 这是一种简单粗暴的方法. 但是 String.contains 时间复杂度 O(N)
        // 整体的复杂度就是 O(N^2)
//        for (int i = 0; i < S.length(); i++) {
//            char c = S.charAt(i);
//            if (J.contains(c + "")) {
//                count++;
//            }
//        }

        // 改进思路: 不是使用 String.contains, 而是使用 Set.contains
        // 如果是 TreeSet.contains 时间复杂度 O(logN)
        // 如果是 HashSet.contains 时间复杂度 O(1)
        // 1. 先把 J 中的字符倒腾到 Set 中
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < J.length(); i++) {
            set.add(J.charAt(i));
        }
        // 2. 再去遍历 S, 判定 S 中的字符是否在 Set 中存在
        for (int i = 0; i < S.length(); i++) {
            if (set.contains(S.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public List<String> topKFrequent(String[] words, int k) {
        // 1. 统计每个单词出现的次数
        Map<String, Integer> map = new HashMap<>();
        for (String x : words) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        // 2. 把所有的 map 中的 key 倒腾到一个 ArrayList 中, 这相当于是进行了去重
        //    最终输出结果肯定不包含重复的单词的.
        List<String> result = new ArrayList<>(map.keySet());
        // 3. 根据单词出现的次数针对 result 进行排序了
        //    按照出现次数降序排序. 如果两个单词出现次数一样, 再按字典序排序
        Collections.sort(result, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 在这个匿名内部类里, 可以访问到上面的 map 变量.
                // 变量捕获
                int count1 = map.get(o1);
                int count2 = map.get(o2);
                if (count1 == count2) {
                    return o1.compareTo(o2);
                }
                return count2 - count1;
            }
        });
        // 4. 根据 k 这个值, 取出前 k 个元素
        return result.subList(0, k);
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.data.add(1);
        t.data.add(2);
        t.data.add(3);

        Test t2 = deepCopy(t);
        t.data.add(4);
        System.out.println(t);
        System.out.println(t2);
    }
}
