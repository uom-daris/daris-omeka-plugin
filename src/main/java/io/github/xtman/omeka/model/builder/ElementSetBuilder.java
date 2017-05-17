package io.github.xtman.omeka.model.builder;

import org.json.JSONObject;

public class ElementSetBuilder implements EntityBuilder {

    private String _name;
    private String _description;

    public ElementSetBuilder(String name, String description) {
        _name = name;
        _description = description;
    }

    public ElementSetBuilder() {
    }

    public ElementSetBuilder setName(String name) {
        _name = name;
        return this;
    }

    public ElementSetBuilder setDescription(String description) {
        _description = description;
        return this;
    }

    @Override
    public JSONObject build() {
        JSONObject jo = new JSONObject();
        jo.put("name", _name);
        if (_description != null) {
            jo.put("description", _description);
        }
        return jo;
    }

}
