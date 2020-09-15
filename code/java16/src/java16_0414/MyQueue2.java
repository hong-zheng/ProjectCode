package java16_0414;

public class MyQueue2 {
    private int[] array = new int[100];
    // [head, tail) 初始情况下队列中应该是没有元素的.
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    // 如果插入失败, 返回 false
    // 如果插入成功, 返回 true
    public boolean offer(int value) {
        if (size == array.length) {
            return false;
        }
        array[tail] = value;
        tail++;
        // 下面的两种写法效果是一样的
//        if (tail >= array.length) {
//            tail = 0;
//        }
        tail = tail % array.length;
        size++;
        return true;
    }

    public Integer poll() {
        if (size == 0) {
            // 队列为空, 出队列失败
            return null;
        }
        // 队列非空, 返回 head 位置的元素, 同时 head++ 删除该元素
        int ret = array[head];
        head++;
        if (head >= array.length) {
            head = 0;
        }
        size--;
        return ret;
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return array[head];
    }

    public static void main(String[] args) {
        MyQueue2 myQueue2 = new MyQueue2();
        myQueue2.offer(1);
        myQueue2.offer(2);
        myQueue2.offer(3);
        myQueue2.offer(4);
        while (true) {
            Integer cur = myQueue2.poll();
            if (cur == null) {
                break;
            }
            System.out.println(cur);
        }
    }
}
