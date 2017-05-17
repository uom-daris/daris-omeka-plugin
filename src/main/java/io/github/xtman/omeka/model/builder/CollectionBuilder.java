package io.github.xtman.omeka.model.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CollectionBuilder implements EntityBuilder {

    private boolean _public;
    private boolean _featured;
    private List<ElementTextBuilder> _elementTexts;

    public CollectionBuilder(boolean isPublic, boolean isFeatured,
            java.util.Collection<ElementTextBuilder> elementTexts) {
        _public = isPublic;
        _featured = isFeatured;
        _elementTexts = new ArrayList<ElementTextBuilder>();
        if (elementTexts != null && !elementTexts.isEmpty()) {
            _elementTexts.addAll(elementTexts);
        }
    }

    public CollectionBuilder() {
        this(true, false, null);
    }

    public CollectionBuilder setPublic(boolean isPublic) {
        _public = isPublic;
        return this;
    }

    public CollectionBuilder setFeatured(boolean isFeatured) {
        _featured = isFeatured;
        return this;
    }

    public CollectionBuilder setElementTexts(java.util.Collection<ElementTextBuilder> elementTexts) {
        _elementTexts.clear();
        if (elementTexts != null && !elementTexts.isEmpty()) {
            _elementTexts.addAll(elementTexts);
        }
        return this;
    }

    public CollectionBuilder addElementText(boolean html, String text, Long elementId) {
        _elementTexts.add(new ElementTextBuilder(html, text, elementId));
        return this;
    }

    @Override
    public JSONObject build() {
        JSONObject jo = new JSONObject();
        jo.put("public", _public);
        jo.put("featured", _featured);
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
