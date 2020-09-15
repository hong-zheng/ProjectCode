package java16_0510;

public class BinarySearchTree {
    static class Node {
        public int key;
        public int value;
        public Node left;
        public Node right;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // 记录树的根节点, 也就知道了整个树
    // 当 root 为 null 表示这是空树
    private Node root = null;

    public Node find(int key) {
        Node cur = root;
        while (cur != null) {
            if (key < cur.key) {
                // 左子树中继续查找
                cur = cur.left;
            } else if (key > cur.key) {
                // 右子树中继续查找
                cur = cur.right;
            } else {
                return cur;
            }
        }
        return null;
    }

    public Node insert(int key, int value) {
        return null;
    }

    public void remove(int key) {

    }
}
