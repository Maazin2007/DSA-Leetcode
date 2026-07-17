/**
 * LeetCode 208. Implement Trie (Prefix Tree)
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 *
 * Approach: each Trie node holds a fixed-size array of 26 children
 * (one per lowercase letter) and a flag marking whether a word ends here.
 * insert/search/startsWith all walk down the tree one character at a time,
 * following (or creating, for insert) the child node matching each letter.
 * Time: O(L) for insert/search/startsWith, where L = word/prefix length
 * Space: O(ALPHABET_SIZE * N * L) worst case across all inserted words
 */

class Trie {
    private Trie[] children;
    private boolean isEndOfWord;

    public Trie() {
        this.children = new Trie[26];
        this.isEndOfWord = false;
    }

    // O(L) where L is the length of the word being inserted
    public void insert(String word) {
        Trie curr = this;
        for (char c : word.toCharArray()) {
            int pos = c - 'a';
            if (curr.children[pos] == null) {
                curr.children[pos] = new Trie();
            }
            curr = curr.children[pos];
        }
        curr.isEndOfWord = true;
    }

    // O(L) where L is the length of the word being searched for
    public boolean search(String word) {
        Trie curr = this;
        for (char c : word.toCharArray()) {
            int pos = c - 'a';
            if (curr.children[pos] == null) return false;
            curr = curr.children[pos];
        }
        return curr.isEndOfWord;
    }

    // O(L) where L is the length of the prefix string
    public boolean startsWith(String prefix) {
        Trie curr = this;
        for (char c : prefix.toCharArray()) {
            int pos = c - 'a';
            if (curr.children[pos] == null) return false;
            curr = curr.children[pos];
        }
        // if we reach the end that means either the prefix is the last string
        // or it will continue - both are acceptable for us
        return true;
    }
}
