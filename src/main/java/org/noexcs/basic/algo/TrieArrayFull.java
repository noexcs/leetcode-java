package org.noexcs.basic.algo;

import java.util.ArrayList;
import java.util.List;

public class TrieArrayFull {

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int wordCount;     // 当前节点作为单词结尾的次数
        int prefixCount;   // 有多少单词经过这个节点（含自身结尾的）
    }

    private final TrieNode root = new TrieNode();
    private int size; // 存储的单词数（去重还是含重复，由 wordCount 决定）

    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26) {
                throw new IllegalArgumentException("非法字符: " + c);
            }
            if (cur.children[idx] == null) {
                cur.children[idx] = new TrieNode();
            }
            cur = cur.children[idx];
            cur.prefixCount++;
        }
        if (cur.wordCount == 0) size++; // 新单词
        cur.wordCount++;
    }

    public boolean search(String word) {
        TrieNode cur = findNode(word);
        return cur != null && cur.wordCount > 0;
    }

    public boolean startsWith(String prefix) {
        TrieNode cur = findNode(prefix);
        return cur != null;
    }

    public int countPrefix(String prefix) {
        TrieNode cur = findNode(prefix);
        return cur == null ? 0 : cur.prefixCount;
    }

    public boolean delete(String word) {
        if (!search(word)) return false;
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            TrieNode child = cur.children[idx];
            child.prefixCount--;
            if (child.prefixCount == 0) {
                cur.children[idx] = null; // 彻底回收分支
                return true;
            }
            cur = child;
        }
        cur.wordCount--;
        if (cur.wordCount == 0) size--;
        return true;
    }

    public int size() {
        return size;
    }

    public List<String> getAllWords() {
        List<String> res = new ArrayList<>();
        dfs(root, new StringBuilder(), res);
        return res;
    }

    private void dfs(TrieNode node, StringBuilder path, List<String> res) {
        if (node.wordCount > 0) {
            for (int i = 0; i < node.wordCount; i++) {
                res.add(path.toString());
            }
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                path.append((char) ('a' + i));
                dfs(node.children[i], path, res);
                path.deleteCharAt(path.length() - 1);
            }
        }
    }

    private TrieNode findNode(String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26) return null;
            if (cur.children[idx] == null) return null;
            cur = cur.children[idx];
        }
        return cur;
    }

    public static void main(String[] args) {
        TrieArrayFull trie = new TrieArrayFull();
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        trie.insert("app"); // 重复插入

        System.out.println("搜索 apple: " + trie.search("apple"));       // true
        System.out.println("搜索 app: " + trie.search("app"));           // true
        System.out.println("搜索 appl: " + trie.search("appl"));         // false
        System.out.println("前缀 appli 数: " + trie.countPrefix("appli"));// 1
        System.out.println("不同单词数: " + trie.size());                // 3
        System.out.println("所有单词: " + trie.getAllWords());           // [app, app, apple, application]

        trie.delete("app");
        System.out.println("删除一个 app 后: " + trie.getAllWords());    // 一个 app 少了
        trie.delete("app");
        System.out.println("再删一个 app: " + trie.getAllWords());       // 彻底没了
    }
}
