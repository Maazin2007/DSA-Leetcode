/**
 * LeetCode 297. Serialize and Deserialize Binary Tree
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 *
 * Approach: Preorder traversal with explicit null markers ("N"), joined by commas.
 * Serialize: standard preorder, but recurse into null children too instead of
 * skipping them — this records the tree's shape directly, no need to infer
 * structure from index positions (works correctly even with duplicate values).
 * Deserialize: split on commas, then rebuild via preorder using a shared index (i)
 * as an instance field, so recursive calls all advance through the same array
 * in order.
 * Time: O(n) for both, Space: O(n) for the string/array, O(h) recursion stack
 */

import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Codec {
    private int i = 0;
    private String[] arr = new String[]{};

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "N";
        return String.valueOf(root.val) + "," + serialize(root.left) + "," + serialize(root.right);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        arr = data.split(",");
        i = 0; // reset index in case this Codec instance is reused
        return helper();
    }

    private TreeNode helper() {
        if (arr[i].equals("N")) {
            i++;
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(arr[i]));
        i++;
        node.left = helper();
        node.right = helper();
        return node;
    }
}
