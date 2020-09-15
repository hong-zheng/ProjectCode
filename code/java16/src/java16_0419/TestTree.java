package java16_0419;

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

        @Override
        public String toString() {
            return "Node{" +
                    "val=" + val +
                    '}';
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

    public static void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    public static void postOrder(Node root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }

//    public static int treeSize = 0;
//    public static void size(Node root) {
//        if (root == null) {
//            return;
//        }
//        treeSize++;  // 访问节点操作
//        size(root.left);
//        size(root.right);
//    }

    // 直接通过 size 返回值来返回 树 节点个数
    public static int size(Node root) {
        if (root == null) {
            // 空树里有 0 个节点
            return 0;
        }
        // 整个树的节点个数 = 根节点个数 + 左子树节点个数 + 右子树节点个数
        return 1 + size(root.left) + size(root.right);
    }

//    public static int leafSize = 0;
//    public static void leafSize(Node root) {
//        if (root == null) {
//            return;
//        }
//        if (root.left == null && root.right == null) {
//            // 如果满足这两个条件, 说明 root 就是 叶子节点
//            leafSize++;
//            return;
//        }
//        leafSize(root.left);
//        leafSize(root.right);
//    }

    public static int leafSize(Node root) {
        // 思路还是借助递归来拆分问题
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        // 求当前这个树叶子节点的个数 = 左子树中叶子节点的个数 + 右子树中叶子节点的个数
        return leafSize(root.left) + leafSize(root.right);
    }

    public static int kLevelSize(Node root, int k) {
        if (root == null || k < 1) {
            return 0;
        }
        if (k == 1) {
            // 第 1 层节点的个数
            return 1;
        }
        // 求树 k 层节点个数 = 左子树的 k - 1 层节点个数 + 右子树的 k - 1 层节点个数
        return kLevelSize(root.left, k - 1) + kLevelSize(root.right, k - 1);
    }

//    public static Node result = null;
//    public static void find(Node root, char toFind) {
//        if (root == null) {
//            return;
//        }
//        if (root.val == toFind) {
//            result = root;
//            return;
//        }
//        find(root.left, toFind);
//        find(root.right, toFind);
//    }

    // 返回值就表示查找到的节点结果.
    // 没找到就返回 null
    public static Node find(Node root, char toFind) {
        if (root == null) {
            return null;
        }
        if (root.val == toFind) {
            return root;
        }
        Node result = find(root.left, toFind);
        if (result != null) {
            return result;
        }
        return find(root.right, toFind);
    }

    public static void main(String[] args) {
        Node root = build();
        // postOrder(root);
        // size(root);
        // System.out.println(treeSize);
//        int size = size(root);
//        System.out.println(size);

//        leafSize(root);
//        System.out.println(leafSize);
        // System.out.println(leafSize(root));

//        find(root, 'H');
//        System.out.println(result);

        Node result = find(root, 'G');
        System.out.println(result);
    }
}
