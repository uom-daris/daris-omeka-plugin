package io.github.xtman.util;

import java.util.List;

public class StringUtils {

    public static String capitalize(String str) {
        return capitalize(str, true);
    }

    public static String capitalize(String str, boolean uncapitalizeOthers) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.substring(0, 1).toUpperCase());
        if (str.length() > 1) {
            if (uncapitalizeOthers) {
                sb.append(str.substring(1).toLowerCase());
            } else {
                sb.append(str.substring(1));
            }
        }
        return sb.toString();
    }

    public static String capitalize(String str, boolean uncapitalizeOthers, char... delimiters) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        if (delimiters == null || delimiters.length == 0) {
            return capitalize(str, uncapitalizeOthers);
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);
            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(uncapitalizeOthers ? Character.toLowerCase(ch) : ch);
            }
        }
        return buffer.toString();
    }

    public static String capitalize(String str, char... delimiters) {
        return capitalize(str, false, delimiters);
    }

    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (int i = 0; i < delimiters.length; i++) {
            if (ch == delimiters[i]) {
                return true;
            }
        }
        return false;
    }

    public static String join(List<String> tokens, String delimiter) {
        if (tokens == null || tokens.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(tokens.get(i));
        }
        return sb.toString();
    }

}
