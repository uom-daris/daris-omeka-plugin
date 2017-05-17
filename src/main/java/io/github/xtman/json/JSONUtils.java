package io.github.xtman.json;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.io.StreamUtils;

public class JSONUtils {

    public static JSONObject parseJsonObject(InputStream in, String charsetName) throws Throwable {
        return readJsonObject(in, charsetName).getValue();
    }

    public static JSONObject parseJsonObject(InputStream in) throws Throwable {
        return readJsonObject(in, "UTF-8").getValue();
    }

    public static SimpleEntry<String, JSONObject> readJsonObject(InputStream in, String charsetName) throws Throwable {
        String response = StreamUtils.readString(in, charsetName == null ? "UTF-8" : charsetName);
        JSONObject jo = new JSONObject(response);
        return new SimpleEntry<String, JSONObject>(response, jo);
    }

    public static SimpleEntry<String, JSONObject> readJsonObject(InputStream in) throws Throwable {
        return readJsonObject(in, "UTF-8");
    }

    public static JSONArray parseJsonArray(InputStream in, String charsetName) throws Throwable {
        return readJsonArray(in, charsetName == null ? "UTF-8" : charsetName).getValue();
    }

    public static JSONArray parseJsonArray(InputStream in) throws Throwable {
        return readJsonArray(in, "UTF-8").getValue();
    }

    public static SimpleEntry<String, JSONArray> readJsonArray(InputStream in, String charsetName) throws Throwable {
        String response = StreamUtils.readString(in, charsetName == null ? "UTF-8" : charsetName);
        JSONArray ja = new JSONArray(response);
        return new SimpleEntry<String, JSONArray>(response, ja);
    }

    public static SimpleEntry<String, JSONArray> readJsonArray(InputStream in) throws Throwable {
        return readJsonArray(in, "UTF-8");
    }

    public static Date getDateValue(JSONObject jo, String key, SimpleDateFormat format) throws Throwable {
        String sv = jo.getString(key);
        if (sv == null) {
            return null;
        }
        return format.parse(sv);
    }

    public static Date getDateValue(JSONObject jo, String key, String format) throws Throwable {
        return getDateValue(jo, key, new SimpleDateFormat(format));
    }

    public static abstract class ObjectArrayParser<T> {

        public List<T> parse(JSONArray ja) throws Throwable {
            List<T> list = new ArrayList<T>();
            int n = ja.length();
            for (int i = 0; i < n; i++) {
                list.add(instantiateList(ja.getJSONObject(i)));
            }
            return list;
        }

        public abstract T instantiateList(JSONObject jo) throws Throwable;
    }

    public static String[] getStringArray(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            JSONArray ja = jo.getJSONArray(key);
            int n = ja.length();
            String[] sa = new String[n];
            for (int i = 0; i < n; i++) {
                sa[i] = ja.getString(i);
            }
            return sa;
        }
        return null;
    }

    public static List<String> getStringList(JSONObject jo, String key) throws Throwable {

        if (jo.has(key)) {
            JSONArray ja = jo.getJSONArray(key);
            int n = ja.length();
            List<String> sl = new ArrayList<String>(n);
            for (int i = 0; i < n; i++) {
                sl.add(ja.getString(i));
            }
            return sl;
        }
        return null;
    }

    public static String getStringValue(JSONObject jo, String key, boolean allowEmpty, String defaultValue) {
        if (jo != null) {
            if (jo.has(key)) {
                String value = jo.getString(key);
                if (!value.isEmpty() || allowEmpty) {
                    return value;
                }
            }
        }
        return defaultValue;
    }

    public static String getStringValue(JSONObject jo, String key, boolean allowEmpty) {
        return getStringValue(jo, key, allowEmpty, null);
    }

    public static String getStringValue(JSONObject jo, String key) {
        return getStringValue(jo, key, false);
    }

    public static long getLongValue(JSONObject jo, String key, long defaults) {
        if (jo != null && jo.has(key)) {
            return jo.getLong(key);
        }
        return defaults;
    }

}
