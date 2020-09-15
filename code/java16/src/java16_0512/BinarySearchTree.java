package java16_0512;

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
        // 1. 如果树本身是空树, 直接让 root 指向新节点即可.
        if (root == null) {
            root = new Node(key, value);
            return root;
        }
        // 2. 先找到 key 所在的位置.
        Node cur = root;
        Node prev = null;
        while (cur != null) {
            if (key < cur.key) {
                // 继续往左找
                prev = cur;
                cur = cur.left;
            } else if (key > cur.key) {
                // 继续往右找
                prev = cur;
                cur = cur.right;
            } else {
                // 找到了 key 相同的元素.
                // 针对 key 重复的情况, 处理方式有很多种.
                // a) 让插入操作直接失败.
                // b) 不创建新节点, 把当前节点的 value 给改成新的 value [此处采取这种方案]
                cur.value = value;
                return cur;
            }
        }
        // 3. 当循环结束, cur 就为 null 了. 此时意味着找到了合适的插入位置.
        //    要把新节点插入到 prev 节点的下面
        Node newNode = new Node(key, value);
        if (newNode.key < prev.key) {
            prev.left = newNode;
        } else {
            prev.right = newNode;
        }
        return newNode;
    }

    public void remove(int key) {
        // 先找到要删除的节点位置. 找的同时记录下该节点父节点位置.
        Node cur = root;
        Node parent = null;
        while (cur != null) {
            if (key < cur.key) {
                parent = cur;
                cur = cur.left;
            } else if (key > cur.key) {
                parent = cur;
                cur = cur.right;
            } else {
                // 找到了要删除的节点.
                removeNode(cur, parent);
                return;
            }
        }
        // 没有找到节点, 删除失败.
        return;
    }

    private void removeNode(Node cur, Node parent) {
        // 如果左右子树都没有, 此时归类到 1 或者 2 中均可.
        if (cur.left == null) {
            // 1. 没有左子树的情况
            if (cur == root) {
                // 1.1 如果 cur 为根节点
                root = cur.right;
            } else if (cur == parent.left) {
                // 1.2 cur 不为根节点, 且为父亲的左子树.
                parent.left = cur.right;
            } else if (cur == parent.right) {
                // 1.3 cur 不为根节点, 且为父亲的右子树
                parent.right = cur.right;
            }
        } else if (cur.right == null) {
            // 2. 没有右子树的情况
            if (cur == root) {
                // 2.1 cur 为根节点
                root = cur.left;
            } else if (cur == parent.left) {
                // 2.2 cur 不为根节点, 且为父亲的左子树
                parent.left = cur.left;
            } else if (cur == parent.right) {
                // 2.3 cur 不为根节点, 且为父亲的右子树
                parent.right = cur.left;
            }
        } else {
            // 3. 左右子树都有的情况
            // a) 先找到替罪羊节点, 同时也记录下替罪羊的父亲.
            Node scapeGoat = cur.right;
            Node scapeGoatParent = cur;
            while (scapeGoat.left != null) {
                scapeGoatParent = scapeGoat;
                scapeGoat = scapeGoat.left;
            }
            // 循环结束之后, scapeGoat 就指向了右子树中的最左侧节点.
            // b) 把替罪羊节点的 key 和 value 设置给 cur
            cur.key = scapeGoat.key;
            cur.value = scapeGoat.value;
            // c) 删除替罪羊节点
            if (scapeGoat == scapeGoatParent.left) {
                scapeGoatParent.left = scapeGoat.right;
            } else {
                scapeGoatParent.right = scapeGoat.right;
            }
        }
    }

    public static void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.key + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.key + " ");
        inOrder(root.right);
    }


    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        binarySearchTree.insert(9, 90);
        binarySearchTree.insert(5, 50);
        binarySearchTree.insert(2, 20);
        binarySearchTree.insert(7, 70);
        binarySearchTree.insert(3, 30);
        binarySearchTree.insert(6, 60);
        binarySearchTree.insert(8, 80);

        BinarySearchTree.preOrder(binarySearchTree.root);
        System.out.println();
        BinarySearchTree.inOrder(binarySearchTree.root);
    }
}
