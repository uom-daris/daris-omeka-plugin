package io.github.xtman.http.client;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class HttpResponse {

    private int _code;
    private String _message;
    private Map<String, List<String>> _headerFields;
    private InputStream _contentStream;

    public HttpResponse(int responseCode, String responseMessage, Map<String, List<String>> responseHeaderFields,
            InputStream responseContentStream) {
        _code = responseCode;
        _message = responseMessage;
        _headerFields = responseHeaderFields;
        _contentStream = responseContentStream;
    }

    public int responseCode() {
        return _code;
    }

    public String responseMessage() {
        return _message;
    }

    public Map<String, List<String>> responseHeaderFields() {
        return _headerFields;
    }

    public InputStream responseContentStream() {
        return _contentStream;
    }

}
