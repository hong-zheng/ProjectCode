package java16_0423;

import java.util.Scanner;

public class BuildTree {
    static class TreeNode {
        public char val; // 根据数据输入结果, 树的每个节点都是一个字符.
        public TreeNode left;
        public TreeNode right;

        public TreeNode(char val) {
            this.val = val;
        }
    }

    // 需要手动处理输入输出的格式
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            // 由于在线 OJ 中的输入数据可能存在多组. 一定要使用循环的方式来处理.
            String line = scanner.next();
            // 读到先序结果后, 就可以构建树了
            // TreeNode root = buildTree(line);
            index = 0;
            TreeNode root = createTreePreOrder(line);
            inOrder(root);  // 打印的每个结果之间都要用空格分割开.
            // 每个输出结果占一行
            System.out.println();
        }
    }

    private static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    private static int index = 0;  // 帮我们在递归中记住当前处理到哪个字符了.
    // 入口
//    private static TreeNode buildTree(String line) {
//        // 输入数据可能存在多组. 每次处理一组新的数据都需要从 0 开始重新计数.
//        index = 0;
//        return createTreePreOrder(line);
//    }

    // 辅助递归的方法
    // 每递归一次, 就处理一个节点. (从字符串中取出一个指定字符)
    private static TreeNode createTreePreOrder(String line) {
        char ch = line.charAt(index);
        if (ch == '#') {
            // 当前节点是个空树
            return null;
        }
        // 如果节点非空, 就可以访问这个节点. 访问操作就是 "创建节点"
        TreeNode node = new TreeNode(ch);
        index++; // 为了处理下一个节点.
        node.left = createTreePreOrder(line);
        index++; // 再去处理下一个节点.
        node.right = createTreePreOrder(line);
        return node;
    }
}
