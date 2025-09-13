package org.noexcs.basic.algo;

public class TrieArray {

    private static class TrieNode {
        TrieNode[] children;  // 使用数组代替Map
        boolean isEndOfWord;

        public TrieNode() {
            children = new TrieNode[26];  // 26个小写字母
            isEndOfWord = false;
        }
    }

    private final TrieNode root;

    public TrieArray() {
        root = new TrieNode();
    }

    private int charToIndex(char c) {
        return c - 'a';
    }

    public void insert(String word) {
        TrieNode current = root;

        for (char c : word.toCharArray()) {
            int index = charToIndex(c);
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            int index = charToIndex(c);
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return current.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            int index = charToIndex(c);
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return true;
    }
}