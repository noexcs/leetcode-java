package org.noexcs.basic.algo;

public class UnionFind {
    int size;
    int[] parent;
    // 秩：用于记录树的高度
    // rank数组在路径压缩时不更新的原因：
    //   rank是高度上界估计，不是实时高度
    //   只需要相对大小信息来做合并决策
    //   更新rank会增加不必要的开销
    //   数学上证明这种设计能达到最优时间复杂度
    int[] rank;

    public UnionFind(int size) {
        this.size = size;
        parent = new int[size];
        rank = new int[size];
        // 初始化：每个元素的父节点指向自己
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) {
            return; // 已经在同一个集合中
        }

        // 按秩合并：将秩较小的树连接到秩较大的树下
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    public int find(int x) {
        // 路径压缩：在查找过程中将路径上的节点直接连接到根节点
        if (x != parent[x]) {
            parent[x] = find(parent[x]); // 递归进行路径压缩
        }
        return parent[x];
    }

    // 非递归版本的find方法（可选）
    public int findIterative(int x) {
        int root = x;
        // 先找到根节点
        while (root != parent[root]) {
            root = parent[root];
        }

        // 路径压缩：将路径上的所有节点直接连接到根节点
        while (x != root) {
            int next = parent[x];
            parent[x] = root;
            x = next;
        }

        return root;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}