package org.noexcs.basic.datastruct;

import java.util.Objects;
import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        // 默认从小到达
        PriorityQueue<Integer> defaultPriorityQueue = new PriorityQueue<>();
        defaultPriorityQueue.add(3);  // [3]
        defaultPriorityQueue.add(2);  // [2, 3]
        defaultPriorityQueue.add(4);  // [2, 3, 4]
        defaultPriorityQueue.add(5);  // [2, 3, 4, 5]
        defaultPriorityQueue.add(1);  // [1, 2, 3, 4, 5]

        System.out.println(Objects.equals(defaultPriorityQueue.poll(), 1));  // [2, 3, 4, 5]
        System.out.println(Objects.equals(defaultPriorityQueue.poll(), 2));  // [3, 4, 5]

        // 使用 Comparator 改为从大到小
        PriorityQueue<Integer> bigFirstPriorityQueue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        bigFirstPriorityQueue.add(3);  // [3]
        bigFirstPriorityQueue.add(2);  // [3, 2]
        bigFirstPriorityQueue.add(4);  // [4, 3, 2]
        bigFirstPriorityQueue.add(5);  // [5, 4, 3, 2]
        bigFirstPriorityQueue.add(1);  // [5, 4, 3, 2, 1]

        System.out.println(Objects.equals(bigFirstPriorityQueue.poll(), 5));  // [4, 3, 2, 1]
        System.out.println(Objects.equals(bigFirstPriorityQueue.poll(), 4));  // [3, 2, 1]
    }
}
