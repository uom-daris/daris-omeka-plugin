package io.github.xtman.http.client;

public interface HttpClient {

    <T> T execute(HttpRequest request, HttpResponseHandler<T> responseHandler) throws Throwable;

}
