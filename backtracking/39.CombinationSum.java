/**
 * LeetCode 39. Combination Sum
 * https://leetcode.com/problems/combination-sum/
 *
 * Approach: backtracking. At each step, try adding candidates starting from
 * the current index onward (not from 0) to avoid duplicate orderings like
 * [2,3] vs [3,2]. Recurse with `i` (not i+1) to allow reusing the same
 * candidate multiple times, since each number can be used unlimited times.
 * Base cases: sum == target adds a copy of path to result; sum > target
 * prunes the branch.
 * Time: O(2^target) worst case, Space: O(target) recursion depth
 */

import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, result, path, 0);
        return result;
    }

    private void backtrack(int[] candidates, int target, List<List<Integer>> result, List<Integer> path, int index) {
        int sum = 0;
        for (Integer num : path) sum += num;

        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (sum > target) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            path.add(candidates[i]);
            backtrack(candidates, target, result, path, i);
            path.remove(path.size() - 1);
        }
    }
}
