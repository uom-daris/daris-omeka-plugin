package io.github.xtman.omeka.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;
import io.github.xtman.omeka.client.util.OmekaDateUtils;

/**
 * 
 * @author wliu5
 *
 *         https://omeka-test.cloud.unimelb.edu.au/omeka14/api/collections/1?pretty_print
 */

public class Collection extends EntityBase {

    private boolean _public;
    private boolean _featured;
    private Date _added;
    private Date _modified;
    private EntityInfo _owner;
    private EntitySetInfo _items;
    private List<ElementText> _elementTexts;
    private List<ExtendedResourceInfo> _extendedResources;

    // @formatter:off
    /*
        "id":1,
        "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/collections\/1",
        "public":true,
        "featured":false,
        "added":"2017-05-09T11:42:04+00:00",
        "modified":"2017-05-09T11:42:04+00:00",
     */
    // @formatter:on
    public Collection(JSONObject jo) throws Throwable {
        super(jo);
        _public = jo.getBoolean("public");
        _featured = jo.getBoolean("featured");
        _added = OmekaDateUtils.getDate(jo, "added");
        _modified = OmekaDateUtils.getDate(jo, "modified");
        _owner = EntityInfo.instantiate(jo, "owner");
        _items = EntitySetInfo.instantiate(jo, "items");
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

    public EntitySetInfo items() {
        return _items;
    }

    public EntityInfo owner() {
        return _owner;
    }

    public List<ElementText> elementTexts() {
        return Collections.unmodifiableList(_elementTexts);
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return Collections.unmodifiableList(_extendedResources);
    }

    public static List<Collection> instantiateCollections(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<Collection>() {

            @Override
            public Collection instantiateList(JSONObject jo) throws Throwable {
                return new Collection(jo);
            }
        }.parse(ja);
    }

    public static List<Collection> instantiateCollections(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateCollections(jo.getJSONArray(key));
        }
        return null;
    }

}
