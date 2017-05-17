package io.github.xtman.omeka.client.command;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import io.github.xtman.http.client.HttpResponseHandler;
import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.util.AbortCheck;
import io.github.xtman.util.PathUtils;
import io.github.xtman.util.ProgressMonitor;

public abstract class OmekaCommandBase<T> implements OmekaCommand<T>, HttpResponseHandler<T> {

    private OmekaClient _client;
    private AbortCheck _abortCheck;
    private ProgressMonitor _uploadMonitor;
    private ProgressMonitor _downloadMonitor;
    private String _path;
    private Map<String, String> _requestProperties;
    private Map<String, String> _requestParameters;

    protected OmekaCommandBase(OmekaClient client, String path) {
        _client = client;
        _path = path;
        _requestProperties = new LinkedHashMap<String, String>();
        _requestParameters = new LinkedHashMap<String, String>();
    }

    @Override
    public OmekaClient client() {
        return _client;
    }

    @Override
    public T execute() throws Throwable {
        setApiKey(_client.apiKey());
        setContentLength(requestContentLength());
        if (requestContentType() != null) {
            setContentType(requestContentType());
        }
        return client().execute(this, this);
    }

    @Override
    public ProgressMonitor uploadMonitor() {
        return _uploadMonitor;
    }

    protected void setUploadMonitor(ProgressMonitor uploadMonitor) {
        _uploadMonitor = uploadMonitor;
    }

    @Override
    public ProgressMonitor downloadMonitor() {
        return _downloadMonitor;
    }

    protected void setDownloadMonitor(ProgressMonitor downloadMonitor) {
        _downloadMonitor = downloadMonitor;
    }

    @Override
    public AbortCheck abortCheck() {
        return _abortCheck;
    }

    protected void setAbortCheck(AbortCheck abortCheck) {
        _abortCheck = abortCheck;
    }

    @Override
    public String resourcePath() {
        return _path;
    }

    @Override
    public Map<String, String> requestProperties() {
        return Collections.unmodifiableMap(_requestProperties);
    }

    public void setRequestProperty(String name, String value) {
        _requestProperties.put(name, value);
    }

    protected void setApiKey(String apiKey) {
        setRequestParameter("key", apiKey);
    }

    protected void setAccept(String mimeType) {
        setRequestProperty("Accept", mimeType);
    }

    protected void setContentType(String contentType) {
        setRequestProperty("Content-Type", contentType);
    }

    protected void setContentLength(Long length) {
        if (length == null) {
            _requestProperties.remove("Content-Length");
        } else {
            setRequestProperty("Content-Length", Long.toString(length));
        }
    }

    @Override
    public Map<String, String> requestParameters() {
        return Collections.unmodifiableMap(_requestParameters);

    }

    protected String getRequestParameter(String name) {
        return _requestParameters.get(name);
    }

    public void setRequestParameter(String name, String value) {
        if (value != null) {
            _requestParameters.put(name, value);
        } else {
            _requestParameters.remove(name);
        }
    }

    protected void removeRequestParameter(String name) {
        _requestParameters.remove(name);
    }

    @Override
    public String requestUri() throws UnsupportedEncodingException {
        String endPoint = _client.endPoint();
        String path = resourcePath();
        StringBuilder sb = new StringBuilder();
        if (path == null) {
            sb.append(endPoint);
        } else {
            sb.append(PathUtils.join(endPoint, path));
        }
        Map<String, String> parameters = requestParameters();
        if (parameters != null && !parameters.isEmpty()) {
            Set<String> paramNames = parameters.keySet();
            boolean firstParam = true;
            for (String paramName : paramNames) {
                if (firstParam) {
                    firstParam = false;
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(paramName);
                String value = parameters.get(paramName);
                if (value != null && !value.isEmpty()) {
                    sb.append("=").append(URLEncoder.encode(value, "UTF-8"));
                }
            }
        }
        return sb.toString();
    }
}
