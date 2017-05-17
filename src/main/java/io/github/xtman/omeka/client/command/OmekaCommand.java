package io.github.xtman.omeka.client.command;

import java.util.Map;

import io.github.xtman.http.client.HttpRequest;
import io.github.xtman.omeka.client.OmekaClient;

public interface OmekaCommand<T> extends HttpRequest {

    OmekaClient client();

    T execute() throws Throwable;

    String resourcePath();

    Map<String, String> requestParameters();

}