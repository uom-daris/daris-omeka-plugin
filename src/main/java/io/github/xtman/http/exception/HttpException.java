package io.github.xtman.http.exception;

import java.io.UnsupportedEncodingException;

import io.github.xtman.http.client.HttpRequest;
import io.github.xtman.http.client.HttpResponse;

public class HttpException extends Exception {

    private static final long serialVersionUID = -2853345446644146536L;

    public HttpException(int responseCode, String responseMessage, String requestUri, String requestMethod,
            Throwable cause) {
        super(messageFor(responseCode, responseMessage, requestUri, requestMethod), cause);
    }

    private static String messageFor(int responseCode, String responseMessage, String requestUri,
            String requestMethod) {
        StringBuilder sb = new StringBuilder("Unexpected HTTP response: ");
        sb.append(responseCode);
        if (responseMessage != null) {
            sb.append(" ").append(responseMessage);
        }
        if (requestUri != null) {
            sb.append(" for ");
            if (requestMethod != null) {
                sb.append(requestMethod).append(" ");
            }
            sb.append("URL: ").append(requestUri);
        }
        return sb.toString();
    }

    public static HttpException create(String message, HttpResponse response, HttpRequest request, Throwable cause)
            throws UnsupportedEncodingException {
        return new HttpException(response.responseCode(), messageFor(message, response), request.requestUri(),
                request.requestMethod(), cause);
    }

    public static HttpException create(String message, HttpResponse response, HttpRequest request)
            throws UnsupportedEncodingException {
        return create(message, response, request, null);
    }

    public static HttpException create(HttpResponse response, HttpRequest request) throws UnsupportedEncodingException {
        return create(null, response, request, null);
    }

    private static String messageFor(String message, HttpResponse response) {
        if (message == null && response == null) {
            return null;
        }
        if (message == null && response != null) {
            return response.responseMessage();
        }
        if (message != null && response == null) {
            return message;
        }
        StringBuilder sb = new StringBuilder();
        if (response != null && response.responseMessage() != null) {
            sb.append(response.responseMessage()).append(" ");
        }
        if (message != null) {
            sb.append(message);
        }
        String r = sb.toString().trim();
        if (r.isEmpty()) {
            return null;
        }
        return r;
    }

}
