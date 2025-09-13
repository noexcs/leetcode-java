package org.noexcs.basic.algo;

import java.util.HashMap;
import java.util.Map;

public class Trie {
    
    // 字典树节点类
    private static class TrieNode {
        Map<Character, TrieNode> children;  // 子节点映射
        boolean isEndOfWord;                // 标记是否为单词结尾
        
        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }
    
    private final TrieNode root;  // 根节点
    
    public Trie() {
        root = new TrieNode();
    }
    
    /**
     * 向字典树中插入一个单词
     * @param word 要插入的单词
     */
    public void insert(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            // 如果当前字符不在子节点中，创建一个新节点
            current.children.putIfAbsent(c, new TrieNode());
            // 移动到下一个节点
            current = current.children.get(c);
        }
        
        // 标记单词结束
        current.isEndOfWord = true;
    }
    
    /**
     * 搜索字典树中是否存在完整单词
     * @param word 要搜索的单词
     * @return 如果单词存在返回true，否则返回false
     */
    public boolean search(String word) {
        TrieNode current = root;
        
        for (char c : word.toCharArray()) {
            // 如果当前字符不在子节点中，返回false
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        
        // 检查是否为完整单词
        return current.isEndOfWord;
    }
    
    /**
     * 检查字典树中是否有以指定前缀开头的单词
     * @param prefix 要检查的前缀
     * @return 如果存在以该前缀开头的单词返回true，否则返回false
     */
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        
        return true;
    }
    
    /**
     * 从字典树中删除一个单词
     * @param word 要删除的单词
     * @return 如果成功删除返回true，否则返回false
     */
    public boolean delete(String word) {
        return delete(root, word, 0);
    }
    
    /**
     * 递归删除单词的辅助方法
     * @param current 当前节点
     * @param word 要删除的单词
     * @param index 当前处理的字符索引
     * @return 是否可以删除当前节点
     */
    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            // 如果当前节点不是单词结尾，不需要删除
            if (!current.isEndOfWord) {
                return false;
            }
            // 取消单词结尾标记
            current.isEndOfWord = false;
            // 如果当前节点没有子节点，可以删除
            return current.children.isEmpty();
        }
        
        char c = word.charAt(index);
        TrieNode node = current.children.get(c);
        
        // 如果字符不存在于子节点中，直接返回false
        if (node == null) {
            return false;
        }
        
        // 递归删除子节点
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);
        
        // 如果子节点应该被删除
        if (shouldDeleteCurrentNode) {
            // 移除对子节点的引用
            current.children.remove(c);
            // 如果当前节点没有其他子节点且不是单词结尾，也可以被删除
            return current.children.isEmpty() && !current.isEndOfWord;
        }
        
        return false;
    }
    
    /**
     * 获取字典树中所有单词的数量
     * @return 单词数量
     */
    public int countWords() {
        return countWords(root);
    }
    
    /**
     * 递归计算单词数量的辅助方法
     * @param node 当前节点
     * @return 以当前节点为根的子树中的单词数量
     */
    private int countWords(TrieNode node) {
        int count = 0;
        
        // 如果当前节点是单词结尾，计数加1
        if (node.isEndOfWord) {
            count++;
        }
        
        // 递归计算所有子节点的单词数量
        for (TrieNode child : node.children.values()) {
            count += countWords(child);
        }
        
        return count;
    }
    
    /**
     * 清空字典树
     */
    public void clear() {
        root.children.clear();
    }
    
    /**
     * 检查字典树是否为空
     * @return 如果为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return root.children.isEmpty();
    }
    
    // 测试代码
    public static void main(String[] args) {
        Trie trie = new Trie();
        
        // 插入单词
        trie.insert("apple");
        trie.insert("app");
        trie.insert("banana");
        trie.insert("bat");
        
        // 测试搜索功能
        System.out.println("Search 'apple': " + trie.search("apple"));    // true
        System.out.println("Search 'app': " + trie.search("app"));        // true
        System.out.println("Search 'appl': " + trie.search("appl"));      // false
        
        // 测试前缀搜索
        System.out.println("StartsWith 'app': " + trie.startsWith("app")); // true
        System.out.println("StartsWith 'ban': " + trie.startsWith("ban")); // true
        System.out.println("StartsWith 'cat': " + trie.startsWith("cat")); // false
        
        // 测试计数
        System.out.println("Word count: " + trie.countWords()); // 4
        
        // 测试删除
        System.out.println("Delete 'app': " + trie.delete("app")); // true
        System.out.println("Search 'app' after deletion: " + trie.search("app")); // false
        System.out.println("Search 'apple' after deletion: " + trie.search("apple")); // true
        
        System.out.println("Word count after deletion: " + trie.countWords()); // 3
        
        // 测试清空
        trie.clear();
        System.out.println("Is empty after clear: " + trie.isEmpty()); // true
    }
}