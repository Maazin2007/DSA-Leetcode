/**
 * LeetCode 230. Kth Smallest Element in a BST
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 *
 * Approach: Iterative inorder traversal using an explicit stack.
 * BST property: inorder traversal visits nodes in sorted order.
 * Push all left children first, then pop/visit/go-right — stop the moment
 * the visit counter reaches k, avoiding a full traversal.
 * Time: O(h + k), Space: O(h) for the stack
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

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        // form the stack inorder traversal method
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        int count = 0;
        while (curr != null || !stack.isEmpty()) {
            // go the maximum left
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            count++;
            if (count == k) {
                return curr.val;
            }
            curr = curr.right;
        }
        return -1;
    }
}
