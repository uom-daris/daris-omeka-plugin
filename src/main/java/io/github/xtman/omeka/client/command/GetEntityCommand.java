package io.github.xtman.omeka.client.command;

import java.net.HttpURLConnection;

import org.json.JSONObject;

import io.github.xtman.http.client.HttpRequest;
import io.github.xtman.http.client.HttpResponse;
import io.github.xtman.http.exception.HttpException;
import io.github.xtman.json.JSONUtils;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Entity;
import io.github.xtman.util.PathUtils;

public abstract class GetEntityCommand<T extends Entity> extends GetCommand<T> {

    protected GetEntityCommand(OmekaClient client, String pathPrefix, long id) {
        super(client, PathUtils.join(pathPrefix, Long.toString(id)));
    }

    protected GetEntityCommand(OmekaClient client, String path) {
        super(client, path);
    }

    @Override
    public T handleResponse(HttpRequest request, HttpResponse response) throws Throwable {
        int responseCode = response.responseCode();
        JSONObject responseJO = JSONUtils.parseJsonObject(response.responseContentStream());
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return instantiate(responseJO);
        } else {
            throw HttpException.create(JSONUtils.getStringValue(responseJO, "message"), response, request);
        }
    }

    protected abstract T instantiate(JSONObject jo) throws Throwable;

}
