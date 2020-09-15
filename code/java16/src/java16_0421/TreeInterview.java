package java16_0421;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeInterview {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        result.addAll(postorderTraversal(root.left));
        result.addAll(postorderTraversal(root.right));
        result.add(root.val);
        return result;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            // 如果都是空树, 认为相等
            return true;
        }
        // if ((p == null && q != null) || (p != null && q == null)) {
        if (p == null || q == null) {
            // 如果两个树一个为空一个非空, 那么最终结果肯定不相等.
            // 由于前面还有一个 p q 均为空的条件限制, 此处 这个条件的 含义是, p 和 q 一个为空一个非空
            return false;
        }
        // 还是按照递归的方式把问题拆分:
        // 判定 p 和 q 是否相等 => p.val == q.val && p.left 和 q.left 相等, && p.right 和 q.right 相等
        return p.val == q.val && isSameTree(p.left, q.left)
                && isSameTree(p.right, q.right);
    }

    public boolean isSubtree(TreeNode s, TreeNode t) {
        // 先序遍历 s 这个树, 访问到某个节点时, "访问" 操作就是 isSameTree 的判定
        if (s == null) {
            return false;
        }
        // 判定 s 中是否包含 t => 先看 s 和 t 是否相同 || s.left 包含 t || s.right 包含 t
        // 访问当前节点
        return isSameTree(s, t) || isSubtree(s.left, t) ||
            isSubtree(s.right, t);
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            // root 就是一个叶子节点
            return 1;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return 1 + (leftDepth > rightDepth ? leftDepth : rightDepth);
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return true;
        }
        // 求当前节点左右子树的高度
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        // 当前这个树平衡 => 当前节点左右子树高度差 <= 1 && 左子树是平衡的 && 右子树是平衡的
        return (leftDepth - rightDepth <= 1 && leftDepth - rightDepth >= -1)
                && isBalanced(root.left) && isBalanced(root.right);
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 把判定 root 是否为对称转换成判定 root.left 和 root.right 是否为对称
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        // 判定 t1 和 t2 是否对称 => t1.val == t2.val && t1.left 和 t2.right 是否对称
        //                                      && t1.right 和 t2.left 是否对称
        return (t1.val == t2.val) && isMirror(t1.left, t2.right)
                    && isMirror(t1.right, t2.left);
    }

    public void levelOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        // 1. 创建一个队列, 初始情况下吧 根节点 加入到队列中
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 2. 进入循环, 循环结束条件为队列为空
        while (!queue.isEmpty()) {
            // a) 取出队首元素
            TreeNode cur = queue.poll();
            // b) 访问当前元素
            System.out.print(cur.val + " ");
            // c) 把当前节点的左右子树分别入队列
            if (cur.left != null) {
                queue.offer(cur.left);
            }
            if (cur.right != null) {
                queue.offer(cur.right);
            }
        }
    }

    public static TreeNode build() {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node22 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node33 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node44 = new TreeNode(4);

        node1.left = node2;
        node1.right = node22;
        node2.left = node3;
        node2.right = node33;
        node3.left = node4;
        node3.right = node44;
        return node1;
    }

    public static TreeNode build2() {
        TreeNode A = new TreeNode(1);
        TreeNode B = new TreeNode(2);
        TreeNode C = new TreeNode(3);
        TreeNode D = new TreeNode(4);
        TreeNode E = new TreeNode(5);
        TreeNode F = new TreeNode(6);
        TreeNode G = new TreeNode(7);

        A.left = B;
        A.right = C;
        B.left = D;
        B.right = E;
        E.left = G;
        C.right = F;
        return A;
    }

    public static void main(String[] args) {
//        TreeNode root = build();
//        TreeInterview interview = new TreeInterview();
//        boolean ret = interview.isBalanced(root);
//        System.out.println(ret);

        TreeNode root = build2();
        TreeInterview interview = new TreeInterview();
        interview.levelOrder(root);
    }
}
