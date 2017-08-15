package io.github.xtman.omeka.model.builder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileBuilder implements EntityBuilder {

    private Long _order;
    private Long _itemId;
    private List<ElementTextBuilder> _elementTexts;
    private String _fileName;
    private String _mimeType;
    private long _length = -1;
    private InputStream _in;

    public FileBuilder(Long order, Long itemId, java.util.Collection<ElementTextBuilder> elementTexts) {
        _order = order;
        _itemId = itemId;
        _elementTexts = new ArrayList<ElementTextBuilder>();
        setElementTexts(elementTexts);
    }

    public FileBuilder() {
        this(null, null, null);
    }

    public FileBuilder setOrder(Long order) {
        _order = order;
        return this;
    }

    public FileBuilder setItemId(Long itemId) {
        _itemId = itemId;
        return this;
    }

    public FileBuilder setElementTexts(java.util.Collection<ElementTextBuilder> elementTexts) {
        _elementTexts.clear();
        if (elementTexts != null && !elementTexts.isEmpty()) {
            _elementTexts.addAll(elementTexts);
        }
        return this;
    }

    public FileBuilder addElementText(boolean html, String text, Long elementId) {
        _elementTexts.add(new ElementTextBuilder(html, text, elementId));
        return this;
    }

    public String fileName() {
        return _fileName;
    }

    public FileBuilder setFileName(String fileName) {
        _fileName = fileName;
        return this;
    }

    public String mimeType() {
        return _mimeType;
    }

    public FileBuilder setMimeType(String mimeType) {
        _mimeType = mimeType;
        return this;
    }

    public long length() {
        return _length;
    }

    public FileBuilder setLength(long length) {
        _length = length;
        return this;
    }

    public InputStream stream() {
        return _in;
    }

    public FileBuilder setStream(InputStream in) {
        _in = in;
        return this;
    }

    public FileBuilder setInput(InputStream in, long length, String mimeType, String fileName) {
        _in = in;
        _length = length;
        _mimeType = mimeType;
        _fileName = fileName;
        return this;
    }

    @Override
    public JSONObject build() {
        JSONObject jo = new JSONObject();
        if (_order != null) {
            jo.put("order", _order);
        }
        if (_itemId != null) {
            JSONObject itemJO = new JSONObject();
            itemJO.put("id", _itemId);
            jo.put("item", itemJO);
        }
        if (!_elementTexts.isEmpty()) {
            JSONArray elementTextJA = new JSONArray();
            for (ElementTextBuilder et : _elementTexts) {
                elementTextJA.put(et.build());
            }
            jo.put("element_texts", elementTextJA);
        }

        return jo;
    }

}
