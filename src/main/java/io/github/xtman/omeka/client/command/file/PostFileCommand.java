package io.github.xtman.omeka.client.command.file;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Vector;

import org.json.JSONObject;

import io.github.xtman.omeka.client.OmekaClient;
import io.github.xtman.omeka.client.command.PostEntityCommand;
import io.github.xtman.omeka.model.File;
import io.github.xtman.omeka.model.builder.FileBuilder;

public class PostFileCommand extends PostEntityCommand<File> {

    public static final String BOUNDARY = "------------E19zNvXGzXaLvS5C";

    private InputStream _in;
    private long _length;

    public PostFileCommand(OmekaClient client, FileBuilder fileMeta, String fileName, InputStream in, long length,
            String mimeType) {
        super(client, "files", fileMeta);

        /*
         * Close the stream in the super class.
         */
        if (super.requestContentStream() != null) {
            try {
                super.requestContentStream().close();
            } catch (Throwable e) {

            }
        }

        _length = 0;
        Vector<InputStream> iss = new Vector<InputStream>(3);
        byte[] prefixBytes = prefix(fileMeta, fileName, length, mimeType).getBytes();
        _length += prefixBytes.length;
        iss.add(new ByteArrayInputStream(prefixBytes));

        _length += length;
        iss.add(in);

        byte[] suffixBytes = BOUNDARY.getBytes();
        _length += suffixBytes.length;
        iss.add(new ByteArrayInputStream(suffixBytes));

        _in = new SequenceInputStream(iss.elements());

    }

    @Override
    public InputStream requestContentStream() {
        return _in;
    }

    @Override
    public Long requestContentLength() {
        return _length;
    }

    @Override
    protected File instantiate(JSONObject jo) throws Throwable {
        return new File(jo);
    }

    static String prefix(FileBuilder fileMeta, String fileName, long length, String mimeType) {
        StringBuilder sb = new StringBuilder();
        sb.append(BOUNDARY).append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"data\"\r\n");
        sb.append("\r\n");
        sb.append(fileMeta.build().toString()).append("\r\n");
        sb.append(BOUNDARY).append("\r\n");
        sb.append(String.format("Content-Disposition: form-data; name=\"file\"; filename=\"%s\"", fileName))
                .append("\r\n");
        sb.append(String.format("Content-Type: %s", mimeType));
        sb.append("\r\n");
        return sb.toString();
    }

}
