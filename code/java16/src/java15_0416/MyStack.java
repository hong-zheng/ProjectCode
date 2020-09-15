package java15_0416;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack {
    Queue<Integer> A = new LinkedList<>();
    Queue<Integer> B = new LinkedList<>();

    /** Initialize your data structure here. */
    public MyStack() {

    }

    /** Push element x onto stack. */
    public void push(int x) {
        // 就直接把元素往 A 中入队即可
        A.offer(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        // 如果栈为空
        if (empty()) {
            return 0;
        }
        // 需要把 A 中的所有元素都往 B 中倒腾. 倒腾到只剩一个元素的时候, 再把最后一个元素删掉即可
        while (A.size() > 1) {
            Integer cur = A.poll();
            B.offer(cur);
        }
        // 当循环结束的时候, A 中就只剩下一个元素的了. 这个元素就是要出栈的栈顶元素
        int top = A.poll();
        // 完成操作之后, 要记得交换 A 和 B, 保证下次入栈的时候是往 A 中来入元素
        swapAB();
        return top;
    }

    private void swapAB() {
        Queue<Integer> tmp = A;
        A = B;
        B = tmp;
    }

    /** Get the top element. */
    public int top() {
        if (empty()) {
            return 0;
        }
        // 要把 A 中的元素往 B 中倒腾, 倒腾到只剩一个元素的时候就结束
        while (A.size() > 1) {
            int cur = A.poll();
            B.offer(cur);
        }
        // 循环结束之后, A 中就只剩下一个元素了
        // 最后这个元素就是栈的栈顶元素
        int top = A.poll();
        B.offer(top);       // 这个代码是和上一个 pop 操作唯一的区别.
        swapAB();
        return top;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return A.isEmpty() && B.isEmpty();
    }
}
