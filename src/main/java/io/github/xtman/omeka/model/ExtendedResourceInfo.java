package io.github.xtman.omeka.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class ExtendedResourceInfo {

    public ExtendedResourceInfo(JSONObject jsonObject) {
        // TODO Auto-generated constructor stub
    }

    public static List<ExtendedResourceInfo> instantiateList(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<ExtendedResourceInfo>() {

            @Override
            public ExtendedResourceInfo instantiateList(JSONObject jo) throws Throwable {
                return new ExtendedResourceInfo(jo);
            }
        }.parse(ja);
    }

    public static List<ExtendedResourceInfo> instantiateList(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateList(jo.getJSONArray(key));
        }
        return null;
    }

}
