package io.github.xtman.omeka.client.command;

import io.github.xtman.omeka.client.OmekaClient;

public abstract class PostCommand<T> extends OmekaCommandBase<T> {

    protected PostCommand(OmekaClient client, String path) {
        super(client, path);
        setContentType("application/json");
    }

    @Override
    public final String requestMethod() {
        return "POST";
    }

    @Override
    public String requestContentType() {
        return "application/json";
    }

}
