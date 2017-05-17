package io.github.xtman.omeka.client.command;

import java.io.InputStream;
import java.net.HttpURLConnection;

import io.github.xtman.http.client.HttpRequest;
import io.github.xtman.http.client.HttpResponse;
import io.github.xtman.http.exception.HttpException;
import io.github.xtman.omeka.client.OmekaClient;

public class DeleteCommand extends OmekaCommandBase<Void> {

    protected DeleteCommand(OmekaClient client, String path) {
        super(client, path);
    }

    @Override
    public final String requestMethod() {
        return "DELETE";
    }

    @Override
    public InputStream requestContentStream() {
        return null;
    }

    @Override
    public Long requestContentLength() {
        return null;
    }

    @Override
    public String requestContentType() {
        return null;
    }

    @Override
    public Void handleResponse(HttpRequest request, HttpResponse response) throws Throwable {
        int responseCode = response.responseCode();
        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            return null;
        } else {
            throw HttpException.create(response, request);
        }
    }

}
