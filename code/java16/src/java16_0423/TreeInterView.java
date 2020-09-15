package java16_0423;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeInterView {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public boolean isComplete(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 这个变量为 true 表示当前在第一阶段. 为 false 表示当前在第二阶段
        boolean isFirstStep = true;
        // 针对这个树进行层序遍历.
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (isFirstStep) {
                // 第一阶段, 要求任意节点必须具备两个子树
                if (cur.left != null && cur.right != null) {
                    // 当前节点有两个子树, 直接把子树入队列, 继续往后遍历
                    queue.offer(cur.left);
                    queue.offer(cur.right);
                } else if (cur.left == null && cur.right != null) {
                    // 当前节点右子树非空, 左子树为空, 这时候就一定不是完全二叉树.
                    // 对应板书上的 d)
                    return false;
                } else if (cur.left != null && cur.right == null) {
                    // 当前节点左子树非空, 右子树为空, 此时进入第二阶段.
                    // 对应板书上的 b)
                    isFirstStep = false;
                    queue.offer(cur.left);
                } else {
                    // 当前节点左右子树都为空, 还是要进入第二阶段
                    // 对应板书上的 a) d)
                    isFirstStep = false;
                }
            } else {
                // 第二阶段, 要求任意节点必须没有子树
                if (cur.left != null || cur.right != null) {
                    // 找到了反例, 直接就判定为不是完全二叉树即可.
                    // 对应板书的 e)
                    return false;
                }
            }
        }  // end while
        // 树遍历完了, 也没有找到反例, 最终就认为是完全二叉树.
        return true;
    }

    // 把结果作为一个成员变量来使用.
    // 递归过程中每层递归方法都可以很方便的操作同一个结果.
    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return result;
        }
        // 对树进行先序遍历, 递归时需要加上一个 层数 作为参数(层数直接从 0 开始算, 方便和 List 下标对应)
        result.clear(); // 最好加上 clear 防止多组数据的时候, 上次操作 result 中的残留值, 对本次操作造成干扰
        levelOrderHelper(root, 0);
        return result;
    }

    private void levelOrderHelper(TreeNode root, int level) {
        if (level == result.size()) {
            // 当前 level 在 result 中没有对应的行. 加入一行
            // 防止下面的 get 操作出现下标越界
            result.add(new ArrayList<>());
        }
        List<Integer> curRow = result.get(level);
        curRow.add(root.val); // 先序遍历的 "访问" 操作.
        if (root.left != null) {
            levelOrderHelper(root.left, level + 1);
        }
        if (root.right != null) {
            levelOrderHelper(root.right, level + 1);
        }
    }
}
