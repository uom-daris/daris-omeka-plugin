package io.github.xtman.omeka.model;

import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class Site implements Entity {

    private String _url;
    private String _version;
    private String _title;
    private String _description;
    private String _author;
    private String _copyright;

    public Site(JSONObject jo) {
        _url = jo.getString("omeka_url");
        _version = jo.getString("omeka_version");
        _title = JSONUtils.getStringValue(jo, "title");
        _description = JSONUtils.getStringValue(jo, "description");
        _author = JSONUtils.getStringValue(jo, "author");
        _copyright = JSONUtils.getStringValue(jo, "copyright");
    }

    public String omekaUrl() {
        return _url;
    }

    public String omekaVersion() {
        return _version;
    }

    public String title() {
        return _title;
    }

    public String description() {
        return _description;
    }

    public String author() {
        return _author;
    }

    public String copyright() {
        return _copyright;
    }

}
