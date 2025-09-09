package org.noexcs.basic.algo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Dijkstra {

    public static int UnreachableDistance = -1;
    public static int UnreachableDistanceV2 = Integer.MAX_VALUE;

    public static HashMap<Integer, Integer> dijkstraHashMap(int[][] adjacencyMatrix, int startVertex) {
        int n = adjacencyMatrix.length;

        HashMap<Integer, Integer> alreadyReached = new HashMap<>();
        PriorityQueue<int[]> candidates = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        candidates.offer(new int[]{startVertex, 0});

        while (!candidates.isEmpty()) {
            int[] current = candidates.poll();
            int vertex = current[0];
            int distance = current[1];
            if (alreadyReached.containsKey(vertex)) {
                continue;
            }

            alreadyReached.put(vertex, distance);
            for (int neighbor = 0; neighbor < adjacencyMatrix[vertex].length; neighbor++) {
                if (adjacencyMatrix[vertex][neighbor] != UnreachableDistance && !alreadyReached.containsKey(neighbor)) {
                    candidates.offer(new int[]{neighbor, distance + adjacencyMatrix[vertex][neighbor]});
                }
            }
        }

        return alreadyReached;
    }

    public static int[] dijkstraArray(int[][] adjacencyMatrix, int startVertex) {
        int n = adjacencyMatrix.length;

        int[] alreadyReached = new int[n];
        Arrays.fill(alreadyReached, UnreachableDistance);

        PriorityQueue<int[]> candidates = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        candidates.offer(new int[]{startVertex, 0});

        while (!candidates.isEmpty()) {
            int[] current = candidates.poll();
            int vertex = current[0];
            int distance = current[1];
            if (alreadyReached[vertex] != UnreachableDistance) {
                continue;
            }

            alreadyReached[vertex] = distance;
            for (int neighbor = 0; neighbor < adjacencyMatrix[vertex].length; neighbor++) {
                if (adjacencyMatrix[vertex][neighbor] != UnreachableDistance && alreadyReached[neighbor] == UnreachableDistance) {
                    candidates.offer(new int[]{neighbor, distance + adjacencyMatrix[vertex][neighbor]});
                }
            }
        }

        return alreadyReached;
    }

    public static void printDistances(int[] distances, int startVertex) {
        System.out.println("最短距离从节点 " + startVertex + " 到其他节点:");
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == UnreachableDistance) {
                System.out.println("节点 " + i + ": 不可达");
            } else {
                System.out.println("节点 " + i + ": " + distances[i]);
            }
        }
    }

    public static void printDistances(HashMap<Integer, Integer> distances, int startVertex) {
        System.out.println("最短距离从节点 " + startVertex + " 到其他节点:");
        for (int i = 0; i < distances.size(); i++) {
            if (distances.containsKey(i)) {
                System.out.println("节点 " + i + ": " + distances.get(i));
            } else {
                System.out.println("节点 " + i + ": ∞");
            }
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 4, -1, -1, -1, -1, -1, 8, -1},
                {4, 0, 8, -1, -1, -1, -1, 11, -1},
                {-1, 8, 0, 7, -1, 4, -1, -1, 2},
                {-1, -1, 7, 0, 9, 14, -1, -1, -1},
                {-1, -1, -1, 9, 0, 10, -1, -1, -1},
                {-1, -1, 4, 14, 10, 0, 2, -1, -1},
                {-1, -1, -1, -1, -1, 2, 0, 1, 6},
                {8, 11, -1, -1, -1, -1, 1, 0, 7},
                {-1, -1, 2, -1, -1, -1, 6, 7, 0}
        };

        int[] result = dijkstraArray(graph, 0);
        printDistances(result, 0);

        HashMap<Integer, Integer> resultMap = dijkstraHashMap(graph, 0);
        printDistances(resultMap, 0);
    }
}
