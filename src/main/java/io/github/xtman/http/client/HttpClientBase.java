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

import io.github.xtman.http.exception.HttpException;
import io.github.xtman.io.StreamUtils;

public class HttpClientBase implements HttpClient {

    private static final String IOEXCEPTION_MESSAGE_PREFIX = "Server returned HTTP response code: ";

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
            try {
                responseInputStream = conn.getInputStream();
            } catch (FileNotFoundException e) {
                responseInputStream = null;
            }
            Map<String, List<String>> responseHeaders = conn.getHeaderFields();
            try {
                if (responseHandler != null) {
                    return responseHandler.handleResponse(request,
                            new HttpResponse(responseCode, responseMessage, responseHeaders, responseInputStream));
                } else {
                    return null;
                }
            } finally {
                if (responseInputStream != null) {
                    responseInputStream.close();
                }
            }
        } catch (java.io.IOException ioe) {
            String msg = ioe.getMessage();
            if (msg != null && msg.startsWith(IOEXCEPTION_MESSAGE_PREFIX)) {
                int startIdx = IOEXCEPTION_MESSAGE_PREFIX.length();
                int endIdx = startIdx + 3;
                int responseCode = Integer.parseInt(msg.substring(startIdx, endIdx));
                throw new HttpException(responseCode, null, request.requestUri(), request.requestMethod(), ioe);
            } else {
                throw ioe;
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
