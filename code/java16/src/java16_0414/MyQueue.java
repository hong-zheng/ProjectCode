package java16_0414;

import sun.applet.AppletViewer;

public class MyQueue {
    // 实现队列也可以使用链表, 也可以使用顺序表.
    // 先用链表的方式实现(更简单一些)
    // 链表尾部作为队尾(方便插入元素), 链表头部作为队首(方便删除元素)
    // 为了更方便的实现尾插操作, 多搞一个引用指向链表的尾部
    static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node head = new Node(-1);
    private Node tail = head;

    // private int size; // 队列中的元素个数

    // 入队列(链表尾插)
    public void offer(int value) {
        Node newNode = new Node(value);
        // 核心操作就是这一行代码
        tail.next = newNode;
        // 插入完毕之后别忘了更新 tail 的指向. 要让 tail 始终指向链表的尾部
        tail = tail.next;
    }

    // 出队列(链表头删)
    public Integer poll() {
        if (head.next == null) {
            // 队列为空, 出队列失败
            return null;
        }
        Node toDelete = head.next;
        head.next = toDelete.next;
        // 不要忘了更新tail
        if (head.next == null) {
            // 此时队列已经为 空 了.
            // 让 tail 能够再指回傀儡节点
            tail = head;
        }
        return toDelete.val;
    }

    // 取队首元素
    public Integer peek() {
        if (head.next == null) {
            // 空队列
            return null;
        }
        return head.next.val;
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        while (true) {
            Integer cur = queue.poll();
            if (cur == null) {
                break;
            }
            System.out.println(cur);
        }
    }
}
