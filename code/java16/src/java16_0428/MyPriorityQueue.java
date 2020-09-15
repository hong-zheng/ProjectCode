package java16_0428;

public class MyPriorityQueue {
    private int[] array = new int[100]; // 暂时不考虑扩容
    private int size = 0;   // [0, size) 表示有效元素区间.

    public void offer(int x) {
        // 1. 先把 x 放到数组元素的末尾.
        array[size] = x;
        size++;
        // 2. 把 x 进行向上调整即可
        // 第一个参数表示用来承载堆的数组
        // 第二个参数表示数组中有效元素的个数.
        // 第三个参数表示从哪个位置进行向上调整.
        shiftUp(array, size, size - 1);
    }

    private void shiftUp(int[] array, int size, int index) {
        int child = index;
        int parent = (child - 1) / 2;
        // 如果 child 为 0, 说明 child 已经是根节点了. 根节点就没有父节点.
        // 调整到这里已经就到顶了.
        while (child > 0) {
            // 比较当前 child 和 parent 之间的大小关系, 看看是否符合大堆.
            if (array[parent] < array[child]) {
                // 交换父子元素的内容
                int tmp = array[parent];
                array[parent] = array[child];
                array[child] = tmp;
            } else {
                break;
            }
            child = parent;
            parent = (child - 1) / 2;
        }
    }

    public Integer poll() {
        if (size <= 0) {
            return null;
        }
        int ret = array[0];

        // 如何删除队首元素(根节点) 呢?
        // 把未知问题转换成已知问题.
        // 1. 把最后一个元素的值填入到 0 号元素上.
        array[0] = array[size - 1];
        // 2. 删除最后一个元素
        size--;
        // 3. 从 0 下标开始进行向下调整
        shiftDown(array, size, 0);
        return ret;
    }

    private void shiftDown(int[] array, int size, int index) {
        int parent = index;
        int child = 2 * parent + 1;
        while (child < size) {
            if (child + 1 < size && array[child + 1] > array[child]) {
                child = child + 1;
            }
            if (array[child] > array[parent]) {
                int tmp = array[child];
                array[child] = array[parent];
                array[parent] = tmp;
            } else {
                break;
            }
            parent = child;
            child = 2 * parent + 1;
        }
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return array[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        MyPriorityQueue queue = new MyPriorityQueue();
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
