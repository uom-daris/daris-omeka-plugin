package io.github.xtman.omeka.client.command;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;

import io.github.xtman.http.client.HttpRequest;
import io.github.xtman.http.client.HttpResponse;
import io.github.xtman.http.exception.HttpException;
import io.github.xtman.json.JSONUtils;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Entity;

public abstract class GetEntitySetCommand<T extends Entity> extends GetCommand<ResultSet<T>> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected static abstract class Params<T extends Params> extends RequestParams {

        public T setPage(long page) {
            put("page", page);
            return (T) this;
        }

        public T setPerPage(int perPage) {
            put("per_page", perPage);
            return (T) this;
        }

    }

    protected GetEntitySetCommand(OmekaClient client, String path, Map<String, Object> params) {
        super(client, path);
        if (params != null) {
            Set<String> pns = params.keySet();
            for (String pn : pns) {
                setRequestParameter(pn, String.valueOf(params.get(pn)));
            }
        }
    }

    protected GetEntitySetCommand(OmekaClient client, String path, RequestParams params) {
        super(client, path);
        if (params != null) {
            params.apply(this);
        }
    }

    @Override
    public ResultSet<T> handleResponse(HttpRequest request, HttpResponse response) throws Throwable {
        int responseCode = response.responseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JSONArray responseJA = JSONUtils.parseJsonArray(response.responseContentStream(),
                    response.contentEncoding());
            List<T> results = instantiate(responseJA);
            return new ResultSet<T>(results, response.responseHeaderFields());
        } else {
            throw HttpException.create(request, response);
        }
    }

    protected abstract List<T> instantiate(JSONArray ja) throws Throwable;

}
