package java16_0428;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TestPriorityQueue {

    public static void main(String[] args) {
        // 这个代码中创建了一个匿名内部类.
        // 此处不知道类名是啥, 但是知道这个类实现了 Comparator 接口
        PriorityQueue<Integer> queue = new PriorityQueue<>((Integer o1, Integer o2) -> {
            return o2 - o1;
        });
        queue.offer(9);
        queue.offer(5);
        queue.offer(2);
        queue.offer(7);
        queue.offer(3);
        queue.offer(6);
        queue.offer(8);

        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            System.out.println(cur);
        }
    }
}
