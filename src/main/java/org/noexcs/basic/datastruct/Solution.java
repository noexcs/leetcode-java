package org.noexcs.basic.datastruct;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int similarPairs(String[] words) {
        String[] words_p = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            words[i] = t(words[i]);
        }
        HashMap<String, Integer> m = new HashMap<>();
        for (String s : words) {
            m.put(s, m.getOrDefault(s, 0) + 1);
        }
        int ans = 0;
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            ans += entry.getValue() * (entry.getValue() - 1) / 2;
        }
        return ans;
    }

    private String t(String s) {
        char[] cs = s.toCharArray();
        Arrays.sort(cs);
        return new String(cs);
    }

    private char[] deduplicate(char[] cs) {
        boolean[] seen = new boolean[26];
        int j = 0;
        for (char c : cs) {
            if (!seen[c - 'a']) {
                seen[c - 'a'] = true;
                cs[j++] = c;
            }
        }
        return Arrays.copyOf(cs, j);
    }
}