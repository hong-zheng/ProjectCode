package java16_0414;

import java.util.*;

public class TestStackAndQueue {
    public static void main(String[] args) {
//        // Stack 是一个 class
//        Stack<Integer> stack = new Stack<>();
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//        stack.push(4);
//
//        // 标准库中的 Stack 如果针对空栈进行 pop 或者 peek 就会抛出一个 EmptyStackException
//        while (!stack.empty()) {
//            Integer cur = stack.pop();
//            System.out.println(cur);
//        }

        // 由于标准库中的 Stack 是一个继承自 Vector 的类. Vector 能执行的操作, Stack 一样也能执行.
        // Stack 并没有起到 限制灵活性 的效果, 反而增加了代码的复杂程度
        // 实际使用 Stack 的时候, 尽量避免使用这些本不应该是 Stack 该有的操作
//        stack.add(2, 100);
//        stack.get(0);
//        stack.contains(2);

        // 标准库中的队列, Queue 是一个 interface
        // 只能实例化一个实现 Queue 接口的类
        // 标准库中的队列是基于链表实现的.
//        Queue<Integer> queue = new LinkedList<>();
//        queue.add(1);
//        queue.add(2);
//        queue.add(3);
//        queue.add(4);
//
//        while (true) {
//            Integer cur = queue.remove();
//            if (cur == null) {
//                break;
//            }
//            System.out.println(cur);
//        }

        Deque<Integer> deque = new LinkedList<>();
        deque.addFirst(1);
        deque.addLast(2);
    }
}
