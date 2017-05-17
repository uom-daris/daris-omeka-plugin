package io.github.xtman.omeka.client.command;

import java.io.InputStream;

import io.github.xtman.omeka.client.OmekaClient;

public abstract class GetCommand<T> extends OmekaCommandBase<T> {

    protected GetCommand(OmekaClient client, String path) {
        super(client, path);
    }

    @Override
    public final String requestMethod() {
        return "GET";
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

}
