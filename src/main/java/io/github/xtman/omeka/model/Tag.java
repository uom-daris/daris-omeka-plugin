package io.github.xtman.omeka.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class Tag extends EntityInfo {

    // @formatter:off
    /*
    {
        "id":1,
        "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/tags\/1",
        "name":"tag1",
        "extended_resources":[
    
        ]
    }
    */
    // @formatter:on
    private List<ExtendedResourceInfo> _extendedResources;

    public Tag(JSONObject jo) throws Throwable {
        super(jo);
        _extendedResources = ExtendedResourceInfo.instantiateList(jo, "extended_resources");
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return Collections.unmodifiableList(_extendedResources);
    }

    public static List<Tag> instantiateTags(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<Tag>() {

            @Override
            public Tag instantiateList(JSONObject jo) throws Throwable {
                return new Tag(jo);
            }
        }.parse(ja);
    }

    public static List<Tag> instantiateTags(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateTags(jo.getJSONArray(key));
        }
        return null;
    }
}
