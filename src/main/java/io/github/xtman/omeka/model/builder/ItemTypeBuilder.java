package io.github.xtman.omeka.model.builder;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ItemTypeBuilder implements EntityBuilder {

    private String _name;
    private String _description;
    private List<Long> _elements;

    public ItemTypeBuilder(String name, String description, java.util.Collection<Long> elements) {
        _name = name;
        _description = description;
        _elements = new ArrayList<Long>();
        if (elements != null && !elements.isEmpty()) {
            _elements.addAll(elements);
        }
    }

    public ItemTypeBuilder() {
        this(null, null, null);
    }

    public ItemTypeBuilder setName(String name) {
        _name = name;
        return this;
    }

    public ItemTypeBuilder setDescription(String description) {
        _description = description;
        return this;
    }

    public ItemTypeBuilder setElements(java.util.Collection<Long> elements) {
        _elements.clear();
        if (elements != null && !elements.isEmpty()) {
            _elements.addAll(elements);
        }
        return this;
    }

    @Override
    public JSONObject build() {
        JSONObject jo = new JSONObject();
        if (_name != null) {
            jo.put("name", _name);
        }
        if (_description != null) {
            jo.put("description", _description);
        }
        JSONArray elementsJA = new JSONArray();
        for (Long element : _elements) {
            JSONObject elementJO = new JSONObject();
            elementJO.put("id", element);
            elementsJA.put(elementJO);
        }
        jo.put("elements", elementsJA);
        return jo;
    }

}
