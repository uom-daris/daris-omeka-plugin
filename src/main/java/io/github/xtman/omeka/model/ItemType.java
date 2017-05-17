package io.github.xtman.omeka.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;

public class ItemType extends EntityBase {

    // @formatter:off
    /*
    {
        "id":1,
        "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/item_types\/1",
        "name":"Text",
        "description":"A resource consisting primarily of words for reading. Examples include books, letters, dissertations, poems, newspapers, articles, archives of mailing lists. Note that facsimiles or images of texts are still of the genre Text.",
        "elements":[
            {
                "id":7,
                "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/elements\/7"
            },
            {
                "id":1,
                "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/elements\/1"
            }
        ],
        "items":{
            "count":0,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/items\/?item_type=1",
            "resource":"items"
        },
        "extended_resources":[
    
        ]
    }
    */
    // @formatter:on

    private String _name;
    private String _description;
    private List<EntityBase> _elements;
    private EntitySetInfo _items;
    private List<ExtendedResourceInfo> _extendedResources;

    public ItemType(JSONObject jo) throws Throwable {
        super(jo);
        _name = jo.getString("name");
        _description = jo.getString("description");

        _elements = jo.has("elements") ? new JSONUtils.ObjectArrayParser<EntityBase>() {
            @Override
            public EntityBase instantiateList(JSONObject jo) throws Throwable {
                return new EntityBase(jo);
            }
        }.parse(jo.getJSONArray("elements")) : null;

        _items = EntitySetInfo.instantiate(jo, "items");
        _extendedResources = ExtendedResourceInfo.instantiateList(jo, "extended_resources");
    }

    public String name() {
        return _name;
    }

    public String description() {
        return _description;
    }

    public List<EntityBase> elements() {
        if (_elements != null && !_elements.isEmpty()) {
            return java.util.Collections.unmodifiableList(_elements);
        }
        return null;
    }

    public EntitySetInfo items() {
        return _items;
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return Collections.unmodifiableList(_extendedResources);
    }

    public static List<ItemType> instantiateItemTypes(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<ItemType>() {

            @Override
            public ItemType instantiateList(JSONObject jo) throws Throwable {
                return new ItemType(jo);
            }
        }.parse(ja);
    }

    public static List<ItemType> instantiateList(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateItemTypes(jo.getJSONArray(key));
        }
        return null;
    }

}
