package io.github.xtman.omeka.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class OmekaDateUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public static Date getDate(JSONObject jo, String key) throws Throwable {
        return JSONUtils.getDateValue(jo, key, DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
    }

}
