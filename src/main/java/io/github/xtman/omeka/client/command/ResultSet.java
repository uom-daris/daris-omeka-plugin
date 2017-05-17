package io.github.xtman.omeka.client.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.github.xtman.omeka.model.Entity;

public class ResultSet<T extends Entity> {

    private long _totalNumberOfResults;
    private String _firstPageUrl;
    private String _lastPageUrl;
    private String _nextPageUrl;
    private String _previousPageUrl;
    private List<T> _results;

    public ResultSet(List<T> results, Map<String, List<String>> responseHeaderFields) throws Throwable {
        if (results != null && !results.isEmpty()) {
            _results = new ArrayList<T>(results);
        }
        String omekaTotalResults = responseHeaderFields.get("Omeka-Total-Results").get(0);
        _totalNumberOfResults = (omekaTotalResults != null && !omekaTotalResults.isEmpty())
                ? Long.parseLong(omekaTotalResults) : 0L;
        if (responseHeaderFields.containsKey("Link")) {
            String link = responseHeaderFields.get("Link").get(0);
            if (link != null) {
                parseLink(link);
            }
        }
    }

    private void parseLink(String link) throws Throwable {
        String[] urlTokens = link.split("\\ *,\\ *");
        for (String urlToken : urlTokens) {
            urlToken = urlToken.trim();
            if (urlToken.startsWith("<")) {
                urlToken = urlToken.substring(1);
            }
            String[] parts = urlToken.split(">;\\ *rel=\"");
            String url = parts[0];
            String type = parts[1].trim();
            if (type.endsWith("\"")) {
                type = type.substring(0, type.length() - 1);
            }
            if ("first".equals(type)) {
                _firstPageUrl = url;
            } else if ("last".equals(type)) {
                _lastPageUrl = url;
            } else if ("prev".equals(type)) {
                _previousPageUrl = url;
            } else if ("next".equals(type)) {
                _nextPageUrl = url;
            } else {
                throw new Exception("Failed to parse Link: " + link);
            }
        }

    }

    public long totalNumberOfResults() {
        return _totalNumberOfResults;
    }

    public String firstPageUrl() {
        return _firstPageUrl;
    }

    public String lastPageUrl() {
        return _lastPageUrl;
    }

    public String nextPageUrl() {
        return _nextPageUrl;
    }

    public String previousPageUrl() {
        return _previousPageUrl;
    }

    public List<T> entities() {
        if (_results != null) {
            return Collections.unmodifiableList(_results);
        }
        return null;
    }

}
