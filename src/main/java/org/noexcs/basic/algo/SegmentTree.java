package org.noexcs.basic.algo;

public class SegmentTree {
    private final long[] treeSum;   // 区间和
    private final long[] treeMax;   // 区间最大值
    private final long[] treeMin;   // 区间最小值
    private final long[] lazyAdd;   // 区间加标记
    private final long[] lazySet;   // 区间赋值标记
    private final boolean[] hasSet; // 是否有赋值标记
    private final int n;

    public SegmentTree(int[] arr) {
        this.n = arr.length;
        treeSum = new long[4 * n];
        treeMax = new long[4 * n];
        treeMin = new long[4 * n];
        lazyAdd = new long[4 * n];
        lazySet = new long[4 * n];
        hasSet = new boolean[4 * n];
        build(0, 0, n - 1, arr);
    }

    private void build(int node, int L, int R, int[] arr) {
        if (L == R) {
            treeSum[node] = arr[L];
            treeMax[node] = arr[L];
            treeMin[node] = arr[L];
            return;
        }
        int mid = L + (R - L) / 2;
        build(node * 2 + 1, L, mid, arr);
        build(node * 2 + 2, mid + 1, R, arr);
        pushUp(node);
    }

    private void pushUp(int node) {
        treeSum[node] = treeSum[node * 2 + 1] + treeSum[node * 2 + 2];
        treeMax[node] = Math.max(treeMax[node * 2 + 1], treeMax[node * 2 + 2]);
        treeMin[node] = Math.min(treeMin[node * 2 + 1], treeMin[node * 2 + 2]);
    }

    public long querySum(int L, int R) {
        return querySum(0, 0, n - 1, L, R);
    }

    private long querySum(int node, int L, int R, int QL, int QR) {
        pushDown(node, L, R);
        if (QR < L || R < QL) return 0;
        if (QL <= L && R <= QR) return treeSum[node];
        int mid = L + (R - L) / 2;
        return querySum(node * 2 + 1, L, mid, QL, QR) + querySum(node * 2 + 2, mid + 1, R, QL, QR);
    }


    public long queryMax(int L, int R) {
        return queryMax(0, 0, n - 1, L, R);
    }

    private long queryMax(int node, int L, int R, int QL, int QR) {
        pushDown(node, L, R);
        if (QR < L || R < QL) return Long.MIN_VALUE;
        if (QL <= L && R <= QR) return treeMax[node];
        int mid = L + (R - L) / 2;
        return Math.max(
                queryMax(node * 2 + 1, L, mid, QL, QR),
                queryMax(node * 2 + 2, mid + 1, R, QL, QR)
        );
    }


    public long queryMin(int L, int R) {
        return queryMin(0, 0, n - 1, L, R);
    }

    private long queryMin(int node, int L, int R, int QL, int QR) {
        pushDown(node, L, R);
        if (QR < L || R < QL) return Long.MAX_VALUE;
        if (QL <= L && R <= QR) return treeMin[node];
        int mid = L + (R - L) / 2;
        return Math.min(
                queryMin(node * 2 + 1, L, mid, QL, QR),
                queryMin(node * 2 + 2, mid + 1, R, QL, QR)
        );
    }

    public void add(int l, int r, int val) {
        add(0, 0, n - 1, l, r, val);
    }

    private void add(int node, int L, int R, int AL, int AR, long val) {
        pushDown(node, L, R);
        if (AR < L || R < AL) return;
        if (AL <= L && R <= AR) {
            applyAdd(node, L, R, val);
            return;
        }
        int mid = L + (R - L) / 2;
        add(node * 2 + 1, L, mid, AL, AR, val);
        add(node * 2 + 2, mid + 1, R, AL, AR, val);
        pushUp(node);
    }

    public void set(int l, int r, int val) {
        set(0, 0, n - 1, l, r, val);
    }

    private void set(int node, int L, int R, int SL, int SR, long val) {
        pushDown(node, L, R);
        if (SR < L || R < SL) return;
        if (SL <= L && R <= SR) {
            applySet(node, L, R, val);
            return;
        }
        int mid = L + (R - L) / 2;
        set(node * 2 + 1, L, mid, SL, SR, val);
        set(node * 2 + 2, mid + 1, R, SL, SR, val);
        pushUp(node);
    }

    public void update(int index, int newVal) {
        set(index, index, newVal);
    }

    private void pushDown(int node, int L, int R) {
        if (L == R) return;
        int mid = L + (R - L) / 2;
        int left = node * 2 + 1, right = node * 2 + 2;
        if (hasSet[node]) {
            applySet(left, L, mid, lazySet[node]);
            applySet(right, mid + 1, R, lazySet[node]);
            hasSet[node] = false;
        }
        if (lazyAdd[node] != 0) {
            applyAdd(left, L, mid, lazyAdd[node]);
            applyAdd(right, mid + 1, R, lazyAdd[node]);
            lazyAdd[node] = 0;
        }
    }

    private void applyAdd(int node, int L, int R, long val) {
        treeSum[node] += val * (R - L + 1);
        treeMax[node] += val;
        treeMin[node] += val;
        if (L != R) lazyAdd[node] += val;
    }

    private void applySet(int node, int L, int R, long val) {
        treeSum[node] = val * (R - L + 1);
        treeMax[node] = val;
        treeMin[node] = val;
        if (L != R) {
            hasSet[node] = true;
            lazySet[node] = val;
            lazyAdd[node] = 0;
        }
    }

    public long[] toArray() {
        long[] res = new long[n];
        collect(0, 0, n - 1, res);
        return res;
    }

    private void collect(int node, int L, int R, long[] res) {
        pushDown(node, L, R);
        if (L == R) {
            res[L] = treeSum[node];
            return;
        }
        int mid = L + (R - L) / 2;
        collect(node * 2 + 1, L, mid, res);
        collect(node * 2 + 2, mid + 1, R, res);
    }
}
