package io.github.xtman.omeka.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class EntityInfo extends EntityBase {

    private String _name;
    private String _resource;

    public EntityInfo(JSONObject jo) {
        super(jo);
        _name = jo.has("name") ? jo.getString("name") : null;
        _resource = jo.has("resource") ? jo.getString("resource") : null;

    }

    public String name() {
        return _name;
    }

    public String resource() {
        return _resource;
    }

    public static EntityInfo instantiate(JSONObject jo, String key) {
        if (jo.has(key)) {
            return new EntityInfo(jo.getJSONObject(key));
        }
        return null;
    }

    public static List<EntityInfo> instantiateList(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<EntityInfo>() {

            @Override
            public EntityInfo instantiateList(JSONObject jo) throws Throwable {
                return new EntityInfo(jo);
            }
        }.parse(ja);
    }

    public static List<EntityInfo> instantiateList(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateList(jo.getJSONArray(key));
        }
        return null;
    }
}
