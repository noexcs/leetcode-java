package org.noexcs.basic.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class Kruskal {
    public static List<Edge> kruskal(List<Edge> edges, int vertices) {
        // 按权重排序所有边
        Collections.sort(edges);

        UnionFind uf = new UnionFind(vertices);
        List<Edge> result = new ArrayList<>();

        for (Edge edge : edges) {
            int rootSrc = uf.find(edge.src);
            int rootDest = uf.find(edge.dest);

            // 如果不在同一个集合中，加入MST并合并集合
            if (rootSrc != rootDest) {
                result.add(edge);
                uf.union(rootSrc, rootDest);

                // 当MST包含V-1条边时停止
                if (result.size() == vertices - 1) {
                    break;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // 示例用法
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        int vertices = 4;
        List<Edge> mst = kruskal(edges, vertices);

        System.out.println("最小生成树的边:");
        int totalWeight = 0;
        for (Edge edge : mst) {
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
            totalWeight += edge.weight;
        }
        System.out.println("总权重: " + totalWeight);
    }
}
