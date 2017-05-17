package io.github.xtman.http.client;

public interface HttpResponseHandler<T> {

    T handleResponse(HttpRequest request, HttpResponse response) throws Throwable;

}
