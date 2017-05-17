package io.github.xtman.omeka.client.command;

import io.github.xtman.omeka.client.OmekaClient;

public abstract class PutCommand<T> extends OmekaCommandBase<T> {

    protected PutCommand(OmekaClient client, String path) {
        super(client, path);
        setContentType("applcation/json");
    }

    @Override
    public final String requestMethod() {
        return "PUT";
    }

    @Override
    public String requestContentType() {
        return "application/json";
    }

}
