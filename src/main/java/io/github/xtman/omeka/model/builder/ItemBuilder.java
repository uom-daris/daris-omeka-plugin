package io.github.xtman.omeka.model.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ItemBuilder implements EntityBuilder {

    private Long _itemTypeId;
    private Long _collectionId;
    private boolean _public;
    private boolean _featured;
    private List<String> _tags;
    private List<ElementTextBuilder> _elementTexts;

    /*
     * { "item_type": {"id": 1}, "collection": {"id": 1}, "public": true,
     * "featured": false, "tags": [ {"name": "foo"}, {"name": "bar"} ],
     * "element_texts": [ { "html": false, "text":
     * "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", "element":
     * {"id": 1} } ] }
     */

    public ItemBuilder(Long itemType, Long collection, boolean isPublic, boolean isFeatured,
            java.util.Collection<String> tags, java.util.Collection<ElementTextBuilder> elementTexts) {
        _itemTypeId = itemType;
        _collectionId = collection;
        _public = isPublic;
        _featured = isFeatured;
        _tags = new ArrayList<String>();
        if (tags != null && !tags.isEmpty()) {
            _tags.addAll(tags);
        }
        _elementTexts = new ArrayList<ElementTextBuilder>();
        if (elementTexts != null && !elementTexts.isEmpty()) {
            _elementTexts.addAll(elementTexts);
        }
    }

    public ItemBuilder() {
        this(null, null, true, false, null, null);
    }

    public ItemBuilder setItemType(long itemTypeId) {
        _itemTypeId = itemTypeId;
        return this;
    }

    public ItemBuilder setCollection(long collectionId) {
        _collectionId = collectionId;
        return this;
    }

    public ItemBuilder setPublic(boolean isPublic) {
        _public = isPublic;
        return this;
    }

    public ItemBuilder setFeatured(boolean featured) {
        _featured = featured;
        return this;
    }

    public ItemBuilder setTags(java.util.Collection<String> tags) {
        _tags.clear();
        if (tags != null && !tags.isEmpty()) {
            _tags.addAll(tags);
        }
        return this;
    }

    public ItemBuilder addTag(String tag) {
        _tags.add(tag);
        return this;
    }

    public ItemBuilder setElementTexts(java.util.Collection<ElementTextBuilder> elementTexts) {
        _elementTexts.clear();
        if (elementTexts != null && !elementTexts.isEmpty()) {
            _elementTexts.addAll(elementTexts);
        }
        return this;
    }

    public ItemBuilder addElementText(boolean html, String text, Long elementId) {
        _elementTexts.add(new ElementTextBuilder(html, text, elementId));
        return this;
    }

    @Override
    public JSONObject build() {
        JSONObject jo = new JSONObject();
        if (_itemTypeId != null) {
            JSONObject itemTypeJO = new JSONObject();
            itemTypeJO.put("id", _itemTypeId);
            jo.put("item_type", itemTypeJO);
        }
        if (_collectionId != null) {
            JSONObject collectionJO = new JSONObject();
            collectionJO.put("id", _collectionId);
            jo.put("collection", collectionJO);
        }
        jo.put("public", _public);
        jo.put("featured", _featured);

        JSONArray tagsJA = new JSONArray();
        if (_tags != null && !_tags.isEmpty()) {
            for (String tag : _tags) {
                JSONObject tagJO = new JSONObject();
                tagJO.put("name", tag);
                tagsJA.put(tagJO);
            }
        }
        jo.put("tags", tagsJA);

        JSONArray elementTextJA = new JSONArray();
        if (!_elementTexts.isEmpty()) {
            for (ElementTextBuilder et : _elementTexts) {
                elementTextJA.put(et.build());
            }
        }
        jo.put("element_texts", elementTextJA);
        return jo;
    }

}
