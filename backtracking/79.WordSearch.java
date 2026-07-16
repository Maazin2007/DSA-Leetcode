/**
 * LeetCode 79. Word Search
 * https://leetcode.com/problems/word-search/
 *
 * Approach: backtracking DFS from every cell in the grid. At each step,
 * check base cases (full word matched, out of bounds, letter mismatch,
 * or cell already visited in this path), then mark the cell visited and
 * explore all 4 directions for the next letter. Unmark (backtrack) after
 * exploring, since the same cell may be reusable on a different path.
 * Time: O(m * n * 4^L) where L = word length, Space: O(L) recursion depth
 */

class Solution {
    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (backtrack(board, word, r, c, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean backtrack(char[][] board, String word, int r, int c, int index, boolean[][] visited) {
        if (index == word.length()) return true;
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length
                || board[r][c] != word.charAt(index) || visited[r][c]) {
            return false;
        }

        visited[r][c] = true;
        boolean res = backtrack(board, word, r - 1, c, index + 1, visited)
                || backtrack(board, word, r + 1, c, index + 1, visited)
                || backtrack(board, word, r, c - 1, index + 1, visited)
                || backtrack(board, word, r, c + 1, index + 1, visited);
        visited[r][c] = false;

        return res;
    }
}
