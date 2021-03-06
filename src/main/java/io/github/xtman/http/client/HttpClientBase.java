package io.github.xtman.http.client;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.xtman.io.StreamUtils;

public class HttpClientBase implements HttpClient {

    @Override
    public <T> T execute(HttpRequest request, HttpResponseHandler<T> responseHandler) throws Throwable {
        URI uri = URI.create(request.requestUri());
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream responseInputStream = null;
        try {
            conn.setDoOutput(true);
            conn.setDoInput(true);
            setRequestMethod(conn, request.requestMethod());
            // String authorization = getAuthorization(username, password);
            // conn.setRequestProperty("Authorization", authorization);
            Map<String, String> requestProperties = request.requestProperties();
            if (requestProperties != null) {
                Set<String> hns = requestProperties.keySet();
                for (String hn : hns) {
                    conn.setRequestProperty(hn, requestProperties.get(hn));
                }
            }
            if (request.requestContentLength() != null && request.requestContentLength() >= 0) {
                conn.setFixedLengthStreamingMode(request.requestContentLength());
            }
            if (request.requestContentStream() != null) {
                try {
                    StreamUtils.copy(request.requestContentStream(), conn.getOutputStream(), request.abortCheck(),
                            request.uploadMonitor());
                } finally {
                    conn.getOutputStream().close();
                }
            }
            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();
            Map<String, List<String>> responseHeaders = conn.getHeaderFields();
            try {
                responseInputStream = conn.getInputStream();
            } catch (FileNotFoundException e) {
                responseInputStream = null;
            } catch (java.io.IOException ioe) {
                responseInputStream = conn.getErrorStream();
            }
            try {
                if (responseHandler != null) {
                    HttpResponse response = new HttpResponse(responseCode, responseMessage, responseHeaders,
                            responseInputStream);
                    return responseHandler.handleResponse(request, response);
                } else {
                    return null;
                }
            } finally {
                if (responseInputStream != null) {
                    responseInputStream.close();
                }
            }
        } finally {
            conn.disconnect();
        }
    }

    private static void setRequestMethod(HttpURLConnection conn, String method) throws Throwable {
        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            Class<?> c = conn.getClass();
            Field methodField = null;
            Field delegateField = null;
            try {
                delegateField = c.getDeclaredField("delegate");
            } catch (NoSuchFieldException nsfe) {

            }
            while (c != null && methodField == null) {
                try {
                    methodField = c.getDeclaredField("method");
                } catch (NoSuchFieldException nsfe) {

                }
                if (methodField == null) {
                    c = c.getSuperclass();
                }
            }
            if (methodField != null) {
                methodField.setAccessible(true);
                methodField.set(conn, method);
            }

            if (delegateField != null) {
                delegateField.setAccessible(true);
                HttpURLConnection delegate = (HttpURLConnection) delegateField.get(conn);
                setRequestMethod(delegate, method);
            }
        }
    }

}
