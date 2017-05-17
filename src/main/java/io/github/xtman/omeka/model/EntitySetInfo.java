package io.github.xtman.omeka.model;

import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class EntitySetInfo {
    private long _count;
    private String _url;
    private String _resource;

    public EntitySetInfo(JSONObject jo) {
        _count = JSONUtils.getLongValue(jo, "count", 0L);
        _url = JSONUtils.getStringValue(jo, "url");
        _resource = JSONUtils.getStringValue(jo, "resource");
    }

    public long count() {
        return _count;
    }

    public String url() {
        return _url;
    }

    public String resource() {
        return _resource;
    }

    public static EntitySetInfo instantiate(JSONObject jo, String key) {
        if (jo.has(key)) {
            return new EntitySetInfo(jo.getJSONObject(key));
        }
        return null;
    }
}
