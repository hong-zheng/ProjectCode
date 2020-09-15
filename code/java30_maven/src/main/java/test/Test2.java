package test;

class Node {
    public int val;
    public Node left;
    public Node right;
}

public class Test2 {
    public static Node build() {
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        Node d = new Node();
        Node e = new Node();
        Node f = new Node();
        Node g = new Node();
        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        c.right = f;
        e.left = g;
        return a;
    }
    Node a = new Node();

    public static void main(String[] args) {
        Node root = build();
    }
}
