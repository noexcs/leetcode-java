package org.noexcs.basic.algo;

public class SegmentTreeMax {
    private int[] tree;
    private int[] lazy;
    private int[] data;
    private int n;

    public SegmentTreeMax(int[] arr) {
        n = arr.length;
        data = new int[n];
        System.arraycopy(arr, 0, data, 0, n);

        // 线段树的大小是原数组长度的4倍
        tree = new int[4 * n];
        lazy = new int[4 * n];
        buildTree(0, 0, n - 1);
    }

    // 构建线段树
    private void buildTree(int node, int start, int end) {
        if (start == end) {
            tree[node] = data[start];
            return;
        }

        int mid = start + (end - start) / 2;
        int leftNode = 2 * node + 1;
        int rightNode = 2 * node + 2;

        buildTree(leftNode, start, mid);
        buildTree(rightNode, mid + 1, end);

        tree[node] = tree[leftNode] + tree[rightNode];
    }

    // 区间查询
    public int query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }

    private int query(int node, int start, int end, int l, int r) {
        if (r < start || l > end) {
            return 0;
        }

        // 懒标记下推
        if (lazy[node] != 0) {
            tree[node] += (end - start + 1) * lazy[node];
            if (start != end) {
                lazy[2 * node + 1] += lazy[node];
                lazy[2 * node + 2] += lazy[node];
            }
            lazy[node] = 0;
        }

        if (l <= start && end <= r) {
            return tree[node];
        }

        int mid = start + (end - start) / 2;
        int leftNode = 2 * node + 1;
        int rightNode = 2 * node + 2;

        int leftSum = query(leftNode, start, mid, l, r);
        int rightSum = query(rightNode, mid + 1, end, l, r);

        return leftSum + rightSum;
    }

    public void update(int idx, int val) {
        update(idx, idx, val);
    }

    public void update(int l, int r, int val) {
        update(0, 0, n - 1, l, r, val);
    }

    private void update(int node, int start, int end, int l, int r, int val) {
        // 懒标记下推
        if (lazy[node] != 0) {
            tree[node] += val;
            if (start != end) {
                lazy[2 * node + 1] += lazy[node];
                lazy[2 * node + 2] += lazy[node];
            }
            lazy[node] = 0;
        }

        if (start > r || end < l) {
            return;
        }

        if (l <= start && end <= r) {
            tree[node] += (end - start + 1) * val;
            if (start != end) {
                lazy[2 * node + 1] += val;
                lazy[2 * node + 2] += val;
            }
            return;
        }

        int mid = start + (end - start) / 2;
        int leftNode = 2 * node + 1;
        int rightNode = 2 * node + 2;

        update(leftNode, start, mid, l, r, val);
        update(rightNode, mid + 1, end, l, r, val);

        tree[node] = tree[leftNode] + tree[rightNode];
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        SegmentTreeMax st = new SegmentTreeMax(arr);

        System.out.println("Original array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();

        // 查询区间 [1, 4] 的和
        System.out.println("Sum of range [1, 4]: " + st.query(1, 4));

        // 更新区间 [1, 3] 每个元素加 2
        st.update(1, 3, 2);
        System.out.println("After updating range [1, 3] by adding 2:");
        System.out.println("Sum of range [1, 4]: " + st.query(1, 4));

        // 更新单点
        st.update(2, 10);
        System.out.println("After updating index 2 to 10:");
        System.out.println("Sum of range [1, 4]: " + st.query(1, 4));
    }
}