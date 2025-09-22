package org.noexcs.basic.algo;

public class TrieArrayTest {

    private static class TrieNode {
        TrieNode[] children;
        boolean hasStop;
    }

    private final TrieNode root;

    public TrieArrayTest() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children == null) {
                cur.children = new TrieNode[26];
            }
            if (cur.children[c - 'a'] == null) {
                cur.children[c - 'a'] = new TrieNode();
            }
            cur = cur.children[c - 'a'];
        }
        cur.hasStop = true;
    }

    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children == null || cur.children[c - 'a'] == null) {
                return false;
            }
            cur = cur.children[c - 'a'];
        }
        return cur.hasStop;
    }

    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.children == null || cur.children[c - 'a'] == null) {
                return false;
            }
            cur = cur.children[c - 'a'];
        }
        return true;
    }

    public static void main(String[] args) {
        TrieArrayTest trie = new TrieArrayTest();

        // 测试插入功能
        System.out.println("=== 测试插入功能 ===");
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        System.out.println("插入单词: apple, app, application");

        // 测试精确搜索功能
        System.out.println("\n=== 测试搜索功能 ===");
        System.out.println("搜索 'apple': " + trie.search("apple"));     // 应该返回 true
        System.out.println("搜索 'app': " + trie.search("app"));         // 应该返回 true
        System.out.println("搜索 'appl': " + trie.search("appl"));       // 应该返回 false
        System.out.println("搜索 'application': " + trie.search("application")); // 应该返回 true
        System.out.println("搜索 'apps': " + trie.search("apps"));       // 应该返回 false

        // 测试前缀功能
        System.out.println("\n=== 测试前缀功能 ===");
        System.out.println("前缀 'app': " + trie.startsWith("app"));     // 应该返回 true
        System.out.println("前缀 'appl': " + trie.startsWith("appl"));   // 应该返回 true
        System.out.println("前缀 'apz': " + trie.startsWith("apz"));     // 应该返回 false
        System.out.println("前缀 'appli': " + trie.startsWith("appli")); // 应该返回 true
    }
}


