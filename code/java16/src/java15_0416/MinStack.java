package java15_0416;

import java.util.Stack;

public class MinStack {
    // A 中是按照正常的栈来存储元素
    Stack<Integer> A = new Stack<>();
    // B 中每个元素都是 A 中对应元素个数情况下的最小值
    Stack<Integer> B = new Stack<>();
    // A 和 B 中的元素要同步变化.

    /** initialize your data structure here. */
    public MinStack() {

    }

    public void push(int x) {
        A.push(x);
        // 判断一下 B 中的元素要入啥
        if (B.empty()) {
            B.push(x);
            return;
        }
        int min = B.peek();  // 取出上一轮的最小值.
        if (x < min) {
            // 新插入的元素比原来的最小值还小, 把 x 入 B 栈
            min = x;
        }
        // 经过上面的条件判断之后, min 的值一定就是挑战者和擂主之间的最小值
        B.push(min);
    }

    public void pop() {
        if (A.empty()) {
            return;
        }
        A.pop();
        B.pop();
    }

    public int top() {
        if (A.empty()) {
            return 0;
        }
        return A.peek();
    }

    public int getMin() {
        if (B.empty()) {
            return 0;
        }
        return B.peek();
    }
}
