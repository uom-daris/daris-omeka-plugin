package io.github.xtman.omeka.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class User extends EntityBase {
    // @formatter:off
    /*
        {
            "id":1,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/users\/1",
            "username":"omeka14",
            "name":"Super User",
            "active":true,
            "email":"rick.truong@unimelb.edu.au",
            "role":"super",
            "extended_resources":[
        
            ]
        }
     */
    // @formatter:on

    private String _username;
    private String _name;
    private boolean _active;
    private String _email;
    private String _role;
    private List<ExtendedResourceInfo> _extendedResources;

    public User(JSONObject jo) throws Throwable {
        super(jo);
        _username = JSONUtils.getStringValue(jo, "username");
        _name = JSONUtils.getStringValue(jo, "name");
        _active = jo.getBoolean("active");
        _email = JSONUtils.getStringValue(jo, "email");
        _role = JSONUtils.getStringValue(jo, "role");
        _extendedResources = ExtendedResourceInfo.instantiateList(jo, "extended_resources");
    }

    public String username() {
        return _username;
    }

    public String name() {
        return _name;
    }

    public boolean isActive() {
        return _active;
    }

    public String email() {
        return _email;
    }

    public String role() {
        return _role;
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return _extendedResources;
    }

    public static List<User> instantiateList(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<User>() {

            @Override
            public User instantiateList(JSONObject jo) throws Throwable {
                return new User(jo);
            }
        }.parse(ja);
    }

    public static List<User> instantiateList(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateList(jo.getJSONArray(key));
        }
        return null;
    }

}
