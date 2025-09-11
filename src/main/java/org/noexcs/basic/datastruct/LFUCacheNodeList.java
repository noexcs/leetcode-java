package org.noexcs.basic.datastruct;

import java.util.HashMap;

public class LFUCacheNodeList {

    static class Node {
        int key;
        int value;
        int freq;
        Node pre;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    static class NodeList {
        int size;
        Node head;
        Node tail;

        public NodeList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.pre = head;
        }

        void addLast(Node node) {
            node.pre = tail.pre;
            node.next = tail;
            tail.pre.next = node;
            tail.pre = node;
            size++;
        }

        void remove(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
            size--;
        }

        Node removeFirst() {
            if (size == 0)
                return null;
            Node firstNode = head.next;
            remove(firstNode);
            return firstNode;
        }
    }

    int capacity;
    int minFreq;
    private HashMap<Integer, Node> keyMap;
    private HashMap<Integer, NodeList> freqMap;

    public LFUCacheNodeList(int capacity) {
        this.capacity = capacity;
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
    }

    private void increaseFreq(Node node) {
        NodeList oldList = freqMap.get(node.freq);
        oldList.remove(node);
        if (oldList.size == 0) {
            freqMap.remove(node.freq);
            if (minFreq == node.freq) {
                minFreq++;
            }
        }

        node.freq++;

        freqMap.putIfAbsent(node.freq, new NodeList());
        freqMap.get(node.freq).addLast(node);
    }

    public int get(int key) {
        if (!keyMap.containsKey(key)) {
            return -1;
        }
        Node node = keyMap.get(key);
        increaseFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            node.value = value;
            increaseFreq(node);
            return;
        }
        if (keyMap.size() == capacity) {
            NodeList nodeList = freqMap.get(minFreq);
            Node node = nodeList.removeFirst();
            keyMap.remove(node.key);
            if (nodeList.size == 0) {
                freqMap.remove(minFreq);
            }
        }

        Node node = new Node(key, value);
        keyMap.put(key, node);
        freqMap.putIfAbsent(node.freq, new NodeList());
        freqMap.get(node.freq).addLast(node);

        minFreq = 1;
    }
}
