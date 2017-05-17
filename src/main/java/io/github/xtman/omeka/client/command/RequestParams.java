package io.github.xtman.omeka.client.command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RequestParams {
    private Map<String, Object> _params;

    protected RequestParams() {
        _params = new LinkedHashMap<String, Object>();
    }

    protected void put(String key, Object value) {
        if (value == null) {
            _params.remove(key);
        } else {
            _params.put(key, value);
        }
    }

    protected void apply(OmekaCommandBase<?> command) {
        if (_params != null && !_params.isEmpty()) {
            Set<String> pns = _params.keySet();
            for (String pn : pns) {
                command.setRequestParameter(pn, String.valueOf(_params.get(pn)));
            }
        }
    }
}
