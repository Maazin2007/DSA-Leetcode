/**
* The approach to this question, the naive solution is to do a nested loop and
* do dfs on each node, but that will be (R*C)^2 time complexity which is more
* what we can do instead is that we can create 2 sets one which goes to each 
* ocean and then finds the intersection points. we can go over the top bottom 
* rows and left and right columns and for each node we can go and find all the
* nodes which reach upto the that specific ocean, then we can go over each 
* node in the array[][] each if it is both nodes.
*Multi-source reverse BFS/DFS: when a problem asks "which cells/nodes can reach ALL of several targets" (or "are reachable FROM several fixed sources"), and checking one starting point at a time would be slow — flip the search to start from the targets/sources themselves, flowing inward/backward, then intersect the results. 
Recognize it by: multiple valid start points + a reachability question + the "check every node individually" approach feeling redundant.
*/

class Solution {
    private int ROWS;
    private int COLS;
    private int[][] heights;
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        // dimesions of the ROWS and COLS
        this.ROWS = heights.length;
        this.COLS = heights[0].length;
        this.heights = heights;
        // hashset to store who can reach each ocean
        HashSet<List<Integer>> pac = new HashSet<>(), atl = new HashSet<>();
        // we need to get the left and right columns
        for (int r = 0; r < ROWS; r++) {
            // left
            dfs(r, 0, pac, heights[r][0]);
            // right
            dfs(r, COLS - 1, atl, heights[r][COLS - 1]);
        }
        // we need top and bottom rows
        for (int c = 0; c < COLS; c++) {
            // top
            dfs(0, c, pac, heights[0][c]);
            // bottom
            dfs(ROWS - 1, c, atl, heights[ROWS - 1][c]);
        }

        // loop through each element in an nested loop and check if if is in both hashsets
        List<List<Integer>> res = new ArrayList<>();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (pac.contains(List.of(r, c)) && atl.contains(List.of(r, c))) res.add(List.of(r, c));
            }
        }
        // returning result array
        return res;
    }

    // helper function dfs which takes a point and the the ocean set and the previous height
    private void dfs(int r, int c, HashSet ocean, int prevHeight) {
        // out of bound or the point is already in the set or the height is smaller
        if (r < 0 || c < 0 || r >= this.ROWS || c >= this.COLS || ocean.contains(List.of(r, c)) || heights[r][c] < prevHeight) {
            return;
        }
        // add the point to the hashset
        ocean.add(List.of(r, c));
        // go over all the neighbours
        dfs(r - 1, c, ocean, heights[r][c]);
        dfs(r + 1, c, ocean, heights[r][c]);
        dfs(r, c - 1, ocean, heights[r][c]);
        dfs(r, c + 1, ocean, heights[r][c]);
    }
}
