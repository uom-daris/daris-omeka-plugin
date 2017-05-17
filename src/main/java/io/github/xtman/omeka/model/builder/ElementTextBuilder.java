package io.github.xtman.omeka.model.builder;

import org.json.JSONObject;

public class ElementTextBuilder implements EntityBuilder {

    private boolean _html;
    private String _text;
    private Long _elementId;

    public ElementTextBuilder(boolean html, String text, Long elementId) {
        _html = html;
        _text = text;
        _elementId = elementId;
    }

    public ElementTextBuilder setHtml(boolean html) {
        _html = html;
        return this;
    }

    public ElementTextBuilder setText(String text) {
        _text = text;
        return this;
    }

    public ElementTextBuilder setElement(long elementId) {
        _elementId = elementId;
        return this;
    }

    @Override
    public JSONObject build() {
        JSONObject jo = new JSONObject();
        jo.put("html", _html);
        if (_text != null) {
            jo.put("text", _text);
        }
        if (_elementId != null) {
            JSONObject elementJO = new JSONObject();
            elementJO.put("id", _elementId);
            jo.put("element", elementJO);
        }
        return jo;
    }

}
