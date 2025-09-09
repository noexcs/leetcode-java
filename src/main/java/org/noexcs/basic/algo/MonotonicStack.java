package org.noexcs.basic.algo;

import java.util.ArrayDeque;
import java.util.Arrays;

public class MonotonicStack {
    public static int[] leftNearestStrictlyGreater(int[] arr) {
        int n = arr.length;
        int[] leftNearestStrictlyGreater = new int[n];
        Arrays.fill(leftNearestStrictlyGreater, -1);

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                stack.pop();
            }
            leftNearestStrictlyGreater[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        return leftNearestStrictlyGreater;
    }

    public static int[] rightNearestStrictlyGreater(int[] arr) {
        int n = arr.length;
        int[] rightNearestStrictlyGreater = new int[n];
        Arrays.fill(rightNearestStrictlyGreater, -1);

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                rightNearestStrictlyGreater[stack.pop()] = i;
            }
            stack.push(i);
        }

        return rightNearestStrictlyGreater;
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 1, 5, 6, 2, 3};
        System.out.println(Arrays.toString(leftNearestStrictlyGreater(arr))); // [-1, 0, 0, -1, -1, 4, 4]
        System.out.println(Arrays.toString(rightNearestStrictlyGreater(arr))); // [3, 3, 3, 4, -1, 6, -1]
    }
}
