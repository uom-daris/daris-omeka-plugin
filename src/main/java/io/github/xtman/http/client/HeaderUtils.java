package io.github.xtman.http.client;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class HeaderUtils {

    public static void printHeaderFields(Map<String, List<String>> headerFields,
            PrintStream out) {

        if (headerFields == null || headerFields.isEmpty()) {
            return;
        }
        for (String key : headerFields.keySet()) {
            out.print(key + ": ");
            String value = joinHeaderFieldValues(headerFields.get(key));
            if (value != null) {
                out.print(value);
            }
            out.println("");
        }

    }

    public static String joinHeaderFieldValues(List<String> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(values.get(i));
        }
        String v = sb.toString();
        if (v.isEmpty()) {
            return null;
        }
        return v;
    }

}
