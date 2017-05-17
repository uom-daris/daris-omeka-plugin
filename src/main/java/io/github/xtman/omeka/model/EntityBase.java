package io.github.xtman.omeka.model;

import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class EntityBase implements Entity {

    private long _id;
    private String _url;

    protected EntityBase(JSONObject jo) {
        _id = jo.getLong("id");
        _url = JSONUtils.getStringValue(jo, "url");
    }

    public long id() {
        return _id;
    }

    public String url() {
        return _url;
    }

}
