package org.noexcs.basic.algo;

public class Manacher {

    public static String manacher(String s) {
        if (s == null || s.isEmpty())
            return "";
        // 1221 -> #1#2#2#1#
        char[] str = manacherString(s);
//        s.indexOf()
        // 回文半径，不含中心位置
        int[] radius = new int[str.length];
        int R = -1, C = -1;
        int maxRadius = 0, maxRadiusCenter = 0;

        for (int i = 0; i < str.length; i++) {
            if (R < i) {
                extendPosition(str, radius, i);
            } else {
                int i_mirror = C - (i - C);
                int i_mirrorLeft = i_mirror - radius[i_mirror];
                int L = C - radius[C];

                if (L < i_mirrorLeft) {
                    radius[i] = radius[i_mirror];
                } else if (i_mirrorLeft == L) {
                    extendPosition(str, radius, i);
                } else {
                    radius[i] = i_mirror - L;
                }
            }
            if (i + radius[i] > R) {
                R = i + radius[i];
                C = i;
            }
            if (radius[i] > maxRadius) {
                maxRadius = radius[i];
                maxRadiusCenter = i;
            }
        }

        return s.substring((maxRadiusCenter - maxRadius) / 2, (maxRadiusCenter + maxRadius) / 2);
    }

    private static void extendPosition(char[] str, int[] palindromeRadius, int i) {
        palindromeRadius[i] = 0;
        while (0 <= i - (palindromeRadius[i] + 1) && i + (palindromeRadius[i] + 1) < str.length) {
            if (str[i - (palindromeRadius[i] + 1)] == str[i + (palindromeRadius[i] + 1)]) {
                palindromeRadius[i]++;
            } else {
                break;
            }
        }
    }

    private static char[] manacherString(String s) {
        char[] str = new char[s.length() * 2 + 1];
        for (int i = 0; i < str.length; i++) {
            str[i] = (i & 1) == 0 ? '#' : s.charAt(i / 2);
        }
        return str;
    }
}
