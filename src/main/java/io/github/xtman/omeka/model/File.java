package io.github.xtman.omeka.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.xtman.json.JSONUtils;
import io.github.xtman.omeka.client.util.OmekaDateUtils;

public class File extends EntityBase {

    // @formatter:off
    /*
    {
        "id":3,
        "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/files\/3",
        "file_urls":{
            "original":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/files\/original\/52b1c490032c67912d133314a3919363.JPG",
            "fullsize":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/files\/fullsize\/52b1c490032c67912d133314a3919363.jpg",
            "thumbnail":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/files\/thumbnails\/52b1c490032c67912d133314a3919363.jpg",
            "square_thumbnail":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/files\/square_thumbnails\/52b1c490032c67912d133314a3919363.jpg"
        },
        "added":"2016-08-29T07:13:31+00:00",
        "modified":"2016-08-29T07:13:41+00:00",
        "filename":"52b1c490032c67912d133314a3919363.JPG",
        "authentication":"c1b43d106b8802eb55aaca061eb3c866",
        "has_derivative_image":true,
        "mime_type":"image\/jpeg",
        "order":null,
        "original_filename":"poang-rocking-chair-white__0452037_PE600918_S4.JPG",
        "size":42377,
        "stored":true,
        "type_os":"JPEG image data, JFIF standard 1.01, resolution (DPI), density 1x1, segment length 16, baseline, precision 8, 500x500, frames 3",
        "metadata":{
            "mime_type":"image\/jpeg",
            "video":{
                "dataformat":"jpg",
                "lossless":false,
                "bits_per_sample":24,
                "pixel_aspect_ratio":1,
                "resolution_x":500,
                "resolution_y":500,
                "compression_ratio":0.056502666666667
            }
        },
        "item":{
            "id":2,
            "url":"https:\/\/omeka-test.cloud.unimelb.edu.au\/omeka14\/api\/items\/2",
            "resource":"items"
        },
        "element_texts":[
    
        ],
        "extended_resources":[
    
        ]
    }
     */
    // @formatter:on

    public static class FileUrls {
        public final String original;
        public final String fullsize;
        public final String thumbnail;
        public final String squareThumbnail;

        FileUrls(JSONObject jo) {
            this.original = jo.getString("original");
            this.fullsize = jo.getString("fullsize");
            this.thumbnail = jo.getString("thumbnail");
            this.squareThumbnail = jo.getString("square_thumbnail");
        }
    }

    private FileUrls _fileUrls;
    private Date _added;
    private Date _modified;
    private String _filename;
    private String _authentication;
    private boolean _hasDerivativeImage;
    private String _mimeType;
    private Long _order;
    private String _originalFilename;
    private long _size;
    private boolean _stored;
    private String _typeOS;
    private JSONObject _metadata;
    private EntityInfo _item;
    private List<ElementText> _elementTexts;
    private List<ExtendedResourceInfo> _extendedResources;

    public File(JSONObject jo) throws Throwable {
        super(jo);
        _fileUrls = new FileUrls(jo.getJSONObject("file_urls"));
        _added = OmekaDateUtils.getDate(jo, "added");
        _modified = OmekaDateUtils.getDate(jo, "modified");
        _filename = jo.getString("filename");
        _authentication = jo.getString("authentication");
        _hasDerivativeImage = jo.getBoolean("has_derivative_image");
        _mimeType = jo.getString("mime_type");
        _order = jo.isNull("order") ? null : jo.getLong("order");
        _originalFilename = jo.getString("original_filename");
        _size = jo.getLong("size");
        _stored = jo.getBoolean("stored");
        _typeOS = jo.getString("type_os");
        _metadata = jo.getJSONObject("metadata");
        _item = EntityInfo.instantiate(jo, "item");
        _elementTexts = ElementText.instantiateList(jo, "element_texts");
        _extendedResources = ExtendedResourceInfo.instantiateList(jo, "extended_resources");
    }

    public FileUrls fileUrls() {
        return _fileUrls;
    }

    public Date added() {
        return _added;
    }

    public Date modified() {
        return _modified;
    }

    public String filename() {
        return _filename;
    }

    public String authentication() {
        return _authentication;
    }

    public boolean hasDerivativeImage() {
        return _hasDerivativeImage;
    }

    public String mimeType() {
        return _mimeType;
    }

    public Long order() {
        return _order;
    }

    public String originalFilename() {
        return _originalFilename;
    }

    public long size() {
        return _size;
    }

    public boolean stored() {
        return _stored;
    }

    public String typeOS() {
        return _typeOS;
    }

    public JSONObject metadata() {
        return _metadata;
    }

    public EntityInfo item() {
        return _item;
    }

    public List<ElementText> elementTexts() {
        return Collections.unmodifiableList(_elementTexts);
    }

    public List<ExtendedResourceInfo> extendedResources() {
        return Collections.unmodifiableList(_extendedResources);
    }

    public static List<File> instantiateFiles(JSONArray ja) throws Throwable {
        return new JSONUtils.ObjectArrayParser<File>() {

            @Override
            public File instantiateList(JSONObject jo) throws Throwable {
                return new File(jo);
            }
        }.parse(ja);
    }

    public static List<File> instantiateFiles(JSONObject jo, String key) throws Throwable {
        if (jo.has(key)) {
            return instantiateFiles(jo.getJSONArray(key));
        }
        return null;
    }

}
