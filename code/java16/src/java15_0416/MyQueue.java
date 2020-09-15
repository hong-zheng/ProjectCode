package java15_0416;

import com.sun.javafx.image.IntPixelGetter;
import com.sun.org.apache.xml.internal.security.Init;

import java.util.Stack;

public class MyQueue {
    private Stack<Integer> A = new Stack<>();
    private Stack<Integer> B = new Stack<>();

    /** Initialize your data structure here. */
    public MyQueue() {

    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        // 要保证所有元素都在 A 中, 然后再把 x 插入到 A 中.
        while (!B.isEmpty()) {
            Integer top = B.pop();
            A.push(top);
        }
        A.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        // 先把 A 中的元素都往 B 中倒腾, 再取 B 的栈顶元素出栈即可.
        if (empty()) {
            return 0;
        }
        while (!A.isEmpty()) {
            Integer top = A.pop();
            B.push(top);
        }
        // 再把 B 中的栈顶元素删除即可.
        return B.pop();
    }

    /** Get the front element. */
    public int peek() {
        if (empty()) {
            return 0;
        }
        while (!A.isEmpty()) {
            Integer top = A.pop();
            B.push(top);
        }
        return B.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return A.isEmpty() && B.isEmpty();
    }
}
