package org.noexcs.basic.algo;

public class KMP {

    public static void main(String[] args) {
        System.out.println(kmp("abcdabcd", "abcd"));
        System.out.println(kmp("abcdabcd", "cdab"));
    }
    public static int kmp(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return -1;
        }
        char[] str = s.toCharArray();
        char[] match = t.toCharArray();
        int[] next = getNextArray(match);
        int i = 0; // str的指针
        int j = 0; // match的指针
        while (i < str.length && j < match.length) {
            if (str[i] == match[j]) {
                i++;
                j++;
            } else if (next[j] == -1) { // j已经移动到头了，i只能往后走
                i++;
            } else { // j没有移动到头，i不动，j回退
                j = next[j];
            }
        }
        return j == match.length ? i - j : -1;
    }
    public static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2; // 当前求next数组的位置
        int cn = 0; // 当前字符的前一个字符的最长前后缀长度
        while (i < next.length) {
            if (match[i - 1] == match[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }
}
