/**
 * LeetCode 200. Number of Islands
 * https://leetcode.com/problems/number-of-islands/
 *
 * Approach: treat the grid as an implicit graph (each cell a node, edges to
 * up/down/left/right neighbors). Loop over every cell; whenever an unvisited
 * land cell ('1') is found, that's the start of a new island — increment
 * count and flood-fill (DFS) outward, marking every connected land cell
 * visited so it's never counted again. No backtracking/undo needed since
 * once a cell is claimed by an island, it stays claimed.
 * Time: O(rows * cols), Space: O(rows * cols) for visited + recursion stack
 */

class Solution {
    public int numIslands(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int count = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1' && visited[r][c] == false) {
                    count++;
                    dfs(grid, visited, r, c);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, boolean[][] visited, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) return;
        if (grid[r][c] == '0' || visited[r][c] == true) return;

        visited[r][c] = true;

        int[] df = new int[]{-1, 1, 0, 0};
        int[] ds = new int[]{0, 0, 1, -1};
        for (int d = 0; d < 4; d++) {
            dfs(grid, visited, r + df[d], c + ds[d]);
        }
    }
}
