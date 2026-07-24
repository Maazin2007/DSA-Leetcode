/*
* for this solution the pattern is to detect the cycle so we ceate a graph 
* using hashmap for the course and its requirmentd and then dfs over each node
* if the we see another node in our visted hash that means we have a loop and
* we return false.
* Time Complexity: O(v + e)
*/
class Solution {
    // O(V + E)
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // we want a hashmap to store the graph
        HashMap<Integer, List<Integer>> preqMap = new HashMap<>();
        // create a key for all cources
        for (int i = 0; i < numCourses; i++) preqMap.put(i, new ArrayList<>());
        // create the HashMap 
        for (int[] elem : prerequisites) preqMap.get(elem[0]).add(elem[1]);
        // visisted HashSet
        HashSet<Integer> visited = new HashSet<>();
        HashSet<Integer> visiting = new HashSet<>();
        // check all courses as starting point
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(preqMap, visited, visiting, i)) return false;
        }
        return true;
    }

    private boolean dfs(HashMap<Integer, List<Integer>> preqMap, HashSet<Integer> visited, HashSet<Integer> visiting, int curr) {
        // if it is already in visited that means we have found a loop
        if (visiting.contains(curr)) return false;
        if (visited.contains(curr)) return true; 
        // if it is empty then we return true as there are no preq
        if (preqMap.get(curr).isEmpty()) return true;
        // further dfs
        visiting.add(curr);
        for (Integer num : preqMap.get(curr)) {
            if (!dfs(preqMap, visited, visiting, num)) return false;
        }
        visiting.remove(curr);
        visited.add(curr);
        return true;
    }
}
