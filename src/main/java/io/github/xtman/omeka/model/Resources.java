package io.github.xtman.omeka.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class Resources implements Entity {

    public static class TypeInfo {
        private String _controller;
        private String _recordType;
        private List<String> _actions;
        private List<String> _indexParams;
        private String _url;

        public TypeInfo(JSONObject jo) throws Throwable {
            _controller = JSONUtils.getStringValue(jo, "controller");
            _recordType = JSONUtils.getStringValue(jo, "record_type");
            _actions = JSONUtils.getStringList(jo, "actions");
            _indexParams = JSONUtils.getStringList(jo, "index_params");
            _url = JSONUtils.getStringValue(jo, "url");
        }

        public String controller() {
            return _controller;
        }

        public String recordType() {
            return _recordType;
        }

        public List<String> actions() {
            if (_actions != null) {
                return Collections.unmodifiableList(_actions);
            }
            return null;
        }

        public List<String> indexParams() {
            if (_indexParams != null) {
                return Collections.unmodifiableList(_indexParams);
            }
            return null;
        }

        public String url() {
            return _url;
        }

    }

    private TypeInfo _site;
    private TypeInfo _resources;
    private TypeInfo _collections;
    private TypeInfo _items;
    private TypeInfo _files;
    private TypeInfo _itemTypes;
    private TypeInfo _elements;
    private TypeInfo _elementSets;
    private TypeInfo _users;
    private TypeInfo _tags;

    public Resources(JSONObject jo) throws Throwable {
        _site = jo.has("site") ? new TypeInfo(jo.getJSONObject("site")) : null;
        _resources = jo.has("resources") ? new TypeInfo(jo.getJSONObject("resources")) : null;
        _collections = jo.has("collections") ? new TypeInfo(jo.getJSONObject("collections")) : null;
        _items = jo.has("items") ? new TypeInfo(jo.getJSONObject("items")) : null;
        _files = jo.has("files") ? new TypeInfo(jo.getJSONObject("files")) : null;
        _itemTypes = jo.has("item_types") ? new TypeInfo(jo.getJSONObject("item_types")) : null;
        _elements = jo.has("elements") ? new TypeInfo(jo.getJSONObject("elements")) : null;
        _elementSets = jo.has("element_sets") ? new TypeInfo(jo.getJSONObject("element_sets")) : null;
        _users = jo.has("users") ? new TypeInfo(jo.getJSONObject("users")) : null;
        _tags = jo.has("tags") ? new TypeInfo(jo.getJSONObject("tags")) : null;
    }

    public TypeInfo site() {
        return _site;
    }

    public TypeInfo resources() {
        return _resources;
    }

    public TypeInfo collections() {
        return _collections;
    }

    public TypeInfo items() {
        return _items;
    }

    public TypeInfo files() {
        return _files;
    }

    public TypeInfo itemTypes() {
        return _itemTypes;
    }

    public TypeInfo elements() {
        return _elements;
    }

    public TypeInfo elementSets() {
        return _elementSets;
    }

    public TypeInfo users() {
        return _users;
    }

    public TypeInfo tags() {
        return _tags;
    }

}
