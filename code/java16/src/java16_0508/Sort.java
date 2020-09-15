package java16_0508;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Sort {
    public static void quickSortByLoop(int[] array) {
        // 栈中保存的元素相当于当前要进行 partition 操作的范围下标
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(array.length - 1);
        while (!stack.isEmpty()) {
            int right = stack.pop();
            int left = stack.pop();
            if (left >= right) {
                // 区间为空或者区间中只有一个元素. 此时不需要进行 partition 操作
                continue;
            }
            int index = partition(array, left, right);
            // 把右子树入栈 [index + 1, right]
            stack.push(index + 1);
            stack.push(right);
            // 把左子树入栈 [left, index - 1]
            stack.push(left);
            stack.push(index - 1);
        }
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

    private static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void mergeSort(int[] array) {
        // [0, length)
        mergeSortHelper(array, 0, array.length);
    }

    private static void mergeSortHelper(int[] array, int left, int right) {
        // [left, right)
        // if (left >= right) {
        if (right - left <= 1) {
            // 当前区间中有 0 个或者 1 个元素. 不需要进行排序
            return;
        }

        // 针对 [left, right) 区间, 分成对等的两个区间
        int mid = (left + right) / 2;
        // 两个区间分别就是
        // [left, mid)
        // [mid, right)
        mergeSortHelper(array, left, mid);
        mergeSortHelper(array, mid, right);
        // 通过上面的递归, 认为这两个区间都被排好序了. 接下来就可以进行合并了
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        // 当前有两个有序数组
        // [left, mid)
        // [mid, right)
        int cur1 = left;
        int cur2 = mid;
        // 临时空间需要能容纳下 两个数组合并后的结果
        int[] output = new int[right - left];
        int outputIndex = 0; // 当前 output 中被插入了几个元素.

        while (cur1 < mid && cur2 < right) {
            // 此处必须是 <= , 不能是 < .
            // 如果是 < 是无法保证稳定性的.
            if (array[cur1] <= array[cur2]) {
                // 把 cur1 位置的元素插入到 output 中
                output[outputIndex] = array[cur1];
                cur1++;
                outputIndex++;
            } else {
                output[outputIndex] = array[cur2];
                cur2++;
                outputIndex++;
            }
        }
        while (cur1 < mid) {
            output[outputIndex] = array[cur1];
            cur1++;
            outputIndex++;
        }
        while (cur2 < right) {
            output[outputIndex] = array[cur2];
            cur2++;
            outputIndex++;
        }
        // 最后一步要把数据从临时空间中拷贝回原来的数组中.
        for (int i = 0; i < right - left; i++) {
            array[left + i] = output[i];
        }
    }

    public static void mergeSortByLoop(int[] array) {
        // gap 表示当前每个组中的元素个数.
        for (int gap = 1; gap < array.length; gap *= 2) {
            for (int i = 0; i < array.length; i += 2*gap) {
                // 每次执行一遍循环体, 相当于把两个长度为 gap 的相邻组进行了合并
                // [i, i + gap)
                // [i + gap, i + 2 * gap)
                int left = i;
                int mid = i + gap;
                int right = i + 2 * gap;
                if (mid > array.length) {
                    mid = array.length;
                }
                if (right > array.length) {
                    right = array.length;
                }
                merge(array, left, mid, right);
            }
        }
    }

    public static void main(String[] args) {
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        arrayList.add(9);
//        arrayList.add(5);
//        arrayList.add(2);
//        arrayList.add(7);
//        arrayList.add(3);
//        arrayList.add(6);
//        Collections.sort(arrayList);
//        System.out.println(arrayList);
//
//        int[] array = {9, 5, 2, 7, 3, 6, 8};
//        Arrays.sort(array);
//        System.out.println(Arrays.toString(array));

        int[] array = {9, 5, 2, 7, 3, 6, 8};
        // quickSortByLoop(array);
        mergeSort(array);
        System.out.println(Arrays.toString(array));
    }
}
