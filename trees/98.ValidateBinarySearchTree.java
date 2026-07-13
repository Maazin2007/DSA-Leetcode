/**
 * LeetCode 98. Validate Binary Search Tree
 * https://leetcode.com/problems/validate-binary-search-tree/
 *
 * Approach: DFS with min/max bounds passed down through recursion.
 * Each node must fall within (min, max), which narrows as we go left (max shrinks)
 * or right (min rises) — this enforces the check against ALL ancestors, not just
 * the immediate parent.
 * Time: O(n), Space: O(h) for recursion stack
 */

import java.util.*;

class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValidHelper(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    private boolean isValidHelper(TreeNode root, double min, double max) {
        if (root == null) return true;
        if (!(min < root.val && root.val < max)) return false;
        return isValidHelper(root.left, min, root.val) && isValidHelper(root.right, root.val, max);
    }
}
