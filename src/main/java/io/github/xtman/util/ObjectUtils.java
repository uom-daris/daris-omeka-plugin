package io.github.xtman.util;

public class ObjectUtils {

    public static boolean equals(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if (a != null) {
            return a.equals(b);
        } else {
            return b.equals(a);
        }
    }

}
