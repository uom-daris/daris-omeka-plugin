package io.github.xtman.omeka.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

/**
 * 
 * @author wliu5
 *
 */
public class ElementSet extends EntityBase {

    // @formatter:off
    /*
    {
        "id":1,
        "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/element_sets\/1",
        "name":"Dublin Core",
        "description":"The Dublin Core metadata element set is common to all Omeka records, including items, files, and collections. For more information see, http:\/\/dublincore.org\/documents\/dces\/.",
        "record_type":null,
        "elements":{
            "count":15,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/elements?element_set=1",
            "resource":"elements"
        },
        "extended_resources":[
    
        ]
    }
     */
    // @formatter:on

    private String _name;
    private String _description;
    private String _recordType;
    private EntitySetInfo _elements;
    private List<ExtendedResourceInfo> _extendedResources;

    public ElementSet(JSONObject jo) throws Throwable {
        super(jo);
        _name = jo.getString("name");
        _description = jo.getString("description");
        Object o = jo.get("record_type");
        if (o != null) {
            String v = String.valueOf(o);
            if (!"null".equalsIgnoreCase(v)) {
                _recordType = v;
            }
        }
        _elements = EntitySetInfo.instantiate(jo, "elements");
        _extendedResources = ExtendedResourceInfo.instantiateList(jo, "extended_resources");
    }

    public String name() {
        return _name;
    }

    public String description() {
        return _description;
    }

    public String recordType() {
        return _recordType;
    }

    public EntitySetInfo elements() {
        return _elements;
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return Collections.unmodifiableList(_extendedResources);
    }

    public static List<ElementSet> instantiateElementSets(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<ElementSet>() {

            @Override
            public ElementSet instantiateList(JSONObject jo) throws Throwable {
                return new ElementSet(jo);
            }
        }.parse(ja);
    }

    public static List<ElementSet> instantiateElementSets(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateElementSets(jo.getJSONArray(key));
        }
        return null;
    }

}
