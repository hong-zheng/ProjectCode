package java16_0801;

class Node {
    public int val;
    public Node left;
    public Node right;

    public Node(int val) {
        this.val = val;
    }
}

public class Test {
    public static Node build() {
        // Vim 一下复制很多, 这就是用 Vim 的快捷键
        // 手动构造一个树
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        Node g = new Node(7);

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        e.left = g;
        c.right = f;
        return a;
    }

    public static void main(String[] args) {
        Node root = build();
        root.right = null;
    }
}
