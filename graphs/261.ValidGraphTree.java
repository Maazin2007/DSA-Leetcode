/**
 * LeetCode 261 - Graph Valid Tree
 *
 * PATTERN: build adjacency list -> DFS -> interpret the visited set
 * (same skeleton as Course Schedule / canFinish, adapted for an UNDIRECTED graph)
 * Related problem: Course Schedule (LeetCode 207) - "canFinish"
 *
 * A valid tree requires exactly two properties:
 *   1. No cycles           -> there must be at most ONE path between any two nodes
 *   2. Fully connected      -> every node must be reachable from every other node
 *
 * --------------------------------------------------------------------------
 * WHY THIS DIFFERS FROM COURSE SCHEDULE (directed graph cycle detection):
 *
 * In canFinish (directed), we needed TWO sets:
 *   - visiting: nodes on the CURRENT path (catches real cycles)
 *   - visited:  nodes fully confirmed safe from an EARLIER, separate DFS call
 *               (needed because the outer loop runs DFS from every node, and
 *                two unrelated courses can legitimately share a prerequisite)
 *
 * Here (undirected, single DFS call from one node), there is no "earlier,
 * separate call" scenario - the whole graph is explored in ONE continuous
 * traversal. So there's nothing for a second set to distinguish:
 *   - "currently on my path" and "ever visited" collapse into the same thing
 *   - ONE visited set is enough, and it is NEVER backtracked (never removed
 *     from) - once a node is visited, it must stay visited for the rest of
 *     this traversal, because re-reaching it (other than via the parent edge)
 *     always means a genuine second path = a cycle.
 *
 * WHY WE NEED THE PARENT PARAMETER:
 *
 * Because edges are undirected, each edge (a, b) is stored in BOTH a's and
 * b's adjacency lists. So when we DFS from A into neighbor B, B's list
 * contains A right back - the edge we just walked. Without tracking the
 * parent, we'd immediately (and incorrectly) flag that as a cycle. Passing
 * "parent" lets us skip only the single edge we arrived from, while still
 * catching any other repeat visit as a real cycle.
 * --------------------------------------------------------------------------
 */
class Solution {

    public boolean validTree(int n, int[][] edges) {
        // Quick structural check: a tree on n nodes has EXACTLY n - 1 edges.
        // Fewer -> can't be fully connected. More -> guaranteed cycle.
        // Not strictly required (the DFS + visited.size() check below would
        // eventually catch invalid graphs too), but it's a cheap early exit.
        if (edges.length != n - 1) return false;

        // adjacency list representation of the graph
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        HashSet<Integer> visited = new HashSet<>();

        // create a key for every node, 0 .. n-1
        for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());

        // undirected: add the edge in BOTH directions
        for (int[] elem : edges) {
            graph.get(elem[0]).add(elem[1]);
            graph.get(elem[1]).add(elem[0]);
        }

        // Only need ONE DFS call, starting anywhere (node 0), because a
        // connected component reaches everything reachable from that start.
        // -1 = "no parent" sentinel for the root of the traversal.
        if (!dfs(graph, visited, 0, -1)) return false;

        // If every node was reachable from node 0, visited will contain
        // all n nodes. If any node was left out, the graph has an island
        // (i.e. isn't fully connected) -> not a valid tree.
        return visited.size() == n;
    }

    private boolean dfs(HashMap<Integer, List<Integer>> graph, HashSet<Integer> visited, int curr, int parent) {
        // Mark visited immediately on entry (NOT right before returning).
        // This must happen before we look at neighbors, so that any deeper
        // recursive call still in progress can see "curr" as already
        // reachable in this traversal and correctly flag a cycle back to it.
        visited.add(curr);

        for (Integer num : graph.get(curr)) {
            // Skip the single edge we just arrived on - not a cycle,
            // just the undirected edge pointing back at its own parent.
            if (num == parent) continue;

            // Checking here (in the parent's loop) instead of at the top of
            // the next dfs() call avoids one extra, immediately-wasted stack
            // frame in the cycle case - functionally identical either way,
            // just a minor call-avoidance optimization, not a complexity
            // class change (still O(V + E) overall).
            if (visited.contains(num)) return false; // genuine second path = cycle

            if (!dfs(graph, visited, num, curr)) return false;
        }

        // No backtracking (no visited.remove here) - unlike canFinish,
        // a node must stay marked visited for the rest of this ONE
        // continuous traversal. Removing it would let a real cycle slip
        // through undetected on a later, still-in-progress path.
        return true;
    }
}
