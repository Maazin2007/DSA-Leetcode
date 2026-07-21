/**
* in this question the approach was to recurively attack each node loop over
* each if its neigbours and then append each of the result to the list of 
* copy node, the way we handled repitition was to use a hashMap this HashMap 
* allowed us to map each old node to the new copy node so when we ran into a 
* a node we reached we just took it from the hasMap, this is also how the 
* recurion ended our base naturally become the the node whose neighbours we
* had already dealt with see we did not nedd to do further recursion for them
* so we would stop and the recursion would come back up naturally.
*/

class Solution {
    // create hashmap to map old nodes to new ones
    private HashMap<Node, Node> oldToNew = new HashMap<>();

    public Node cloneGraph(Node node) {
        // edge case check if the orginal input node is null or not
        return node != null ? clone(node) : null;
    }

    private Node clone(Node node) {
        // check if the old clone has been already cloned
        if(oldToNew.containsKey(node)) return oldToNew.get(node);
        // clone the nodes value
        Node copy = new Node(node.val);
        // add it to the hashMap
        oldToNew.put(node, copy);
        // recursive call on the neighbours
        for (Node nei : node.neighbors) {
            copy.neighbors.add(clone(nei));
        }
        return copy;
    }
}
