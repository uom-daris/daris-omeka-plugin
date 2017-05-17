package io.github.xtman.util;

public class PathUtils {

    public static String join(String first, String... more) {
        StringBuilder sb = new StringBuilder(first);
        if (more != null && more.length > 0) {
            boolean lastTrailingSlash = first.endsWith("/");
            for (int i = 0; i < more.length; i++) {
                String component = more[i];
                if (component != null && !component.isEmpty()) {
                    if (!lastTrailingSlash) {
                        sb.append("/");
                    }
                    sb.append(trimLeadingSlash(component));
                    lastTrailingSlash = component.endsWith("/");
                }
            }
        }
        return sb.toString();
    }

    public static String trimLeadingSlash(String s) {
        if (s == null) {
            return null;
        }
        while (s.startsWith("/")) {
            s = s.substring(1);
            s = s.trim();
        }
        return s;
    }

    public static String trimTrailingSlash(String s) {
        if (s == null) {
            return null;
        }
        while (s.endsWith("/")) {
            s = s.substring(0, s.length() - 1);
            s = s.trim();
        }
        return s;
    }

    public static String trimSlash(String s) {
        return trimLeadingSlash(trimTrailingSlash(s));
    }

    public static void main(String[] args) {
//        System.out.println(
//                join("http://www.exe.com/", "/abc/", "/def?", "a=", "bb"));
//        System.out.println(trimTrailingSlash("abc//"));
    }
}
