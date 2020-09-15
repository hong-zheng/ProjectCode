package java16_0418;

public class TestTree {
    static class Node {
        public char val;
        public Node left;
        public Node right;
        // 此处没有 parent. 面试中常见的二叉树都是不带 parent

        public Node(char val) {
            this.val = val;
            // 以下两个代码可以省略.
            // 引用类型的成员变量, 会被默认初始化为 null
            this.left = null;
            this.right = null;
        }
    }

    // 辅助我们构造测试数据的.
    static Node build() {
        // 通过 build 方法构建一棵树, 返回树的根节点.
        Node A = new Node('A');
        Node B = new Node('B');
        Node C = new Node('C');
        Node D = new Node('D');
        Node E = new Node('E');
        Node F = new Node('F');
        Node G = new Node('G');
        A.left = B;
        A.right = C;
        B.left = D;
        B.right = E;
        E.left = G;
        C.right = F;
        return A;
    }

    public static void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void main(String[] args) {
        Node root = build();
        preOrder(root);
    }
}
