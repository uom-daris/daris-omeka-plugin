package io.github.xtman.http.client;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import io.github.xtman.util.AbortCheck;
import io.github.xtman.util.ProgressMonitor;

public interface HttpRequest {

    String requestUri() throws UnsupportedEncodingException;

    String requestMethod();

    Map<String, String> requestProperties();

    InputStream requestContentStream();

    Long requestContentLength();

    String requestContentType();

    AbortCheck abortCheck();

    ProgressMonitor uploadMonitor();

    ProgressMonitor downloadMonitor();

}
