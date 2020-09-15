package java16_0428;

import java.util.Arrays;

public class Heap {
    // 堆是一个完全二叉树, 通常使用数组来表示.
    // 数组中哪些元素是堆 [0, size)
    // index 表示从哪个下标出发进行调整.
    public static void shiftDown(int[] array, int size, int index) {
        int parent = index;
        // 根据父节点下标, 先找到左子树的下标
        int child = 2 * parent + 1;
        // 循环条件的意思是, 如果 child 对应节点存在, 就继续调整. 如果超过 size 说明
        // 当前的 parent 已经是叶子节点, 没有子节点了.
        while (child < size) {
            // 再去找下右子树对应的节点.
            if (child + 1 < size && array[child + 1] > array[child]) {
                child = child + 1;
            }
            // child 经过上面的条件, 已经不知道指向的是 parent 左子树还是右子树了
            // child 肯定是左右子树中值比较大的那个~

            // 接下来就可以拿刚才 child 位置的元素和 parent 进行对比了, 看看是否符合大堆的要求.
            // 如果不符合大堆 (child 位置的元素比 parent 位置的元素大), 就交换 child 和 parent 位置的元素
            if (array[child] > array[parent]) {
                int tmp = array[child];
                array[child] = array[parent];
                array[parent] = tmp;
            } else {
                // 当前 child 和 parent 的关系已经符合大堆的要求了, 说明就调整完毕了
                break;
            }
            // 下次循环之前, 需要先更新 parent 和 child
            parent = child;
            child = 2 * parent + 1;
        }
    }

    public static void createHeap(int[] array, int size) {
        // 从后往前遍历, 从最后一个非叶子节点出发, 依次进行向下调整.
        // size - 1 得到的是最后一个元素的下标.
        // 再次 - 1 / 2 是为了根据最后一个节点的下标, 找到该节点对应的父节点下标.
        for (int i = (size - 1 - 1) / 2; i >= 0; i--) {
            shiftDown(array, size, i);
        }
    }

    public static void main(String[] args) {
        int[] array = {9, 5, 2, 7, 3, 6, 8};
        createHeap(array, array.length);
        System.out.println(Arrays.toString(array));
    }
}
