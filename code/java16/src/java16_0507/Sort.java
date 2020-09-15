package java16_0507;

import java.util.Arrays;

public class Sort {
    public static void selectSort(int[] array) {
        for (int bound = 0; bound < array.length; bound++) {
            // 此时已经借助 bound 把数组分成两个区间了.
            // [0, bound) 已排序区间
            // [bound, size) 待排序区间
            // 接下来就需要在待排序区间中找到最小值, 放到 bound 位置上.
            for (int cur = bound; cur < array.length; cur++) {
                if (array[cur] < array[bound]) {
                    // 以 bound 位置的元素作为擂台.
                    // 拿当前元素和擂台上的元素进行 pk.
                    // pk 赢了就进行交换. 当前是升序排序, 谁小, 谁就赢了
                    swap(array, cur, bound);
                }
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void heapSort(int[] array) {
        // 1. 先建立堆
        createHeap(array);
        int heapSize = array.length;
        for (int i = 0; i < array.length - 1; i++) {
            // 2. 交换堆顶元素和堆中的最后一个元素
            swap(array, 0 , heapSize - 1);
            // 3. 把最后一个元素从堆中删除掉
            heapSize--;
            // 4. 针对当前的堆从 根节点 开始进行向下调整
            shiftDown(array, heapSize, 0);
        }
    }

    private static void createHeap(int[] array) {
        // 从最后一个非叶子节点出发, 从后往前遍历, 依次进行向下调整
        for (int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            shiftDown(array, array.length, i);
        }
    }

    private static void shiftDown(int[] array, int size, int index) {
        int parent = index;
        int child = 2 * parent + 1;
        while (child < size) {
            // 当前是建立大堆. 找出左右子树中的较大值, 然后再和根节点比较.
            if (child + 1 < size && array[child + 1] > array[child]) {
                child = child + 1;
            }
            // 经历这个条件之后, child 是左子树还是右子树, 已经不知道了. 但是child 一定是指向
            // 左右子树的较大值.
            if (array[child] > array[parent]) {
                swap(array, child, parent);
            } else {
                break;
            }
            parent = child;
            child = 2 * parent + 1;
        }
    }

    public static void bubbleSort(int[] array) {
        // 从后往前遍历, 每次找最小元素放前面
        // [0, bound), 已排序区间
        // [bound, size), 待排序区间
        for (int bound = 0; bound < array.length; bound++) {
            // 接下来就需要在待排序区间中找到当前的最小值.
            // 具体的找法就是, 比较相邻元素, 看是否符合升序要求, 不符合就交换元素
            // 边界条件问题, 最好的处理方法就是, 代入个实际的数字验证一下.
            for (int cur = array.length - 1; cur > bound; cur--) {
                if (array[cur - 1] > array[cur]) {
                    swap(array, cur - 1, cur);
                }
            }
        }
    }

    public static void quickSort(int[] array) {
        quickSortHelper(array, 0, array.length - 1);
    }

    // [left, right] 前闭后闭区间. 针对当前范围进行快速排序
    private static void quickSortHelper(int[] array, int left, int right) {
        if (left >= right) {
            // 区间中有 0 个元素或者 1 个元素
            return;
        }
        // 返回值表示 整理之后, 基准值所处在的位置.
        int index = partition(array, left, right);
        // [left, index - 1]
        // [index + 1, right]
        quickSortHelper(array, left, index - 1);
        quickSortHelper(array, index + 1, right);
    }

    private static int partition(int[] array, int left, int right) {
        int baseValue = array[right];
        int i = left;
        int j = right;
        while (i < j) {
            // 1. 先从左往右找到一个大于基准值的元素
            while (i < j && array[i] <= baseValue) {
                i++;
            }
            // 此时 i 指向的位置要么和 j 重合, 要么就是一个比基准值大的元素
            // 2. 再从右往左找到一个小于基准值的元素
            while (i < j && array[j] >= baseValue) {
                j--;
            }
            // 此时 j 指向的元素要么和 i 重合, 要么就是比基准值小的元素
            // 3. 交换 i 和 j 的值
            if (i < j) {
                swap(array, i, j);
            }
        }
        // 当整个循环结束, i 和 j 就重合了. 接下来就把 基准值 位置的元素交换到 i j 重合位置上.
        // 此时 i 和 j 重合位置的元素一定是大于基准值的元素.
        // 为啥 i 和 j 重合位置的元素一定大于基准值呢?
        // 1) i++ 触发了和 j 重合, 上次循环中刚把 i 和 j 交换元素. 交换之后 j 一定是一个大于基准值的元素. i 再往 j 上靠, 结果也一定是指向大于基准值的元素
        // 2) j-- 触发了和 i 重合, 此时 i 一定是指向一个大于基准值的元素(第一个 while 的功能)
        swap(array, i, right);
        return i;
    }

    public static void main(String[] args) {
        int[] array = {9, 5, 2, 7, 3, 6, 8};
        // selectSort(array);
        // heapSort(array);
        // bubbleSort(array);
        quickSort(array);
        System.out.println(Arrays.toString(array));
    }
}
