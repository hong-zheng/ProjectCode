package java16_0414;

public class MyStack2 {
    static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }
    // 使用链表也能实现栈. 用链表头部表示栈顶, 链表尾部表示栈底.
    // 只要知道头结点, 就能获取到后面的所有节点.
    // 一般表示链表. 都是使用一个头结点应用来表示整个链表.
    // 此处相当于是 "用局部表示整体" 这种修辞手法叫做 "借代"

    // 链表有八种链表.
    // 1. 不带环的 vs 带环的
    // 2. 单向的 vs 双向的
    // 3. 带傀儡节点的 vs 不带傀儡节点的.
    // 此处为了方便, 写一个带傀儡节点的链表
    Node head = new Node(-1);

    public void push(int value) {
        // 头插
        Node newNode = new Node(value);
        newNode.next = head.next;
        head.next = newNode;
    }

    public Integer pop() {
        // 头删
        Node toDelete = head.next;
        if (toDelete == null) {
            // 链表为空, 删除失败
            return null;
        }
        head.next = toDelete.next;
        return toDelete.val;
    }

    public Integer peek() {
        if (head.next == null) {
            return null;
        }
        return head.next.val;
    }
}
