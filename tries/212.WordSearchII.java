/**
 * LeetCode 212. Word Search II
 * https://leetcode.com/problems/word-search-ii/
 *
 * Approach: build a Trie from the word list first. Then backtrack over
 * every grid cell, walking the grid and the Trie together — at each step,
 * check if the current TrieNode has a child matching the grid letter (if
 * not, dead end, prune immediately). Track the current path in a
 * StringBuilder; whenever a TrieNode's isWord flag is true, the path so
 * far is a complete word — add it to results and clear the flag to avoid
 * duplicate additions if the same word is reachable via another path.
 * Mark/unmark visited cells and append/remove from the StringBuilder as
 * the classic backtracking undo step.
 * Time: O(m * n * 4^L) worst case, L = longest word length
 * Space: O(total characters across words) for the Trie, O(L) recursion depth
 */

import java.util.*;

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isWord = false;

    void insert(String word) {
        TrieNode curr = this;
        for (char c : word.toCharArray()) {
            int pos = c - 'a';
            if (curr.children[pos] == null) {
                curr.children[pos] = new TrieNode();
            }
            curr = curr.children[pos];
        }
        curr.isWord = true;
    }
}

class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            root.insert(word);
        }

        List<String> result = new ArrayList<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                backtrack(board, r, c, root, new StringBuilder(), visited, result);
            }
        }
        return result;
    }

    private void backtrack(char[][] board, int r, int c, TrieNode node, StringBuilder path, boolean[][] visited, List<String> result) {
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length || visited[r][c]) {
            return;
        }

        char letter = board[r][c];
        int pos = letter - 'a';
        if (node.children[pos] == null) {
            return; // no word in the Trie has this prefix, dead end
        }

        TrieNode nextNode = node.children[pos];
        path.append(letter);
        visited[r][c] = true;

        if (nextNode.isWord) {
            result.add(path.toString());
            nextNode.isWord = false; // avoid duplicate additions
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {
            backtrack(board, r + dr[d], c + dc[d], nextNode, path, visited, result);
        }

        visited[r][c] = false;
        path.deleteCharAt(path.length() - 1);
    }
}
