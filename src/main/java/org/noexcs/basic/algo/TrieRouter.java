package org.noexcs.basic.algo;

import java.util.*;

public class TrieRouter {
    private final Node root = new Node();

    public void add(String pattern, String name) {
        String[] parts = split(pattern);
        Node cur = root;
        for (String part : parts) {
            if (isParam(part)) {
                if (cur.paramChild == null) {
                    cur.paramChild = new Node();
                    cur.paramChild.paramName = part.substring(1, part.length() - 1);
                }
                cur = cur.paramChild;
            } else {
                cur.children.putIfAbsent(part, new Node());
                cur = cur.children.get(part);
            }
        }
        cur.endpoint = name;
    }

    public String get(String url) {
        String[] parts = split(url);
        Node cur = root;
        for (String part : parts) {
            Node next = cur.children.get(part);
            if (next != null) {
                cur = next;
            } else if (cur.paramChild != null) {
                cur = cur.paramChild;
                // 这里可以记录参数值，先忽略
            } else {
                return null;
            }
        }
        return cur.endpoint;
    }

    private String[] split(String path) {
        return Arrays.stream(path.split("/"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    private boolean isParam(String part) {
        return part.startsWith("{") && part.endsWith("}");
    }

    private static class Node {
        Map<String, Node> children = new HashMap<>();
        Node paramChild;      // 支持一个参数段
        String paramName;     // 参数名（比如 id）
        String endpoint;      // 如果是终点，存接口名
    }

    // demo
    public static void main(String[] args) {
        TrieRouter router = new TrieRouter();
        router.add("/api/p/{id}", "getProduct");
        router.add("/api/u/{id}/info", "getUserInfo");

        System.out.println(router.get("/api/p/123"));      // getProduct
        System.out.println(router.get("/api/p/456"));      // getProduct
        System.out.println(router.get("/api/u/42/info"));  // getUserInfo
        System.out.println(router.get("/api/x/42"));       // null
    }
}
