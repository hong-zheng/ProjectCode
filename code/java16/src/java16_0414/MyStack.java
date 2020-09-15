package java16_0414;

public class MyStack {
    // 栈可以基于顺序表实现, 也可以基于链表实现.
    private int[] array = new int[100];
    // array 中 [0, size) 区间中的元素是栈中的有效元素
    // 0 号元素相当于栈底; size - 1 位置的元素相当于栈顶
    private int size = 0;

    public void push(int value) {
        // 把 value 放到数组末尾. 此处没有考虑扩容的问题.
        array[size] = value;
        size++;
    }

    public Integer pop() {
        if (size <= 0) {
            // 此处的失败可以有两种表示方式.
            // 可以返回非法值, 也可以抛出异常.
            return null;
        }
        int ret = array[size - 1];
        size--;
        return ret;
    }

    public Integer peek() {
        if (size <= 0) {
            return null;
        }
        int ret = array[size - 1];
        return ret;
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);

//        Integer cur = null;
//        while ((cur = myStack.pop()) != null) {
//            System.out.println(cur);
//        }
        while (true) {
            Integer cur = myStack.pop();
            if (cur == null) {
                break;
            }
            System.out.println(cur);
        }
    }
}
