package io.github.xtman.http.exception;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.json.JSONObject;

import io.github.xtman.http.client.HttpRequest;
import io.github.xtman.http.client.HttpResponse;
import io.github.xtman.io.StreamUtils;
import io.github.xtman.json.JSONUtils;

public class HttpException extends Exception {

    private static final long serialVersionUID = -2853345446644146536L;

    private int _statusCode;

    public HttpException(int responseCode, String responseMessage, String requestUri, String requestMethod,
            Throwable cause) {
        super(messageFor(responseCode, responseMessage, requestUri, requestMethod), cause);
        _statusCode = responseCode;
    }

    public int statusCode() {
        return _statusCode;
    }

    public boolean isNotFoundError() {
        return HttpURLConnection.HTTP_NOT_FOUND == _statusCode;
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

    private static HttpException create(String message, HttpResponse response, HttpRequest request, Throwable cause)
            throws UnsupportedEncodingException {
        return new HttpException(response.responseCode(), messageFor(message, response), request.requestUri(),
                request.requestMethod(), cause);
    }

    private static HttpException create(String message, HttpResponse response, HttpRequest request)
            throws UnsupportedEncodingException {
        return create(message, response, request, null);
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

    public static HttpException create(HttpRequest request, HttpResponse response) throws Throwable {
        String contentType = response.contentType();
        String contentEncoding = response.contentEncoding();
        String content = response.responseContentStream() == null ? null
                : StreamUtils.readString(response.responseContentStream(), contentEncoding);
        String message = null;
        if (content != null) {
            if ("application/json".equals(contentType)) {
                JSONObject jo = new JSONObject(content);
                message = JSONUtils.getStringValue(jo, "message");
            } else if ("text/html".equals(contentType)) {
                int idx1 = content.indexOf("<title>");
                int idx2 = content.indexOf("</title>");
                if (idx1 != -1 && idx2 != -1) {
                    message = content.substring(idx1 + 7, idx2);
                } else {
                    message = content;
                }
            } else {
                message = content;
            }
        }
        return HttpException.create(message, response, request);
    }

}
