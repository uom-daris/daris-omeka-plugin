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
 *         https://omeka-test.cloud.unimelb.edu.au/omeka14/api/elements/51?pretty_print
 *
 */
public class Element extends EntityBase {

    // @formatter:off
    /*
        {
            "id":51,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/elements\/51",
            "order":13,
            "name":"Type",
            "description":"The nature or genre of the resource",
            "comment":"",
            "element_set":{
                "id":1,
                "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/element_sets\/1",
                "resource":"element_sets"
            },
            "extended_resources":[
        
            ]
        }
     */
    // @formatter:on

    private Long _order;
    private String _name;
    private String _description;
    private String _comment;
    private EntityInfo _elementSet;
    private List<ExtendedResourceInfo> _extendedResources;

    public Element(JSONObject jo) throws Throwable {
        super(jo);
        _order = (!jo.has("order") || jo.isNull("order")) ? null : jo.getLong("order");
        _name = jo.getString("name");
        _description = jo.getString("description");
        _comment = jo.getString("comment");
        _elementSet = EntityInfo.instantiate(jo, "element_set");
        _extendedResources = ExtendedResourceInfo.instantiateList(jo, "extended_resources");
    }

    public Long order() {
        return _order;
    }

    public String name() {
        return _name;
    }

    public String description() {
        return _description;
    }

    public String comment() {
        return _comment;
    }

    public EntityInfo elementSet() {
        return _elementSet;
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return Collections.unmodifiableList(_extendedResources);
    }

    public static List<Element> instantiateElements(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<Element>() {

            @Override
            public Element instantiateList(JSONObject jo) throws Throwable {
                return new Element(jo);
            }
        }.parse(ja);
    }

    public static List<Element> instantiateElements(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateElements(jo.getJSONArray(key));
        }
        return null;
    }

}
