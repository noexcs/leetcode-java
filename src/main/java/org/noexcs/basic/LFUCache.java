package org.noexcs.basic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * LFU 缓存
 * <p>
 * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
 * <p>
 * 实现 LFUCache 类：
 * <p>
 * LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
 * int get(int key) - 如果键 key 存在于缓存中，则获取键的值，否则返回 -1 。
 * void put(int key, int value) - 如果键 key 已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量 capacity 时，则应该在插入新项之前，移除最不经常使用
 * 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
 * <p>
 * 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。
 * <p>
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * <p>
 * 提示：
 * <p>
 * * 1 <= capacity <= 104
 * * 0 <= key <= 105
 * * 0 <= value <= 109
 * * 最多调用 2 * 105 次 get 和 put 方法
 */
public class LFUCache {
    int capacity;

    static class Node {
        int key;
        int value;
        int freq;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }


    private HashMap<Integer, Node> map;

    private TreeMap<Integer, LinkedHashMap<Integer, Node>> freq2Nodes;

    public LFUCache(int capacity) {
        this.capacity = capacity;

        map = new HashMap<>();
        freq2Nodes = new TreeMap<>();
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }

        increaseFreq(key, node);

        return node.value;
    }

    private void increaseFreq(int key, Node node) {
        freq2Nodes.get(node.freq).remove(key);

        if (!freq2Nodes.containsKey(node.freq + 1)) {
            freq2Nodes.put(node.freq + 1, new LinkedHashMap<>(0, 0.75f, true));
        }

        node.freq += 1;
        freq2Nodes.get(node.freq).put(key, node);
    }

    public void put(int key, int value) {
        // 已经存在，更新值
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            increaseFreq(key, node);
            return;
        }

        // 不存在，新建
        Node node = new Node(key, value);
        node.freq++;
        map.put(key, node);


        if (!freq2Nodes.containsKey(node.freq)) {
            freq2Nodes.put(node.freq, new LinkedHashMap<>(0, 0.75f, true));
        }
        freq2Nodes.get(node.freq).put(key, node);

        // 判断容量，移除元素
        if (map.size() > capacity) {
            int minFreq = freq2Nodes.ceilingKey(0);
            LinkedHashMap<Integer, Node> linkedHashMap = freq2Nodes.get(minFreq);
            Map.Entry<Integer, Node> nodeEntry = linkedHashMap.pollLastEntry();

            if (linkedHashMap.isEmpty()) {
                freq2Nodes.remove(minFreq);
            }
            map.remove(nodeEntry.getKey());
        }
    }
}
