package org.noexcs.basic.algo;

import java.util.*;
import java.util.regex.*;

public class SimpleRouter {
    // 保存模式和接口名称
    private final List<Route> routes = new ArrayList<>();

    // 添加路由
    public void add(String pattern, String name) {
        // 把 {xxx} 转换成正则里的 (\\w+)，简化处理只支持字母数字下划线
        String regex = pattern.replaceAll("\\{[^/]+}", "([\\\\w-]+)");
        Pattern compiled = Pattern.compile("^" + regex + "$");
        routes.add(new Route(pattern, compiled, name));
    }

    // 根据url查找匹配的接口
    public String get(String url) {
        for (Route route : routes) {
            if (route.regex.matcher(url).matches()) {
                return route.name;
            }
        }
        return null; // 没匹配到
    }

    private static class Route {
        String pattern;
        Pattern regex;
        String name;

        Route(String pattern, Pattern regex, String name) {
            this.pattern = pattern;
            this.regex = regex;
            this.name = name;
        }
    }

    // demo
    public static void main(String[] args) {
        SimpleRouter router = new SimpleRouter();
        router.add("/api/p/a/{id}", "getProduct2");
        router.add("/api/p/{id}", "getProduct1");
        router.add("/api/u/{id}/info", "getUserInfo");

        System.out.println(router.get("/api/p/123"));      // 输出 getProduct
        System.out.println(router.get("/api/p/a/123"));      // 输出 null
        System.out.println(router.get("/api/p/456"));      // 输出 getProduct
        System.out.println(router.get("/api/u/42/info"));  // 输出 getUserInfo
        System.out.println(router.get("/api/x/42"));       // 输出 null
    }
}
