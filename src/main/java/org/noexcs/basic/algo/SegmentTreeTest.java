package org.noexcs.basic.algo;

import java.util.Arrays;

public class SegmentTreeTest {
    public static void main(String[] args) {
        setAdd();
    }

    private static void setAdd() {
        int[] arr = {1, 2, 3, 4, 5};
        SegmentTree seg = new SegmentTree(arr);

        // 初始数组
        System.out.println("初始: " + Arrays.toString(seg.toArray()));
        // 区间和
        System.out.println("Sum[0..4] = " + seg.querySum(0, 4));

        // 先 set 再 add
        seg.set(0, 4, 10); // 覆盖成 10
        System.out.println("set(0,4,10) 后: " + Arrays.toString(seg.toArray()));

        seg.add(0, 4, 5); // 每个元素都 +5
        System.out.println("add(0,4,5) 后: " + Arrays.toString(seg.toArray()));

        // 再查一次区间和
        System.out.println("Sum[0..4] = " + seg.querySum(0, 4));
    }

    private static void addSet() {
        int[] arr = {1, 2, 3, 4, 5};
        SegmentTree seg = new SegmentTree(arr);

        // 初始数组
        System.out.println("初始: " + Arrays.toString(seg.toArray()));
        // 区间和
        System.out.println("Sum[0..4] = " + seg.querySum(0, 4));

        // 先 add 再 set
        seg.add(0, 4, 5); // 每个元素都 +5
        System.out.println("add(0,4,5) 后: " + Arrays.toString(seg.toArray()));

        seg.set(0, 4, 10); // 覆盖成 10
        System.out.println("set(0,4,10) 后: " + Arrays.toString(seg.toArray()));

        // 再查一次区间和
        System.out.println("Sum[0..4] = " + seg.querySum(0, 4));
    }

    private static void test() {
        int[] arr = {1, 2, 3, 4, 5};
        SegmentTree seg = new SegmentTree(arr);

        System.out.println(seg.querySum(0, 4)); // 15
        System.out.println(seg.queryMax(0, 4)); // 5
        System.out.println(seg.queryMin(0, 4)); // 1

        seg.add(1, 3, 2);
        System.out.println(seg.querySum(0, 4)); // 21
        System.out.println(seg.queryMax(0, 4)); // 6
        System.out.println(seg.queryMin(0, 4)); // 1

        seg.set(2, 4, 7);
        System.out.println(seg.querySum(0, 4)); // 26
        System.out.println(seg.queryMax(0, 4)); // 7
        System.out.println(seg.queryMin(0, 4)); // 1

        seg.update(0, 10);
        System.out.println(Arrays.toString(seg.toArray())); // [10, 4, 7, 7, 7]
    }
}
