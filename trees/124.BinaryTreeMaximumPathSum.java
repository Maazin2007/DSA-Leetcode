/**
 * LeetCode 124. Binary Tree Maximum Path Sum
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 *
 * Approach: DFS post-order. helper() returns the best single-branch sum
 * extendable upward from this node (root.val + max(left, right), clamped at 0
 * since negative branches should be dropped). A global `res` tracks the best
 * "forked" path at any node (root.val + left + right), which can't be
 * extended to a parent but is a valid standalone path.
 * Time: O(n), Space: O(h) for recursion stack
 */

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

class Solution {
    private int res = -1001;

    public int maxPathSum(TreeNode root) {
        res = root.val;
        helper(root);
        return res;
    }

    private int helper(TreeNode root) {
        if (root == null) return 0;
        int leftMax = helper(root.left);
        int rightMax = helper(root.right);
        leftMax = Math.max(leftMax, 0);
        rightMax = Math.max(rightMax, 0);
        res = Math.max(res, root.val + leftMax + rightMax);
        return root.val + Math.max(leftMax, rightMax);
    }
}
