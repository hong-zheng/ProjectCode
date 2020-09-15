package java16_0425;

import java16_0418.TestTree;

import java.sql.Connection;
import java.util.Stack;

public class TreeInterview {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 借助这个成员变量保存最近公共祖先.
    private TreeNode lca = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        findNode(root, p, q);
        return lca;
    }

    // [约定] 如果在 root 中能够找到 p 或者 q, 返回 true, 否则返回 false
    private boolean findNode(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }
        // 采取后序遍历的形式进行查找
        int left = findNode(root.left, p, q) ? 1 : 0;
        int right = findNode(root.right, p, q) ? 1 : 0;
        // 访问根节点.
        int mid = (root == p || root == q) ? 1 : 0;
        // 接下来的操作非常神奇, 大家一定看好了~~~
        if (left + right + mid == 2) {
            lca = root;
        }
//        if ((left == 1 && right == 1 && mid == 0)
//            || (left == 1 && right == 0 && mid == 1)
//            || (left == 0 && right == 1 && mid == 1)) {
//            lca = root;
//        }
        return (left + right + mid) > 0;
    }

    // 返回值的含义表示链表的头节点.
    public TreeNode Convert(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            // 只有一个根节点. 得到的链表也就只有一个节点
            return root;
        }
        // 先递归处理左子树, 这个方法一执行, 就相当于把左子树已经完整的转换成双向链表了
        // left 就是左子树链表的头结点.
        TreeNode left = Convert(root.left);
        // 处理根节点, 需要把根节点追加到左子树链表的末尾.
        // 这就相当于链表尾插, 需要先找到前面链表的最后一个节点.
        TreeNode leftTail = left;
        while (leftTail != null && leftTail.right != null) {
            leftTail = leftTail.right;
        }
        // 当上述循环结束之后, leftTail 就是 left 这个链表的最后一个节点.
        // 把当前根节点, 尾插过去
        // 当 left 为 null (没有左子树的时候), leftTail 也就是 null
        if (leftTail != null) {
            leftTail.right = root;
            root.left = leftTail;
        }
        // 最后递归处理右子树, right 就是右子树链表的头结点
        TreeNode right = Convert(root.right);
        if (right != null) {
            // 根节点和右子树连接到一起
            right.left = root;
            root.right = right;
        }
        // 返回整个链表的头结点
        return left != null ? left : root;
    }

    // preorder 这个数组的长度一定和 inorder 是相同的.
    // 为了辅助遍历, 能够搞清楚当前 preorder 中访问到哪个元素了,
    // 还是通过 index 来记录一下
    private int index = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        index = 0;
        // 借助 buildTreeHelper 方法进行递归. 为了更好的描述子树, 在方法中
        // 加入一对参数表示当前子树对应的中序遍历结果的区间.
        return buildTreeHelper(preorder, inorder, 0, inorder.length);
    }

    // [inorderLeft, inorderRight) 表示当前这个子树的中序遍历区间
    private TreeNode buildTreeHelper(int[] preorder, int[] inorder,
                                     int inorderLeft, int inorderRight) {
        if (inorderLeft >= inorderRight) {
            return null;
        }
        if (index >= preorder.length) {
            return null;
        }
        TreeNode newNode = new TreeNode(preorder[index]);
        int pos = find(inorder, inorderLeft, inorderRight, newNode.val);
        index++;  // 只 ++ 一次. 之前写的另外一个版本是 ++ 两次.
        newNode.left = buildTreeHelper(preorder, inorder, inorderLeft, pos);
        newNode.right = buildTreeHelper(preorder, inorder, pos + 1, inorderRight);
        return newNode;
    }

    private int find(int[] inorder, int inorderLeft, int inorderRight, int val) {
        for (int i = inorderLeft; i < inorderRight; i++) {
            if (inorder[i] == val) {
                return i;
            }
        }
        return -1;
    }

    // 用 sb 来表示最终的结果.
    // 递归过程中需要把访问到的字符依次追加到 结果 中.
    // String 不可变对象. 要想修改 String 只能创建新对象.
    private StringBuilder stringBuilder = new StringBuilder();
    public String tree2str(TreeNode t) {
        if (t == null) {
            return "";
        }
        helper(t);
        // 删除最外层的左右括号
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private void helper(TreeNode root) {
        if (root == null) {
            return;
        }
        // 先访问当前节点, 此时的访问操作就是把元素加到 stringBuilder 中
        stringBuilder.append("(");
        stringBuilder.append(root.val);
        helper(root.left);
        if (root.left == null && root.right != null) {
            stringBuilder.append("()");
        }
        helper(root.right);
        stringBuilder.append(")");
    }

    private static TreeNode build() {
        // 通过 build 方法构建一棵树, 返回树的根节点.
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

    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode top = stack.pop();
            System.out.print(top.val + " ");
            if (top.right != null) {
                stack.push(top.right);
            }
            if (top.left != null) {
                stack.push(top.left);
            }
        }
    }

    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (true) {
            // 1. cur 一直往左找, 循环入栈, 直到 cur 为空
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // 2. 取栈顶元素并访问了. 如果遇到空栈, 说明访问完毕了
            if (stack.empty()) {
                break;
            }
            TreeNode top = stack.pop();
            System.out.print(top.val + " ");

            // 3. cur 从 top 的右子树出发, 重复 1 2
            cur = top.right;
        }
    }

    public void postOrder(TreeNode root) {
        if (root == null) {
            return ;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        // prev 用来记录上一个被访问过的节点. 初始情况下没有任何节点被访问过.
        TreeNode prev = null;
        while (true) {
            // 1. cur 循环往左找, 遇到的非空节点都入栈
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // 2. 取栈顶元素, 看看能不能访问这个元素
            //    满足以下两个条件, 才能够访问当前栈顶元素
            //   a) 右子树为空
            //   b) 右子树被访问过
            if (stack.empty()) {
                // 遍历结束
                break;
            }
            TreeNode top = stack.peek();  // 只是取元素判断一下, 并没有真的出栈. 只有这个节点被访问过了, 才能出栈.
            if (top.right == null || top.right == prev) {
                // 可以访问 top 了
                System.out.print(top.val + " ");
                stack.pop();
                prev = top;
            } else {
                // 3. 让 cur 从 top.right 出发, 继续循环 1 和 2
                cur = top.right;
            }
        }
    }

    public static void main(String[] args) {
        TreeNode root = build();
        TreeInterview interview = new TreeInterview();
        interview.postOrder(root);
    }
}
