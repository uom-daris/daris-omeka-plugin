package io.github.xtman.omeka.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

// @formatter:off
/*
    {
        "html":false,
        "text":"identifier1",
        "element_set":{
            "id":1,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/element_sets\/1",
            "name":"Dublin Core",
            "resource":"element_sets"
        },
        "element":{
            "id":43,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/elements\/43",
            "name":"Identifier",
            "resource":"elements"
        }
    },
 */
// @formatter:on
/**
 * 
 * @author wliu5
 *
 */
public class ElementText {
    private boolean _html;
    private String _text;
    private EntityInfo _elementSet;
    private EntityInfo _element;

    public ElementText(JSONObject jo) {
        _html = jo.getBoolean("html");
        _text = jo.getString("text");
        _elementSet = EntityInfo.instantiate(jo, "element_set");
        _element = EntityInfo.instantiate(jo, "element");
    }

    public boolean isHtml() {
        return _html;
    }

    public String text() {
        return _text;
    }

    public EntityInfo elementSet() {
        return _elementSet;
    }

    public EntityInfo element() {
        return _element;
    }

    public static List<ElementText> instantiateList(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<ElementText>() {

            @Override
            public ElementText instantiateList(JSONObject jo) throws Throwable {
                return new ElementText(jo);
            }
        }.parse(ja);
    }

    public static List<ElementText> instantiateList(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateList(jo.getJSONArray(key));
        }
        return null;
    }

}
