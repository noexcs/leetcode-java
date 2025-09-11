package org.noexcs.basic.datastruct;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LFUCacheLinkedHashMap {
    int capacity;
    /**
     * minFreq 发生改变的时机
     * 1. 访问了 minFreq 所对应的唯一元素，导致其频率值加一，minFreq 直接加一
     * 2. 添加了新元素，无论有没有把 minFreq 对应的唯一元素清除，minFreq 都重置为 1
     */
    int minFreq; // 添加minFreq来跟踪最小频率

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
    private HashMap<Integer, LinkedHashMap<Integer, Node>> freq2Nodes;

    public LFUCacheLinkedHashMap(int capacity) {
        this.capacity = capacity;
        this.minFreq = 1;

        map = new HashMap<>();
        freq2Nodes = new HashMap<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        Node node = map.get(key);
        increaseFreq(key, node);
        return node.value;
    }

    private void increaseFreq(int key, Node node) {
        int oldFreq = node.freq;

        // 从当前频率的map中移除
        if (freq2Nodes.containsKey(oldFreq)) {
            LinkedHashMap<Integer, Node> oldMap = freq2Nodes.get(oldFreq);
            oldMap.remove(key);

            // 如果当前频率的map为空，则移除该频率
            if (oldMap.isEmpty()) {
                freq2Nodes.remove(oldFreq);
                // 如果移除的是最小频率，更新minFreq
                if (oldFreq == minFreq) {
                    minFreq++;
                }
            }
        }

        // 增加频率
        node.freq++;
        int newFreq = node.freq;

        // 确保新频率的map存在（使用访问顺序）
        freq2Nodes.putIfAbsent(newFreq, new LinkedHashMap<>());

        // 添加到新频率的map中
        freq2Nodes.get(newFreq).put(key, node);
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        // 已经存在，更新值
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            increaseFreq(key, node);
            return;
        }

        // 容量已满，需要移除最不常用的
        if (map.size() >= capacity) {
            // 找到最小频率的LinkedHashMap
            LinkedHashMap<Integer, Node> minFreqMap = freq2Nodes.get(minFreq);

            // 移除最久未使用的（LinkedHashMap的第一个元素）
            Map.Entry<Integer, Node> entryToRemove = minFreqMap.pollFirstEntry();
            int keyToRemove = entryToRemove.getKey();

            minFreqMap.remove(keyToRemove);
            map.remove(keyToRemove);

            // 如果该频率的map变为空，则移除
            if (minFreqMap.isEmpty()) {
                freq2Nodes.remove(minFreq);
            }
        }

        // 创建新节点
        Node node = new Node(key, value);
        map.put(key, node);

        // 确保频率为1的Map存在
        freq2Nodes.putIfAbsent(1, new LinkedHashMap<>());
        freq2Nodes.get(1).put(key, node);

        // 重置最小频率为1
        minFreq = 1;
    }
}