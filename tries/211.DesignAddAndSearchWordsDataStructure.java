/**
 * LeetCode 211. Design Add and Search Words Data Structure
 * https://leetcode.com/problems/design-add-and-search-words-data-structure/
 *
 * Approach: standard Trie for addWord (same as Implement Trie). search adds
 * backtracking on top: at each character, if it's '.', try every non-null
 * child recursively (any one succeeding means success); otherwise follow
 * the single matching child as normal. Base case: reached end of word,
 * return whether this node marks a complete word.
 * Time: O(L) for addWord; O(26^L) worst case for search with many dots,
 * O(L) typical case with few/no dots
 * Space: O(ALPHABET_SIZE * N * L) across all inserted words
 */

class WordDictionary {
    private WordDictionary[] children;
    private boolean isEndOfWord;

    public WordDictionary() {
        this.children = new WordDictionary[26];
        this.isEndOfWord = false;
    }

    public void addWord(String word) {
        WordDictionary curr = this;
        for (char c : word.toCharArray()) {
            int pos = c - 'a';
            if (curr.children[pos] == null) {
                curr.children[pos] = new WordDictionary();
            }
            curr = curr.children[pos];
        }
        curr.isEndOfWord = true;
    }

    public boolean search(String word) {
        return search(word.toCharArray(), this, 0);
    }

    private boolean search(char[] wordArr, WordDictionary curr, int index) {
        if (index == wordArr.length) return curr.isEndOfWord;

        if (wordArr[index] == '.') {
            for (WordDictionary trie : curr.children) {
                if (trie != null && search(wordArr, trie, index + 1)) {
                    return true;
                }
            }
            return false;
        } else {
            int pos = wordArr[index] - 'a';
            if (curr.children[pos] == null) return false;
            return search(wordArr, curr.children[pos], index + 1);
        }
    }
}
