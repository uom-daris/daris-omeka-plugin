package io.github.xtman.omeka.client.command;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.json.JSONObject;

import io.github.xtman.http.client.HttpRequest;
import io.github.xtman.http.client.HttpResponse;
import io.github.xtman.http.exception.HttpException;
import io.github.xtman.json.JSONUtils;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.model.Entity;
import io.github.xtman.omeka.model.builder.EntityBuilder;
import io.github.xtman.util.PathUtils;

public abstract class PutEntityCommand<T extends Entity> extends PutCommand<T> {

    private ByteArrayInputStream _in;
    private long _length;

    protected PutEntityCommand(OmekaClient client, String prefix, long entityId, EntityBuilder entity) {
        super(client, PathUtils.join(prefix, Long.toString(entityId)));
        byte[] joBytes = entity.build().toString().getBytes();
        _in = new ByteArrayInputStream(joBytes);
        _length = joBytes.length;
    }

    @Override
    public InputStream requestContentStream() {
        return _in;
    }

    @Override
    public Long requestContentLength() {
        return _length;
    }

    @Override
    public T handleResponse(HttpRequest request, HttpResponse response) throws Throwable {
        int responseCode = response.responseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JSONObject jo = JSONUtils.parseJsonObject(response.responseContentStream(), response.contentEncoding());
            return instantiate(jo);
        } else {
            throw HttpException.create(request, response);
        }
    }

    protected abstract T instantiate(JSONObject jo) throws Throwable;

}
