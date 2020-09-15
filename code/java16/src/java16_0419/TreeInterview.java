package java16_0419;

import java.util.ArrayList;
import java.util.List;

public class TreeInterview {
    static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            // 空树应该要返回一个 空的 List (不是 null)
            return result;
        }
        // 访问根节点. 此处的访问操作就是把元素添加到 result 中.
        result.add(root.val);
        // 递归访问左子树
        result.addAll(preorderTraversal(root.left));
        // 递归访问右子树
        result.addAll(preorderTraversal(root.right));
        return result;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        result.addAll(inorderTraversal(root.left));
        result.add(root.val);
        result.addAll(inorderTraversal(root.right));
        return result;
    }
}
