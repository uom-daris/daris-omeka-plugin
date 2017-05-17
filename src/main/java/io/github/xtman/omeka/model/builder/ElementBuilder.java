package io.github.xtman.omeka.model.builder;

import org.json.JSONObject;

public class ElementBuilder implements EntityBuilder {

    private Long _order;
    private String _name;
    private String _description;
    private String _comment;
    private Long _elementSetId;

    public ElementBuilder() {

    }

    public ElementBuilder(Long order, String name, String description, String comment, Long elementSetId) {
        _order = order;
        _name = name;
        _description = description;
        _comment = comment;
        _elementSetId = elementSetId;
    }

    public ElementBuilder setOrder(long order) {
        _order = order;
        return this;
    }

    public ElementBuilder setName(String name) {
        _name = name;
        return this;
    }

    public ElementBuilder setDescription(String description) {
        _description = description;
        return this;
    }

    public ElementBuilder setComment(String comment) {
        _comment = comment;
        return this;
    }

    public ElementBuilder setElementSetId(long elementSetId) {
        _elementSetId = elementSetId;
        return this;
    }

    @Override
    public JSONObject build() {
        JSONObject jo = new JSONObject();
        if (_order != null) {
            jo.put("order", _order);
        }
        if (_name != null) {
            jo.put("name", _name);
        }
        if (_description != null) {
            jo.put("description", _description);
        }
        if (_comment != null) {
            jo.put("comment", _comment);
        }
        if (_elementSetId != null) {
            JSONObject elementSetJO = new JSONObject();
            elementSetJO.put("id", _elementSetId);
            jo.put("element_set", elementSetJO);
        }
        return jo;
    }

}
