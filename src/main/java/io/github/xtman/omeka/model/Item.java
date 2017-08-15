package io.github.xtman.omeka.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;
import io.github.xtman.omeka.client.util.OmekaDateUtils;

public class Item extends EntityBase {

    // @formatter:off
    /*
    {
        "id":8,
        "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/items\/8",
        "public":true,
        "featured":false,
        "added":"2017-05-10T05:25:40+00:00",
        "modified":"2017-05-10T05:33:55+00:00",
        "item_type":{
            "id":14,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/item_types\/14",
            "name":"Dataset",
            "resource":"item_types"
        },
        "collection":{
            "id":1,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/collections\/1",
            "resource":"collections"
        },
        "owner":{
            "id":1,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/users\/1",
            "resource":"users"
        },
        "files":{
            "count":0,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/files?item=8",
            "resource":"files"
        },
        "tags":[
            {
                "id":1,
                "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/tags\/1",
                "name":"tag1",
                "resource":"tags"
            }
        ],
        "element_texts":[
            {
                "html":false,
                "text":"Test item",
                "element_set":{
                    "id":1,
                    "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/element_sets\/1",
                    "name":"Dublin Core",
                    "resource":"element_sets"
                },
                "element":{
                    "id":50,
                    "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/elements\/50",
                    "name":"Title",
                    "resource":"elements"
                }
            }
        ],
        "extended_resources":[

        ]
    }
    */
    // @formatter:on
    private boolean _public;
    private boolean _featured;
    private Date _added;
    private Date _modified;
    private EntityInfo _itemType;
    private EntityInfo _collection;
    private EntityInfo _owner;
    private EntitySetInfo _files;
    private List<EntityInfo> _tags;
    private List<ElementText> _elementTexts;
    private List<ExtendedResourceInfo> _extendedResources;

    public Item(JSONObject jo) throws Throwable {
        super(jo);
        _public = jo.getBoolean("public");
        _featured = jo.getBoolean("featured");
        _added = OmekaDateUtils.getDate(jo, "added");
        _modified = OmekaDateUtils.getDate(jo, "modified");
        _itemType = (jo.has("item_type") && !jo.isNull("item_type")) ? new EntityInfo(jo.getJSONObject("item_type"))
                : null;
        _collection = (jo.has("collection") && !jo.isNull("collection"))
                ? new EntityInfo(jo.getJSONObject("collection")) : null;
        _owner = new EntityInfo(jo.getJSONObject("owner"));
        _files = new EntitySetInfo(jo.getJSONObject("files"));
        _tags = EntityInfo.instantiateList(jo, "tags");
        _elementTexts = ElementText.instantiateList(jo, "element_texts");
        _extendedResources = ExtendedResourceInfo.instantiateList(jo, "extended_resources");
    }

    public boolean isPublic() {
        return _public;
    }

    public boolean isFeatured() {
        return _featured;
    }

    public Date added() {
        return _added;
    }

    public Date modified() {
        return _modified;
    }

    public EntityInfo itemType() {
        return _itemType;
    }

    public EntityInfo collection() {
        return _collection;
    }

    public Long collectionId() {
        if (_collection == null) {
            return null;
        } else {
            return _collection.id();
        }
    }

    public EntityInfo owner() {
        return _owner;
    }

    public EntitySetInfo files() {
        return _files;
    }

    public List<EntityInfo> tags() {
        return Collections.unmodifiableList(_tags);
    }

    public List<ElementText> elementTexts() {
        return Collections.unmodifiableList(_elementTexts);
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return Collections.unmodifiableList(_extendedResources);
    }

    public static List<Item> instantiateItems(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<Item>() {

            @Override
            public Item instantiateList(JSONObject jo) throws Throwable {
                return new Item(jo);
            }
        }.parse(ja);
    }

    public static List<Item> instantiateItems(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateItems(jo.getJSONArray(key));
        }
        return null;
    }

}
